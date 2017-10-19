package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.client.CalendarUI;
import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/11/17.
 */
public class Main {

    public static void main(String[] args) {

        Config config = new Config("My calendar", "EEE MMM dd kk:mm:ss zzz yyyy", "/tmp");
        Server server = new Server(config);

        CalendarUI calendarUI = new CalendarUI(server.getCalendar());
        calendarUI.start();

    }
}
