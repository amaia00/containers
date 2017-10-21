package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.*;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CalendarTest {

    @Test
    public void testAddedToCollection() throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("tiw", Command.ADD_EVENT);
        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);
        assertNotNull(evt);
        assertTrue(calendarImpl.getEvents().contains(evt));
    }

    @Test
    public void testSynchroDAO() throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("test-synchro", Command.ADD_EVENT);
        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);

        calendarImpl = TestCalendarBuilder.getCalendarByAction(calendarImpl.getEntity(), Command.REMOVE_EVENT);
        calendarImpl.getEvents().remove(evt);

        calendarImpl = TestCalendarBuilder.getCalendarByAction(calendarImpl.getEntity(), Command.SYNC_EVENTS);
        calendarImpl.process(Command.SYNC_EVENTS, null);

        assertTrue(calendarImpl.getEvents().contains(evt));
    }
}
