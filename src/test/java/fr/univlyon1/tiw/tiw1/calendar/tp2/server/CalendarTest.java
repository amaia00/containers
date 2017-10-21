package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CalendarTest {
    private static CalendarContext context;

    @BeforeClass
    public static void setupClass() throws SAXException, JAXBException, IOException {
        context = new CalendarContextImpl(new XMLCalendarDAO(new File("target/test-data")));
    }


    @Test
    public void testAddedToCollection() throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("tiw", Command.ADD_EVENT, context);
        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);
        assertNotNull(evt);
        assertTrue(calendarImpl.getEvents().contains(evt));
    }

    @Test
    public void testSynchroDAO() throws ParseException, ObjectNotFoundException {
        Calendar calendarImpl = TestCalendarBuilder.buildCalendar("test-synchro", Command.ADD_EVENT, context);
        Event evt = TestCalendarBuilder.addCMJava(calendarImpl);

        calendarImpl = TestCalendarBuilder.getCalendarByAction(calendarImpl.getEntity(), Command.REMOVE_EVENT, context);
        calendarImpl.getEvents().remove(evt);

        calendarImpl = TestCalendarBuilder.getCalendarByAction(calendarImpl.getEntity(), Command.SYNC_EVENTS, context);
        calendarImpl.process(Command.SYNC_EVENTS, null);

        assertTrue(calendarImpl.getEvents().contains(evt));
    }
}
