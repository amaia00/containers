package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.Startable;
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
public class Calendar implements Startable {
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


    /**
     *
     * @param command
     * @param eventDTO
     * @return
     * @throws ObjectNotFoundException
     */
    public Object process(Command command, EventDTO eventDTO) throws ObjectNotFoundException {
        Object reponse = null;

        switch (command) {
            case INIT_EVENT:
                synchronizeEvents();
                break;
            case ADD_EVENT:
                reponse = addEvent(eventDTO);
                break;
            case REMOVE_EVENT:
                removeEvent(eventDTO);
                break;
            case LIST_EVENTS:
                reponse = getInfos();
                break;
            case FIND_EVENT:
                reponse = findEvent(eventDTO);
                break;
            default:
                break;

        }

        return reponse;
    }


    @XmlElement(name = "event")
    public Collection<Event> getEvents() {
        return events;
    }

    private Event addEvent(EventDTO eventDTO) {

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

    private Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException {

        for (Event temp : events) {
            Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                    this.parseDate(eventDTO.getEnd()));

            if (temp.equals(event))
                return temp;
        }

        throw new ObjectNotFoundException("L'evenement ".concat(eventDTO.getTitle())
                .concat(" ne se trouve pas dans la liste d'evenements"));
    }

    private void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException {

        Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                this.parseDate(eventDTO.getEnd()));

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

    /**
     *
     */
    private void synchronizeEvents() {
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
    private String getInfos() {
        StringBuilder info = new StringBuilder();
        for (Event event : this.events) {
            EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), this.formatDate(event.getStart()),
                    this.formatDate(event.getEnd()), event.getId());
            info.append(eventDTO.toString());
        }
        return info.toString();
    }

    @Override
    public void start() {
        //Calendar démarré. Objet d'accès aux données : fr.univlyon1.tiw.tiw1.calendar.dao.XMLCalendarDAO@95c083
        System.out.println("LIFE CYCLE: Calendar démarré. Objet d'accès aux données : ".concat(this.toString()));
    }

    @Override
    public void stop() {
        System.out.println("LIFE CYCLE: Calendar detenu. Objet d'accès aux données : ".concat(this.toString()));
    }
}