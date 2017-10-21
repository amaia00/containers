package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.CalendarContext;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public class CalendarFind extends CalendarImpl {

    public CalendarFind(Config config, CalendarContext context) {
        super(config, context);
    }

    @Override
    Event addEvent(EventDTO eventDTO) {
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
