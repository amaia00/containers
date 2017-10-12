package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.client.CalendarUI;
import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Calendar;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/11/17.
 */
public class ServerMain {

    public static void  main (String[] args) {
        // TODO config params
        Config config = new Config("My calendar","EEE MMM dd kk:mm:ss zzz yyyy", "/tmp");

        //Variable globale (singleton)
        Calendar calendar = new Calendar(config);
        CalendarUI calendarUI = new CalendarUI(calendar);
        calendarUI.start();
    }
}
