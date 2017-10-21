package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.CalendarContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarRemove extends CalendarImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CalendarRemove.class);

    public CalendarRemove(Config config, CalendarContext context) {
        super(config, context);
    }

    @Override
    Event addEvent(EventDTO eventDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException {

        Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                this.parseDate(eventDTO.getEnd()));

        for (Iterator<Event> i = getEvents().iterator(); i.hasNext(); ) {
            Event temp = i.next();
            if (temp.equals(event)) {
                getEvents().remove(temp);
                // Suppression dans le support de persistance
                try {
                    dao.deleteEvent(temp, getEntity());
                    LOG.debug("DAO called in deleteEvent");
                } catch (Exception e) {
                    LOG.error("Error in deleteEvent", e);
                }
                removeEvent(eventDTO);
                return;
            }
        }
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
