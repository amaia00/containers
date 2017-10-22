package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Class for the mapping XML / JSON / etc
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */

public class CalendarEntity {

    private String name;

    private Collection<Event> event;

    public CalendarEntity() {
        this.event = new ArrayList<>();
    }

    public CalendarEntity(String name) {
        this.name = name;
        this.event = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Collection<Event> getEvent() {
        return event;
    }

    public void setEvent(Collection<Event> event) {
        this.event = event;
    }

    /**
     * This method synchronize the list of events in this class with the list of
     * events in the container.
     * We keep this class only for the mapping purposes.
     *
     * @param eventList the list of events in the container
     */
    public void sync(List<Event> eventList) {
        this.event.clear();
        this.event = eventList;
    }
}
