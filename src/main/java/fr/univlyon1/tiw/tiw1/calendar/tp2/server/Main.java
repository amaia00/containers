package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.client.CalendarUI;
import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.AnnuaireImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.frame.ServerImpl;

import java.io.InvalidClassException;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/11/17.
 */
public class Main {

    public static void main(String[] args) throws InvalidClassException {

        Config config = new Config("My calendar", "EEE MMM dd kk:mm:ss zzz yyyy", "/tmp");

        Annuaire annuaire = new AnnuaireImpl();
        new ServerImpl(config, annuaire);

        CalendarUI calendarUI = new CalendarUI(annuaire);
        calendarUI.start();

    }
}
