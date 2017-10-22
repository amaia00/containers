package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.ComponentFactory;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 *
 * The object pattern implemented in this class  is an adaptation of the code publish in Wikipedia
 * (https://en.wikipedia.org/wiki/Object_pool_pattern)
 *
 * This class is the container "son" which have by objective provide instances of Event class
 * respecting the configuration file where the user has defined the maximal number of instances
 * allowed.
 *
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/22/17.
 */
public class EventContainer extends DefaultPicoContainer {
    private static final Logger LOG = LoggerFactory.getLogger(EventContainer.class);
    private static HashMap<Event, Long> available = new HashMap<>();
    private static HashMap<Event, Long> inUse = new HashMap<>();

    private static final String EVENT = "Event";
    private String calendarName;
    private int maxInstances;
    private ICalendarDAO calendarDAO;


    /**
     *  The constructor which receive the number of instances that this container
     *  can keep.
     *
     * @param componentFactory the factory
     * @param parent the parent container (serverContainer)
     * @param maxInstances the maximal number of instances allowed
     */
    public EventContainer(ComponentFactory componentFactory, PicoContainer parent, int maxInstances) {
        super(componentFactory, parent);
        this.maxInstances = maxInstances;
        this.calendarName = getParent().getComponent(Config.class).getProperty(Config.CALENDAR_NAME);
        this.calendarDAO = getParent().getComponent(ICalendarDAO.class);
    }


    /**
     * This method allows add a new event to the XML file.
     *
     * @param eventDTO event
     * @return event
     */
    public Event add (EventDTO eventDTO) {
        Event event = getEventInstance(eventDTO);
        calendarDAO.saveEvent(calendarName, event);
        return (Event) getComponent(EVENT + (getComponents().size() - 1));
    }


    /**
     * This method receive a EventDTO instance and return a Event instance who is result
     * of the process of recycle Event instances'
     *
     * @param eventDTO EventDTO instance
     * @return Event instance
     */
    private Event getEventInstance(EventDTO eventDTO) {
        Calendar calendar = (Calendar) getParent().getComponent(Command.ADD_EVENT.getCommande());
        Event event = getObject();
        event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStart(calendar.parseDate(eventDTO.getStart()));
        event.setEnd(calendar.parseDate(eventDTO.getEnd()));

        return event;
    }

    /**
     * This method remove a Event of the XML file.
     *
     * @param eventDTO EventDTO instance
     */
    public void remove(EventDTO eventDTO) {
        stop();

        int i = 0;
        List<Object> eventList = getComponents();

        /* We're obligated to remove all the components which are instances of Event,
        * because we cannot made a comparison due to the fact that if we add a new
         * Event instance, this will be automatically in the component ... See createInstance method. */
        for (Iterator<Object> iterator = eventList.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            if (object.getClass().equals(Event.class)) {
                removeComponent(EVENT + i);
                removeComponentByInstance(object);

                i++;
            }
        }

        /* We create new instances */
        createInstances(maxInstances);
        Event event = getEventInstance(eventDTO);
        calendarDAO.deleteEvent(calendarName, event);
        release(event);
        start();
    }

    /**
     * This method read the XML file and get all the events in.
     * @return the list of events.
     */
    public String list() {
        Calendar calendar = (Calendar) getParent().getComponent(Command.LIST_EVENTS.getCommande());
        StringBuilder info = new StringBuilder();

        try {
            for (Event event : calendarDAO.loadCalendar(calendarName).getEvent()) {
                EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), calendar.formatDate(event.getStart()),
                        calendar.formatDate(event.getEnd()), event.getId());
                info.append(eventDTO.toString());
            }
        } catch (CalendarNotFoundException e) {
            LOG.warn(e.getMessage());
        }

        return info.toString();
    }


    /**
     * This method synchronize the events in the XML file and the components in the container.
     * However, due to the quantity of instances allowed, this method could result useless.
     */
    public void sync() {
        try {
            Collection<Event> eventList = calendarDAO.loadCalendar(calendarName).getEvent();
            System.out.println("Nombre d'événements : " + eventList.size());

            /* *** Vidage du conteneur d'événements *** */
            stop();
            for(Object event: getComponents()) {
                if (event.getClass().equals(Event.class)) {
                    release((Event) event);
                    removeComponentByInstance(event);
                }
            }

            inUse.clear();
            available.clear();
            createInstances(maxInstances);

            /* Synchronisation du conteneur d'événements et du support de persistance */
            for (Event event : eventList) {
                Event evnt = getObject();
                evnt.setId(event.getId());
                evnt.setTitle(event.getTitle());
                evnt.setDescription(event.getDescription());
                evnt.setStart(event.getStart());
                evnt.setEnd(event.getEnd());
            }

            start();
        } catch (CalendarNotFoundException e) {
            LOG.warn(e.getMessage());
        }
    }


    /**
     * Get an instance of Event applying the characteristics of object pattern design
     *
     * @return an instance of Event
     */
    public static synchronized Event getObject() {
        long now = System.currentTimeMillis();
        Event event;
        if (available.isEmpty()) {
            event = popElement(inUse);
        } else {
            event = popElement(available);
        }

        cleanUp(event);
        inUse.put(event, now);

        return event;
    }

    /**
     * Create so many instances as maxInstances allowed.
     *
     * @param maxInstances quantity allowed of instances.
     */
    private void createInstances(int maxInstances) {

        int i = inUse.size() + available.size();
        for (; i< maxInstances; i++) {
            addComponent(EVENT + i, new Event());
            available.put(((Event)getComponent(EVENT + i)), System.currentTimeMillis());
        }
    }

    /**
     *
     * @param map map
     * @return Event instance
     */
    private static Event popElement(HashMap<Event, Long> map) {
        Map.Entry<Event, Long> entry = map.entrySet().iterator().next();
        Event key = entry.getKey();

        map.remove(entry.getKey());
        return key;
    }


    /**
     * deactivate an instance of Event
     * @param event event instance
     */
    private static void release(Event event) {
        cleanUp(event);
        available.put(event, System.currentTimeMillis());
        inUse.remove(event);
    }

    /**
     * clean the attributes of the object
     *
     * @param event event instance.
     */
    private static void cleanUp(Event event) {
        event.setId(null);
        event.setTitle(null);
        event.setDescription(null);
        event.setStart(null);
        event.setEnd(null);
    }
}
