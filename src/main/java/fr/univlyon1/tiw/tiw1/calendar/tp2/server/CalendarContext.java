package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;

import java.io.InvalidClassException;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public interface CalendarContext {

    ICalendarDAO getCalendarDAO() throws InvalidClassException;

    void setCalendarDAO(ICalendarDAO calendarDAO);
}
