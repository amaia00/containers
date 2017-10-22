package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.composants;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
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

    public CalendarRemove(Config config, Annuaire annuaire) {
        super(config, annuaire);
    }

    @Override
    protected Event addEvent(EventDTO eventDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException {
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
    protected void synchronizeEvents() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getInfos() {
        throw new UnsupportedOperationException();
    }

}
