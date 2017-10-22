package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.ComponentFactory;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;

import java.util.*;


/**
 *
 * The object pattern implemented in this class  is an adaptation of the code publish in Wikipedia
 * (https://en.wikipedia.org/wiki/Object_pool_pattern)
 *
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/22/17.
 */
public class EventContainer extends DefaultPicoContainer implements  Observer {

    private static final String EVENT = "Event";
    private int maxInstances;
    private ICalendarDAO calendarDAO;
    private static HashMap<Event, Long> available = new HashMap<>();
    private static HashMap<Event, Long> inUse = new HashMap<>();


    public EventContainer(ComponentFactory componentFactory, PicoContainer parent, int maxInstances,
                          ICalendarDAO calendarDAO) {
        super(componentFactory, parent);
        this.calendarDAO = calendarDAO;
        this.maxInstances = maxInstances;
    }

    public Event add (EventDTO eventDTO) {
        stop();

        Calendar calendar = (Calendar) getParent().getComponent(Command.ADD_EVENT.getCommande());
        Event event = getObject();
        event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStart(calendar.parseDate(eventDTO.getStart()));
        event.setEnd(calendar.parseDate(eventDTO.getEnd()));

//        addComponent(EVENT + getComponents().size(), event);
        calendarDAO.saveEvent(calendar.getName(), event);
        getParent().getComponent(CalendarEntity.class).getEvent().add(event);

        start();
//        sync();

        return (Event) getComponent(EVENT + (getComponents().size() - 1));
    }

    public int remove(EventDTO eventDTO) {

        Event tmp;
        int i = 0;
        int affectedEvents = 0;

        stop();


        List<Object> eventList = getComponents();
        for (Iterator<Object> iterator = eventList.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            if (object.getClass().equals(Event.class)) {
                removeComponent(EVENT + i);
                removeComponentByInstance(object);
//                tmp = (Event) object;
//                if (event.equals(tmp)) {
//                    removeComponent(EVENT + i);
//                /*
//                 On coupe le lien entre le conteneur et la Event
//                 Plus aucune référence n'existe sur cet objet
//                 On attend que le garbage collecting passe...
//                 */
//                    removeComponentByInstance(tmp);
//                    affectedEvents++;
//                } else {
//                /*
//                Il faut changer les noms de référence des composants suivant ceux qu'on a enlevés,
//                sans quoi on aura un problème pour en rajouter d'autres...
//                */
//                    if (affectedEvents > 0) {
//                        addComponent(EVENT + ( i - affectedEvents),
//                                getComponent(EVENT + i));
//                        removeComponent(EVENT + i);
//                    }
//                }
//
                i++;

            }

        }

        createInstances(maxInstances);

        Calendar calendar = (Calendar) getParent().getComponent(Command.REMOVE_EVENT.getCommande());
        Event event = getObject();
        event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStart(calendar.parseDate(eventDTO.getStart()));
        event.setEnd(calendar.parseDate(eventDTO.getEnd()));

        calendarDAO.deleteEvent(calendar.getName(), event);
        getParent().getComponent(CalendarEntity.class).getEvent().remove(event);
        release(event);

        start();
        return affectedEvents;
    }

    public String list() {
        Calendar calendar = (Calendar) getParent().getComponent(Command.LIST_EVENTS.getCommande());
        StringBuilder info = new StringBuilder();

        try {
            for (Event event : calendarDAO.loadCalendar(calendar.getName()).getEvent()) {
                EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), calendar.formatDate(event.getStart()),
                        calendar.formatDate(event.getEnd()), event.getId());
                info.append(eventDTO.toString());
            }
        } catch (CalendarNotFoundException e) {
            //TODO: ADD LOG
        }
        return info.toString();
    }


    public void sync() {
        Calendar calendar = (Calendar) getParent().getComponent(Command.SYNC_EVENTS.getCommande());
        Collection<Event> eventList;

        try {
            eventList = calendarDAO.loadCalendar(calendar.getName()).getEvent();
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

                //addComponent(EVENT + getComponents().size(), evnt);
                getParent().getComponent(CalendarEntity.class).getEvent().add(evnt);
            }

            start();

        } catch (CalendarNotFoundException e) {
            //TODO LOG
        }
    }


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


    private void createInstances(int maxInstances) {

        int i = inUse.size() + available.size();
        for (; i< maxInstances; i++) {
            addComponent(EVENT + i, new Event());
            available.put(((Event)getComponent(EVENT + i)), System.currentTimeMillis());
        }
    }

    private static Event popElement(HashMap<Event, Long> map) {
        Map.Entry<Event, Long> entry = map.entrySet().iterator().next();
        Event key = entry.getKey();

        map.remove(entry.getKey());
        return key;
    }

    private static Event popElement(HashMap<Event, Long> map, Event key) {
        map.remove(key);
        return key;
    }

    public static void release(Event event) {
        cleanUp(event);
        available.put(event, System.currentTimeMillis());
        inUse.remove(event);
    }

    private static void cleanUp(Event event) {
        event.setId(null);
        event.setTitle(null);
        event.setDescription(null);
        event.setStart(null);
        event.setEnd(null);
    }


    @Override
    public void update(Observable o, Object arg) {
        // TODO: ??
    }
}
