package fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name = "calendar")
@XmlType(propOrder = {"name", "events"})
public class Calendar {
    private static final Logger LOG = LoggerFactory.getLogger(Calendar.class);

    @XmlElement
    private String name;
    private ICalendarDAO dao;
    private Collection<Event> events;
    private Config config;

    public Calendar() {
        this.events = new ArrayList<>();
    }

    public Calendar(Config config) {
        this.config = config;
        this.name = config.getProperty(Config.CALENDAR_NAME);

        try {
            this.dao = new XMLCalendarDAO(new File(config.getProperty(Config.DIRECTORY_NAME)));
        } catch (JAXBException | IOException e) {
            LOG.error(e.getMessage());
        }

        this.events = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    @XmlElement(name = "event")
    public Collection<Event> getEvents() {
        return events;
    }

    public Event addEvent(EventDTO eventDTO) {

        Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                this.parseDate(eventDTO.getEnd()));
        event.setId(UUID.randomUUID().toString());

        events.add(event);
        try {
            dao.saveEvent(event, this);
            LOG.debug("DAO called in addEvent");
        } catch (Exception e) {
            LOG.error("Error in addEvent", e);
        }
        return event;
    }

    public Event findEvent(EventDTO eventDTO) throws EventNotFoundException {

        for (Event temp : events) {
            Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                    this.parseDate(eventDTO.getEnd()));

            if (temp.equals(event))
                return temp;
        }

        throw new EventNotFoundException("L'evenement ".concat(eventDTO.getTitle())
                .concat(" ne se trouve pas dans la liste d'evenements"));
    }

    public void removeEvent(EventDTO eventDTO) throws EventNotFoundException {

        Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                this.parseDate(eventDTO.getEnd()));

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
                removeEvent(eventDTO);
                return;
            }
        }


    }

    public void synchronizeEvents() {
        try {
            Calendar tmp = dao.loadCalendar(this.name);
            this.setEvents(tmp.getEvents());
        } catch (CalendarNotFoundException e) {
            LOG.error("Error while loading calendar", e);
        }
    }

    private void setEvents(Collection<Event> events) {
        this.events = events;
    }

    private String formatDate(Date d) {
        return new SimpleDateFormat(this.config.getProperty(Config.DATE_FORMAT)).format(d);
    }

    private Date parseDate(String s) {
        try {
            return new SimpleDateFormat(this.config.getProperty(Config.DATE_FORMAT)).parse(s);
        } catch (ParseException e) {
            LOG.error(e.getMessage());
        }
        return new Date();
    }


    /**
     * Méthode destinée à l'affichage
     *
     * @return une string formattée contentant toutes les infos des événements de l'calendar
     */
    public String getInfos() {
        StringBuilder info = new StringBuilder();
        for (Event event : this.events) {
            EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), this.formatDate(event.getStart()),
                    this.formatDate(event.getEnd()), event.getId());
            info.append(eventDTO.toString());
        }
        return info.toString();
    }
}