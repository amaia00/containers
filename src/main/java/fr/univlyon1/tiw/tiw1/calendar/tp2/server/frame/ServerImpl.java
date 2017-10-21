package fr.univlyon1.tiw.tiw1.calendar.tp2.server.frame;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.RegistryVariable;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContextImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.ContextVariable;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public class ServerImpl implements Server, Observer {
    private static final String PACKAGE_NAME = CalendarImpl.class.getPackage().getName();
    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);
    private MutablePicoContainer container = new DefaultPicoContainer();

    /**
     * @param config configuration of Calendar
     *               - The file name
     *               - The date format
     *               - The calendars' name
     */
    public ServerImpl(Config config, Annuaire annuaire) {

        MutablePicoContainer configContainer = new DefaultPicoContainer().as(Characteristics.CACHE);
        configContainer.addComponent(SimpleDateFormat.class);

        // FIXME: Verify if I can change the dependency File for XMLCalendarDAO same for store container.
        MutablePicoContainer xmlContainer = new DefaultPicoContainer(configContainer).as(Characteristics.CACHE);
        xmlContainer.addComponent(ArrayList.class);
        xmlContainer.addComponent(new File(config.getProperty(Config.DIRECTORY_NAME)));
        xmlContainer.addComponent(XMLCalendarDAO.class);

        MutablePicoContainer store = new DefaultPicoContainer(xmlContainer).as(Characteristics.CACHE);
        store.addComponent(new CalendarEntity(config.getProperty(Config.CALENDAR_NAME)));

        /* ***  Creation of context for every layer (root, application, business and persistence) *** */
        MutablePicoContainer contextRoot = new DefaultPicoContainer(configContainer).as(Characteristics.CACHE);
        contextRoot.addComponent(CalendarContextImpl.class);
        contextRoot.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.CONFIG, config);
        contextRoot.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.REQUEST, this);

        MutablePicoContainer contextApp = new DefaultPicoContainer(configContainer).as(Characteristics.CACHE);
        contextApp.addComponent(CalendarContextImpl.class);
        contextApp.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.CONFIG, config);

        MutablePicoContainer contextBusiness = new DefaultPicoContainer(store).as(Characteristics.CACHE);
        contextBusiness.addComponent(CalendarContextImpl.class);
        contextBusiness.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.ENTITY,
                store.getComponent(CalendarEntity.class));

        MutablePicoContainer contextPersistence = new DefaultPicoContainer(xmlContainer).as(Characteristics.CACHE);
        contextPersistence.addComponent(CalendarContextImpl.class);
        contextPersistence.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.DAO,
                xmlContainer.getComponent(XMLCalendarDAO.class));


        /* *** Setting of all the context on the registry *** */
        annuaire.setRegistry(RegistryVariable.CONTEXT_ROOT, contextRoot.getComponent(CalendarContextImpl.class));
        annuaire.setRegistry(RegistryVariable.CONTEXT_APPLICATION, contextApp.getComponent(CalendarContextImpl.class));
        annuaire.setRegistry(RegistryVariable.CONTEXT_BUSINESS, contextBusiness.getComponent(CalendarContextImpl.class));
        annuaire.setRegistry(RegistryVariable.CONTEXT_PERSISTENCE, contextPersistence.getComponent(CalendarContextImpl.class));


        MutablePicoContainer serverContainer = new DefaultPicoContainer().as(Characteristics.CACHE);
        serverContainer.addComponent(config);
        serverContainer.addComponent(annuaire);
        serverContainer.addComponent(CalendarAdd.class);
        serverContainer.addComponent(CalendarRemove.class);
        serverContainer.addComponent(CalendarList.class);
        serverContainer.addComponent(CalendarSync.class);
        serverContainer.addComponent(CalendarFind.class);

        this.container = serverContainer;
        this.container.start();

        annuaire.addObserver(this);
    }

    @Override
    public String processRequest(Command command) throws ObjectNotFoundException {
        return processRequest(command, null);
    }

    @Override
    public String processRequest(Command command, Object object) throws ObjectNotFoundException {

        String list = "";
        Map<String, String> parameters = new HashMap<>();

        if (object != null)
            parameters =  putFieldsToMap(object);

        try {
            list = String.valueOf(((Calendar) this.container
                    .getComponent(Class.forName(PACKAGE_NAME.concat(".").concat(command.getCommande()))))
                    .process(command, paramsToEventDTO(parameters)));
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }

        return list;
    }

    private EventDTO paramsToEventDTO(Map<String, String> params) {

        try {
            return new EventDTO(params.get("title"), params.get("description"),
                    params.get("start"), params.get("end"), null);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Map<String, String> putFieldsToMap(Object object) {
        Map<String, String> fields = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                try {
                    field.setAccessible(true);
                    fields.put(field.getName(), String.valueOf(field.get(object)));
                } catch (IllegalAccessException e) {
                    LOG.warn(e.getMessage());
                }
        }

        return fields;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO: What should I add here??
    }
}
