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
public class CalendarList extends CalendarImpl {

    public CalendarList(Config config, Annuaire annuaire, EventContainer eventContainer) {
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
       throw new UnsupportedOperationException();
    }

    @Override
    protected String getInfo() {
        StringBuilder info = new StringBuilder();

        for (Event event : getEventContainer().list()) {
            EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), this.formatDate(event.getStart()),
                    this.formatDate(event.getEnd()), event.getId());
            info.append(eventDTO.toString());
        }
        return info.toString();
    }

}
