package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.Startable;

import java.util.Collection;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public interface Calendar extends Startable {

    String getName();

    CalendarEntity getEntity();

    /**
     *
     * @param command
     * @param eventDTO
     * @return
     * @throws ObjectNotFoundException
     */
    Object process(Command command, EventDTO eventDTO) throws ObjectNotFoundException;

    Collection<Event> getEvents();
//
//    Event addEvent(EventDTO eventDTO);
//
//    Event findEvent(EventDTO eventDTO) throws ObjectNotFoundException;
//
//    void removeEvent(EventDTO eventDTO) throws ObjectNotFoundException;
//
//
//    abstract void synchronizeEvents();
//
//    abstract String getInfo();

}
