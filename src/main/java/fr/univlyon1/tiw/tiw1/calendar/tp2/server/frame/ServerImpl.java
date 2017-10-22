package fr.univlyon1.tiw.tiw1.calendar.tp2.server.frame;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.ApplicationConfig;
import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.EventContainer;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.RegistryVariable;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContext;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContextImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.ContextVariable;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AnnotatedFieldInjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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
    private String packageName;
    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);
    private MutablePicoContainer container = new DefaultPicoContainer();

    /**
     * @param config configuration of Calendar
     *               - The file name
     *               - The date format
     *               - The calendars' name
     */
    public ServerImpl (Config config, Annuaire annuaire) throws ParserConfigurationException, SAXException, IOException,
            ClassNotFoundException {

        ApplicationConfig applicationConfig = new ApplicationConfig();

        MutablePicoContainer serverContainer = new DefaultPicoContainer().as(Characteristics.CACHE);
        serverContainer.addComponent(SimpleDateFormat.class);
        serverContainer.addComponent(ArrayList.class);
        serverContainer.addComponent(new File(config.getProperty(Config.DIRECTORY_NAME)));
        serverContainer.addComponent(ICalendarDAO.class, applicationConfig.getDAOClass());
        serverContainer.addComponent(Config.class, config);
        serverContainer.addComponent(CalendarEntity.class, new CalendarEntity(config.getProperty(Config.CALENDAR_NAME)));


        serverContainer.addComponent(RegistryVariable.CONTEXT_ROOT.getContextName(), CalendarContextImpl.class);
        ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_ROOT.getContextName()))
                .setContextVariable(ContextVariable.CONFIG, config);
        ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_ROOT.getContextName()))
                .setContextVariable(ContextVariable.REQUEST, this);

        serverContainer.addComponent(RegistryVariable.CONTEXT_APPLICATION.getContextName(), CalendarContextImpl.class);
        ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_APPLICATION.getContextName()))
                .setContextVariable(ContextVariable.CONFIG, config);

        serverContainer.addComponent(RegistryVariable.CONTEXT_BUSINESS.getContextName(), CalendarContextImpl.class);
        ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_BUSINESS.getContextName()))
                .setContextVariable(ContextVariable.ENTITY,
                serverContainer.getComponent(CalendarEntity.class));

        serverContainer.addComponent(RegistryVariable.CONTEXT_PERSISTENCE.getContextName(), CalendarContextImpl.class);
        ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_PERSISTENCE.getContextName(),
                CalendarContextImpl.class)).setContextVariable(ContextVariable.DAO,
                serverContainer.getComponent(ICalendarDAO.class));

        /* *** Instance of event administrator *** */
        EventContainer eventContainer = new EventContainer(new Caching().wrap(new AnnotatedFieldInjection()),
                serverContainer, applicationConfig.getMaxInstances());

        eventContainer.addComponent("id", String.class);
        eventContainer.addComponent("title", String.class);
        eventContainer.addComponent("description", String.class);
        eventContainer.addComponent("start", Date.class);
        eventContainer.addComponent("end", Date.class);

        serverContainer.addConfig("eventContainer", eventContainer);

        annuaire.setRegistry(RegistryVariable.CONTEXT_ROOT, serverContainer
                .getComponent(RegistryVariable.CONTEXT_ROOT.getContextName()));
        annuaire.setRegistry(RegistryVariable.CONTEXT_APPLICATION, serverContainer
                .getComponent(RegistryVariable.CONTEXT_APPLICATION.getContextName()));
        annuaire.setRegistry(RegistryVariable.CONTEXT_BUSINESS, serverContainer
                .getComponent(RegistryVariable.CONTEXT_BUSINESS.getContextName()));
        annuaire.setRegistry(RegistryVariable.CONTEXT_PERSISTENCE, serverContainer
                .getComponent(RegistryVariable.CONTEXT_PERSISTENCE.getContextName()));

        serverContainer.addComponent(annuaire);

        for (Class classBusiness: applicationConfig.getAllBusinessComponentsClass()) {
            serverContainer.addComponent(classBusiness.getSimpleName(), classBusiness);

            ((CalendarContext) serverContainer.getComponent(RegistryVariable.CONTEXT_BUSINESS.getContextName()))
                    .setContextVariable(classBusiness.getName(), serverContainer.getComponent(CalendarEntity.class));
            packageName = classBusiness.getPackage().getName();

            annuaire.setRegistry(classBusiness.getName(), serverContainer.getComponent(classBusiness.getSimpleName()));
        }

        this.container = serverContainer;
        this.container.start();
//
//        for (Class classBusiness: applicationConfig.getAllBusinessComponentsClass()) {
//
//        }
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
                    .getComponent(Class.forName(packageName.concat(".").concat(command.getCommande()))))
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
        /* Il n'y a pas besoin de rien faire pour le moment */
    }
}
