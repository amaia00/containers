package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.ICalendarDAO;

import java.io.InvalidClassException;

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
    public ICalendarDAO getCalendarDAO() throws InvalidClassException {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();

        String className = elements[2].getClassName();

        // FIXME: remove naivemethodaccessor (?) for test purposes
        if (className.matches("(.*).Calendar(Add|Remove|List|Find|Sync)") || className.equals("sun.reflect.NativeMethodAccessorImpl"))
            return this.calendarDAO;

        throw new InvalidClassException("The caller class should be a calendar. Founded class: ".concat(className) );
    }

    @Override
    public void setCalendarDAO(ICalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;

    }
}
