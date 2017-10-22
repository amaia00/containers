package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.picocontainer.Startable;

import java.util.Collection;
import java.util.Date;

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

    String formatDate(Date d);

    Date parseDate(String s);
}
