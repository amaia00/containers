package fr.univlyon1.tiw.tiw1.calendar.modele;

import fr.univlyon1.tiw.tiw1.calendar.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.dao.XMLCalendarDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.IOException;
import java.util.*;

@XmlRootElement(name = "calendar")
@XmlType(propOrder = {"name", "events"})
public class Calendar {

    private static final Logger LOG = LoggerFactory.getLogger(Calendar.class);

    @XmlElement
    private String name;
    private ICalendarDAO dao;
    private Collection<Event> events;

    public Calendar() {
    }

    public Calendar(String name, File daoDirectory) {
        this.name = name;
        try {
            this.dao = new XMLCalendarDAO(daoDirectory);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.events = new ArrayList<>();
    }

    @XmlElement(name="event")
    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    public Event addEvent(String title, Date start, Date end, String description) {
        UUID uuid = UUID.randomUUID();
        Event evt = new Event(title, description, start, end, uuid.toString());
        events.add(evt);
        // insertion dans le support de persistance
        try {
            dao.saveEvent(evt, this);
            LOG.debug("DAO called in addEvent");
        } catch (Exception e) {
            LOG.error("Error in addEvent", e);
        }
        return evt;
    }

    public void removeEvent(String title, Date start, Date end, String desc) {
        Event event = new Event(title, desc, start, end, null);

        // Suppression dans la liste
        for (Iterator<Event> i = events.iterator(); i.hasNext(); ) {
            Event temp = i.next();
            if (temp.equals(event)) {
                events.remove(temp);
                // Suppression dans le support de persistance
                try {
                    dao.deleteEvent(temp, this);
                    LOG.debug("DAO called in deleteEvent");
                } catch (Exception e) {
                    LOG.error("Error in deleteEvent", e);
                }
                removeEvent(title, start, end, desc);
                return;
            }
        }
    }

    public void synchronizeEvents() {
        try {
            Calendar tmp = dao.loadCalendar(this.name);
            this.setEvents(tmp.getEvents());
        } catch (CalendarNotFoundException e) {
            LOG.error("Error while loading calendar",e );
        }
    }

    public String getName() {
        return this.name;
    }

    /**
     * Méthode destinée à l'affichage
     *
     * @return une string formattée contentant toutes les infos des événements de l'calendar
     */
    public String getInfos() {
        String infos = "";
        for (Iterator<Event> i = events.iterator(); i.hasNext(); ) {
            Event temp = i.next();
            infos += temp.getInfos() + "\n";
        }
        return infos;
    }
}