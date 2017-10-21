package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarAdd extends CalendarImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CalendarAdd.class);

    public CalendarAdd (Config config, CalendarEntity calendarEntity) {
        super(config, calendarEntity);
    }

    @Override
    protected Event addEvent(EventDTO eventDTO) {

        Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                this.parseDate(eventDTO.getEnd()));
        event.setId(UUID.randomUUID().toString());

        getEvents().add(event);
        try {
            dao.saveEvent(event, getEntity());
            LOG.debug("DAO called in addEvent");
        } catch (Exception e) {
            LOG.error("Error in addEvent", e);
        }
        return event;
    }

    @Override
    Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    void synchronizeEvents() {
        throw new UnsupportedOperationException();
    }

    @Override
    String getInfos() {
        throw new UnsupportedOperationException();
    }

}
