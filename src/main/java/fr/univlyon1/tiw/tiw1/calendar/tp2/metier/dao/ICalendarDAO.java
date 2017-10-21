package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public interface ICalendarDAO {

    /**
     * Sauve un calendrier dans le support de persitance.
     * @param calendar le calendrier à sauver.
     */
    void saveCalendar(CalendarEntity calendar);

    /**
     * Supprime un calendrier dans le support de persitance.
     * @param calendar le calendrier à sauver.
     */
    void deleteCalendar(CalendarEntity calendar);

    /**
     * Charge un calendrier depuis le support de persistance.
     * @param name le nom du calendrier à charger.
     * @return le calendrier chargé.
     * @throws CalendarNotFoundException si le calendrier n'a pas été trouvé
     */
    CalendarEntity loadCalendar(String name) throws CalendarNotFoundException;

    /**
     * Sauve un evenement d'un calendrier dans le support de persitance.
     * @param event l'evenement à sauver.
     * @param calendar le calendrier dans lequel se trouve l'evenement.
     */
    void saveEvent(Event event, CalendarEntity calendar);

    /**
     * Supprime un evenement d'un calendrier dans le support de persitance.
     * @param event l'evenement à sauver.
     * @param calendar le calendrier dans lequel se trouve l'evenement.
     */
    void deleteEvent(Event event, CalendarEntity calendar);

    /**
     *
     * @param event
     * @param calendar
     * @return
     */
    Event findEvent(Event event, CalendarEntity calendar);

    void marshall(CalendarEntity calendar, File output) throws IOException;

    /**
     * Writes an calendarImpl to an output stream
     *
     * @param calendarImpl the calendarImpl to write
     * @param output the output stream to write the calendarImpl to
     * @throws IOException in case of IO problem
     */
    void marshall(CalendarEntity calendarImpl, Writer output) throws IOException;


    CalendarEntity unmarshall(StreamSource xml) throws JAXBException;
}
