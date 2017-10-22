package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.frame.ServerImpl;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertTrue;

public class CalendarTest {
    private static ServerImpl server;
    private static Annuaire annuaire;

    @BeforeClass
    public static void setupClass() throws ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        annuaire = new Annuaire();
        server = new ServerImpl(annuaire);
    }

    @Test
    @Ignore
    public void testAddedToCollection() throws ParseException, ObjectNotFoundException {
//        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("tiw", Command.ADD_EVENT, context);
//        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);
//        assertNotNull(evt);
//        assertTrue(calendarImpl.getEvents().contains(evt));
        EventDTO eventDTO = new EventDTO("test-event", "description", "", "", null);
        server.processRequest(Command.ADD_EVENT, eventDTO);

        String allEvents = server.processRequest(Command.LIST_EVENTS);
        assertTrue(allEvents.contains("test-event"));

    }

    @Test
    public void testSynchroDAO() throws ParseException, ObjectNotFoundException {
//        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("test-synchro", Command.ADD_EVENT, context);
//        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);
//
//        calendarImpl = TestCalendarBuilder.getCalendarByAction(Command.REMOVE_EVENT, context);
//        calendarImpl.getEvents().remove(evt);
//
//        calendarImpl = TestCalendarBuilder.getCalendarByAction(Command.SYNC_EVENTS, context);
//        calendarImpl.process(Command.SYNC_EVENTS, null);
//
//        assertTrue(calendarImpl.getEvents().contains(evt));
    }
}
