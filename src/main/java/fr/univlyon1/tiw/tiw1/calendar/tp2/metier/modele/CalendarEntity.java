package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import java.util.ArrayList;
import java.util.Collection;

/**
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
}
