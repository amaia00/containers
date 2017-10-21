package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public class CalendarContextImpl implements CalendarContext{
    private ICalendarDAO calendarDAO;

    public CalendarContextImpl(ICalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;
    }

    @Override
    public ICalendarDAO getCalendarDAO() {
        return this.calendarDAO;
    }

    @Override
    public void setCalendarDAO(ICalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;

    }
}
