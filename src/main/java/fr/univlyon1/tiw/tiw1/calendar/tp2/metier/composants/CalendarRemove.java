package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.composants;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.EventContainer;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarRemove extends CalendarImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CalendarRemove.class);

    public CalendarRemove(Config config, Annuaire annuaire, EventContainer eventContainer) {
        super(config, annuaire, eventContainer);
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

        getEventContainer().remove(event);
    }

    @Override
    protected void synchronizeEvents() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getInfo() {
        throw new UnsupportedOperationException();
    }

}
