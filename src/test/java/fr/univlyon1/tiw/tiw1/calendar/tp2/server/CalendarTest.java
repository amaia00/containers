package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CalendarTest {

    @Test
    public void testAddedToCollection() throws ParseException {
        Calendar calendar = TestCalendarBuilder.buildCalendar("tiw");
        Event evt = TestCalendarBuilder.addCMJava(calendar);
        assertNotNull(evt);
        assertTrue(calendar.getEvents().contains(evt));
    }

    @Test
    public void testSynchroDAO() throws ParseException {
        Calendar calendar = TestCalendarBuilder.buildCalendar("test-synchro");
        Event evt = TestCalendarBuilder.addCMJava(calendar);
        calendar.getEvents().remove(evt);
        calendar.synchronizeEvents();

        assertTrue(calendar.getEvents().contains(evt));
    }
}
