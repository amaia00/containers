package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarList extends CalendarImpl {

    public CalendarList(Config config, Annuaire annuaire) {
        super(config, annuaire);
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
    void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    void synchronizeEvents() {
       throw new UnsupportedOperationException();
    }

    @Override
    protected String getInfos() {
        StringBuilder info = new StringBuilder();
        for (Event event : getEvents()) {
            EventDTO eventDTO = new EventDTO(event.getTitle(), event.getDescription(), this.formatDate(event.getStart()),
                    this.formatDate(event.getEnd()), event.getId());
            info.append(eventDTO.toString());
        }
        return info.toString();
    }

}
