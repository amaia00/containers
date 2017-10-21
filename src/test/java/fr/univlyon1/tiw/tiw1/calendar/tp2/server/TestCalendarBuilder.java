package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class TestCalendarBuilder {

    private static Config config;


    public static Calendar buildCalendar(String nom, Command command) {
        config = new Config(nom, "/tmp");
        CalendarEntity calendarEntity = new CalendarEntity(nom);
        //CalendarImpl calendarImpl = new CalendarAdd(config, new CalendarEntity());
        return getCalendarByAction(calendarEntity, command);
    }

    public static CalendarImpl getCalendarByAction(CalendarEntity calendarEntity, Command command) {
        switch (command) {
            case ADD_EVENT:
                return new CalendarAdd(config, calendarEntity);
            case LIST_EVENTS:
                return new CalendarList(config, calendarEntity);
            case FIND_EVENT:
                return new CalendarFind(config, calendarEntity);
            case REMOVE_EVENT:
                return new CalendarRemove(config, calendarEntity);
            case SYNC_EVENTS:
                return new CalendarSync(config, calendarEntity);
            default:
                throw new RuntimeException("Not calendar founded");
        }
    }

    public static Calendar calendar1() throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = buildCalendar("My calendarImpl", Command.ADD_EVENT);
        addCMJava(calendarImpl);
        return calendarImpl;
    }


    public static Event addCMJava(Calendar calendarImpl) throws ParseException, ObjectNotFoundException {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, 10, 11, 8, 0);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 9);
        f.set(java.util.Calendar.MINUTE, 30);

        EventDTO event = new EventDTO("CM1 TIW1", "Introduction", d.getTime().toString(),
                f.getTime().toString(), null);

        return (Event) calendarImpl.process(Command.ADD_EVENT, event);
    }

    public static Event addTPJava(Calendar calendarImpl) throws ParseException, ObjectNotFoundException {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, java.util.Calendar.NOVEMBER, 11, 9, 45);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 11);
        f.set(java.util.Calendar.MINUTE, 15);

        EventDTO event = new EventDTO("TP1 TIW1", "Révision Java", d.getTime().toString(),
                f.getTime().toString(), null);

        return (Event) calendarImpl.process(Command.ADD_EVENT, event);
    }
}