package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.composants;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarFind extends CalendarImpl {

    public CalendarFind(Config config, Annuaire annuaire) {
        super(config, annuaire);
    }

    @Override
    protected Event addEvent(EventDTO eventDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException {

        for (Event temp : getEvents()) {
            Event event = new Event(eventDTO, this.parseDate(eventDTO.getStart()),
                    this.parseDate(eventDTO.getEnd()));

            if (temp.equals(event))
                return temp;
        }

        throw new ObjectNotFoundException("L'evenement ".concat(eventDTO.getTitle())
                .concat(" ne se trouve pas dans la liste d'evenements"));
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
    protected String getInfos() {
        throw new UnsupportedOperationException();
    }
}
