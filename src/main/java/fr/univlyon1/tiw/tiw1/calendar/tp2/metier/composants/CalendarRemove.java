package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.composants;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.EventContainer;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarRemove extends CalendarImpl {

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
        getEventContainer().remove(eventDTO);
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
