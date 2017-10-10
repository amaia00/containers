package fr.univlyon1.tiw.tiw1.calendar.modele;

import java.io.File;
import java.util.GregorianCalendar;

public class TestCalendarBuilder {

    //TODO Mettre à jour cette valeur avant de lancer les tests
    private static final String storageDirectoryName;

    static {
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
            storageDirectoryName = "/C:/temp/";
        } else {
            storageDirectoryName = "/tmp";
        }
    }


    public static Calendar buildCalendar(String nom) {
        Calendar calendar = new Calendar(nom, new File(storageDirectoryName));
        return calendar;
    }

    public static Calendar calendar1() {
        Calendar calendar = buildCalendar("My calendar");
        addCMJava(calendar);
        return calendar;
    }


    public static Event addCMJava(Calendar calendar) {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, 10, 11, 8, 0);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 9);
        f.set(java.util.Calendar.MINUTE, 30);
        Event evt = calendar.addEvent("CM1 TIW1", d.getTime(), f.getTime(), "Introduction");
        return evt;
    }

    public static Event ajouteTPJava(Calendar calendar) {
        java.util.Calendar d = GregorianCalendar.getInstance();
        d.set(2017, 10, 11, 9, 45);
        java.util.Calendar f = (java.util.Calendar) d.clone();
        f.set(java.util.Calendar.HOUR, 11);
        f.set(java.util.Calendar.MINUTE, 15);
        Event evt = calendar.addEvent("TP1 TIW1", d.getTime(), f.getTime(), "Révision Java");
        return evt;
    }
}