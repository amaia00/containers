package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class TestCalendarBuilder {

    private static Config config;


    public static Calendar buildCalendar(String nom) {
        config = new Config(nom, "/tmp");
        Calendar calendar = new Calendar(config);
        return calendar;
    }

    public static Calendar calendar1() throws ParseException, ObjectNotFoundException {
        Calendar calendar = buildCalendar("My calendar");
        addCMJava(calendar);
        return calendar;
    }


    public static Event addCMJava(Calendar calendar) throws ParseException, ObjectNotFoundException {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, 10, 11, 8, 0);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 9);
        f.set(java.util.Calendar.MINUTE, 30);

        EventDTO event = new EventDTO("CM1 TIW1", "Introduction", d.getTime().toString(),
                f.getTime().toString(), null);

        return (Event) calendar.process(Command.ADD_EVENT, event);
    }

    public static Event ajouteTPJava(Calendar calendar) throws ParseException, ObjectNotFoundException {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, java.util.Calendar.NOVEMBER, 11, 9, 45);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 11);
        f.set(java.util.Calendar.MINUTE, 15);

        EventDTO event = new EventDTO("TP1 TIW1", "Révision Java", d.getTime().toString(),
                f.getTime().toString(), null);

        return (Event) calendar.process(Command.ADD_EVENT, event);
    }
}