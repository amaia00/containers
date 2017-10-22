package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.client.CalendarUI;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.frame.ServerImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/11/17.
 */
public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {

        /*
         * It's possible call the Server avec a configuration like:
         * Config config = new Config("My calendar", "EEE MMM dd kk:mm:ss zzz yyyy", "/tmp");
         * new ServerImpl(config, annuaire);
         * Otherwise, the Server get the config set in the file application-config.xml in the
         * resources folder.
         */

        Annuaire annuaire = new Annuaire();
        new ServerImpl(annuaire);

        CalendarUI calendarUI = new CalendarUI(annuaire);
        calendarUI.start();

    }
}