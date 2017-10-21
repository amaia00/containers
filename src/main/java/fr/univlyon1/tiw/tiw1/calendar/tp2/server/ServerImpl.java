package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public class ServerImpl implements Server {
    private static final String PACKAGE_NAME = CalendarImpl.class.getPackage().getName();
    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);
    private MutablePicoContainer container = new DefaultPicoContainer();
    private CalendarContext calendarContext;

    /**
     * @param config configuration of Calendar
     *               - The file name
     *               - The date format
     *               - The calendars' name
     */
    public ServerImpl(Config config) {

        MutablePicoContainer configContainer = new DefaultPicoContainer().as(Characteristics.CACHE);
        configContainer.addComponent(SimpleDateFormat.class);

        // FIXME: Verify if I can change the dependency File for XMLCalendarDAO same for store container.
        MutablePicoContainer xmlContainer = new DefaultPicoContainer(configContainer).as(Characteristics.CACHE);
        xmlContainer.addComponent(ArrayList.class);
        xmlContainer.addComponent(new File(config.getProperty(Config.DIRECTORY_NAME)));
        xmlContainer.addComponent(XMLCalendarDAO.class);

        MutablePicoContainer store = new DefaultPicoContainer(xmlContainer).as(Characteristics.CACHE);
        store.addComponent(new CalendarEntity(config.getProperty(Config.CALENDAR_NAME)));

        MutablePicoContainer context = new DefaultPicoContainer().as(Characteristics.CACHE);
        context.addComponent(CalendarContextImpl.class);

        context.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.DAO,
                xmlContainer.getComponent(XMLCalendarDAO.class));
        context.getComponent(CalendarContextImpl.class).setContextVariable(ContextVariable.ENTITY,
                store.getComponent(CalendarEntity.class));

        MutablePicoContainer serverContainer = new DefaultPicoContainer(context).as(Characteristics.CACHE);
        serverContainer.addComponent(config);
        serverContainer.addComponent(CalendarAdd.class);
        serverContainer.addComponent(CalendarRemove.class);
        serverContainer.addComponent(CalendarList.class);
        serverContainer.addComponent(CalendarSync.class);
        serverContainer.addComponent(CalendarFind.class);

        this.container = serverContainer;
        this.container.start();
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
}
