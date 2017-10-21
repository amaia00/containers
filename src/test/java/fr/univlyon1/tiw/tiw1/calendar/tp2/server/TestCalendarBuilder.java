package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContext;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.ContextVariable;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class TestCalendarBuilder {

    private static Config config;


    public static Calendar buildCalendar(String nom, Command command, CalendarContext context) {
        config = new Config(nom, "/tmp");
        context.setContextVariable(ContextVariable.ENTITY, new CalendarEntity(nom));
        //CalendarImpl calendarImpl = new CalendarAdd(config, new CalendarEntity());
        return getCalendarByAction(command, context);
    }

    public static CalendarImpl getCalendarByAction(Command command,
                                                   CalendarContext context) {
        // FIXME: Annuaire instead of CalendarContext
//        switch (command) {
//            case ADD_EVENT:
//                return new CalendarAdd(config, context);
//            case LIST_EVENTS:
//                return new CalendarList(config, context);
//            case FIND_EVENT:
//                return new CalendarFind(config, context);
//            case REMOVE_EVENT:
//                return new CalendarRemove(config, context);
//            case SYNC_EVENTS:
//                return new CalendarSync(config, context);
//            default:
//                throw new RuntimeException("Not calendar founded");
//        }
        return null;
    }

    public static Calendar calendar1(CalendarContext context) throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = buildCalendar("My calendarImpl", Command.ADD_EVENT, context);
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