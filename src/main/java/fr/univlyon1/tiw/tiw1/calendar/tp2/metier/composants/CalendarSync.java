package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.composants;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.CalendarNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarSync extends CalendarImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CalendarRemove.class);

    public CalendarSync(Config config, Annuaire annuaire, EventContainer eventContainer) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    protected void synchronizeEvents() {
        try {
            CalendarEntity tmp = dao.loadCalendar(getName());
            getEventContainer().sync(new ArrayList<>(tmp.getEvent()));
        } catch (CalendarNotFoundException e) {
            LOG.error("Error while loading calendar", e);
        }
    }

    @Override
    protected String getInfo() {
        throw new UnsupportedOperationException();
    }
}
