package fr.univlyon1.tiw.tiw1.calendar.dao;

import fr.univlyon1.tiw.tiw1.calendar.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.modele.Event;

public interface ICalendarDAO {

    /**
     * Sauve un calendrier dans le support de persitance.
     * @param calendar le calendrier à sauver.
     */
    void saveCalendar(Calendar calendar);

    /**
     * Supprime un calendrier dans le support de persitance.
     * @param calendar le calendrier à sauver.
     */
    void deleteCalendar(Calendar calendar);

    /**
     * Charge un calendrier depuis le support de persistance.
     * @param name le nom du calendrier à charger.
     * @return le calendrier chargé.
     * @throws CalendarNotFoundException si le calendrier n'a pas été trouvé
     */
    Calendar loadCalendar(String name) throws CalendarNotFoundException;

    /**
     * Sauve un evenement d'un calendrier dans le support de persitance.
     * @param event l'evenement à sauver.
     * @param calendar le calendrier dans lequel se trouve l'evenement.
     */
    void saveEvent(Event event, Calendar calendar);

    /**
     * Supprime un evenement d'un calendrier dans le support de persitance.
     * @param event l'evenement à sauver.
     * @param calendar le calendrier dans lequel se trouve l'evenement.
     */
    void deleteEvent(Event event, Calendar calendar);
}
