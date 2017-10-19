package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public class ServerImpl implements  Server{
    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);
    private MutablePicoContainer container = new DefaultPicoContainer();
    private Calendar calendar;

    /**
     *
     * @param config
     */
    public ServerImpl(Config config) {

        MutablePicoContainer configContainer = new DefaultPicoContainer();
        configContainer.addComponent(config);
        configContainer.addComponent(SimpleDateFormat.class);

        MutablePicoContainer xmlContainer = new DefaultPicoContainer(configContainer);
        xmlContainer.addComponent(XMLCalendarDAO.class);

        MutablePicoContainer serverContainer = new DefaultPicoContainer(xmlContainer).as(Characteristics.CACHE);
        serverContainer.addComponent(ArrayList.class);
        serverContainer.addComponent(Calendar.class);

        this.container = serverContainer;
        calendar = serverContainer.getComponent(Calendar.class);
        calendar.start();
    }

    @Override
    public String processRequest(Command command) throws ObjectNotFoundException {
        return  processRequest(command, null);
    }

    @Override
    public String processRequest(Command command, Object object) throws ObjectNotFoundException {

        String list = "";
        switch (command) {
            case INIT_EVENT:
            case LIST_EVENTS:
                list = String.valueOf(calendar.process(command, null));
                break;
            default:
                Map<String, String> parameters = putFieldsToMap(object);

                EventDTO eventDTO = new EventDTO(parameters.get("title"), parameters.get("description"),
                        parameters.get("start"), parameters.get("end"), null);
                calendar.process(command, eventDTO);

                calendar.process(command, eventDTO);
        }

        return list;
    }

    private Map<String, String> putFieldsToMap(Object object)  {
        Map<String, String> fields = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
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
