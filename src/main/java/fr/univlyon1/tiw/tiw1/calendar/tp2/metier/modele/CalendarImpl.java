package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class CalendarImpl implements Calendar {
    private static final Logger LOG = LoggerFactory.getLogger(CalendarImpl.class);

    private CalendarEntity entity;
    protected ICalendarDAO dao;
    private Config config;

//    public CalendarImpl() {
//        this.entity.setEvent(new ArrayList<>());
//    }

//    public CalendarImpl(Config config) {
//        this.config = config;
//        this.entity = new CalendarEntity(config.getProperty(Config.CALENDAR_NAME));
//
//        try {
//            this.dao = new XMLCalendarDAO(new File(config.getProperty(Config.DIRECTORY_NAME)));
//        } catch (JAXBException | IOException e) {
//            LOG.error(e.getMessage());
//        }
//
//        this.entity.setEvent(new ArrayList<>());
//    }

    public CalendarImpl(Config config, CalendarEntity entity) {
        this.config = config;

        this.entity = entity;

        if (entity.getEvent() == null)
            this.entity.setEvent(new ArrayList<>());

        if (entity.getName() == null)
            this.entity.setName(config.getProperty(Config.CALENDAR_NAME));

        try {
            this.dao = new XMLCalendarDAO(new File(config.getProperty(Config.DIRECTORY_NAME)));
        } catch (JAXBException | IOException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public Object process(Command command, EventDTO eventDTO) throws ObjectNotFoundException {
        Object reponse = null;

        switch (command) {
            case SYNC_EVENTS:
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

    public Collection<Event> getEvents() {
        return this.entity.getEvent();
    }

    abstract Event addEvent(EventDTO eventDTO);

    abstract Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException;

    abstract void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException;

    abstract void synchronizeEvents();

    void setEvents(Collection<Event> events) {
        this.entity.setEvent(events);
    }

    /**
     * Méthode destinée à l'affichage
     *
     * @return une string formattée contentant toutes les infos des événements de l'calendar
     */
    abstract String getInfos();

    protected String formatDate(Date d) {
        return new SimpleDateFormat(this.config.getProperty(Config.DATE_FORMAT)).format(d);
    }

    protected Date parseDate(String s) {
        try {
            return new SimpleDateFormat(this.config.getProperty(Config.DATE_FORMAT)).parse(s);
        } catch (ParseException e) {
            LOG.error(e.getMessage());
        }
        return new Date();
    }

    public CalendarEntity getEntity() {
        return entity;
    }

    @Override
    public void start() {
        //CalendarImpl démarré. Objet d'accès aux données : fr.univlyon1.tiw.tiw1.calendar.dao.XMLCalendarDAO@95c083
        System.out.println("LIFE CYCLE: CalendarImpl démarré. Objet d'accès aux données : ".concat(this.toString()));
    }

    @Override
    public void stop() {
        System.out.println("LIFE CYCLE: CalendarImpl detenu. Objet d'accès aux données : ".concat(this.toString()));
    }
}