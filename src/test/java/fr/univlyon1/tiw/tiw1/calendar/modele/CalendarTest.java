package fr.univlyon1.tiw.tiw1.calendar.modele;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CalendarTest {

    @Test
    public void testAddedToCollection() {
        Calendar calendar = TestCalendarBuilder.buildCalendar("tiw");
        Event evt = TestCalendarBuilder.addCMJava(calendar);
        assertNotNull(evt);
        assertTrue(calendar.getEvents().contains(evt));
    }

    @Test
    public void testSynchroDAO() {
        Calendar calendar = TestCalendarBuilder.buildCalendar("test-synchro");
        Event evt = TestCalendarBuilder.addCMJava(calendar);
        calendar.getEvents().remove(evt);
        calendar.synchronizeEvents();
        assertTrue(calendar.getEvents().contains(evt));
    }
}
