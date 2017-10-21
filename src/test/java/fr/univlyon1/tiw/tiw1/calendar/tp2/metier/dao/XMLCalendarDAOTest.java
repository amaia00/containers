package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.CalendarContext;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.CalendarContextImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.TestCalendarBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class XMLCalendarDAOTest {

    private final static Logger LOG = LoggerFactory.getLogger(XMLCalendarDAOTest.class);

    private Calendar calendarImpl;
    private CalendarContext context;

    private static Schema schema;

    @BeforeClass
    public static void setupClass() throws SAXException {
        schema = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(XMLCalendarDAO.class.getResourceAsStream("/calendar-schema.xsd")));

    }

    @Before
    public void setup() throws JAXBException, IOException, ParseException, ObjectNotFoundException {

//        xDao = new XMLCalendarDAO(new File("target/test-data"));
        CalendarContext context = new CalendarContextImpl(new XMLCalendarDAO(new File("target/test-data")));
        this.context = context;
        calendarImpl = TestCalendarBuilder
                .calendar1(context);
    }

    @Test
    public void testSchema() throws IOException {
        Validator validator = schema.newValidator();
        StringWriter sw = new StringWriter();

        context.getCalendarDAO().marshall(calendarImpl.getEntity(), sw);

        StringReader sr = new StringReader(sw.toString());
        StreamSource ss = new StreamSource(sr);

        try {
            validator.validate(ss);
        } catch (SAXException e) {
            fail("Validation of produced XML failed: " + e.getMessage());
        }
    }

    @Test
    public void testExportImport() throws CalendarNotFoundException {
        context.getCalendarDAO().saveCalendar(calendarImpl.getEntity());
        CalendarEntity calendarImpl2 = context.getCalendarDAO().loadCalendar(calendarImpl.getName());
        assertEquals(calendarImpl.getName(), calendarImpl2.getName());
        for (Event evt : calendarImpl.getEvents()) {
            assertTrue("Event " + evt.getId() + " not found in serialized calendarImpl",
                    calendarImpl2.getEvent().contains(evt));
        }
        for (Event evt : calendarImpl2.getEvent()) {
            assertTrue("Event " + evt.getId() + " not present in initial calendarImpl",
                    calendarImpl.getEvents().contains(evt));
        }
    }

    @Test
    public void testAddEvent() throws CalendarNotFoundException, ParseException, ObjectNotFoundException {
        context.getCalendarDAO().saveCalendar(calendarImpl.getEntity());
        String id1 = calendarImpl.getEvents().iterator().next().getId();
        LOG.debug("Event in calendarImpl: {}", id1);
        Event evt = TestCalendarBuilder.addTPJava(calendarImpl);
        Iterator<Event> it2 = calendarImpl.getEvents().iterator();
        it2.next();
        String id2 = it2.next().getId();
        LOG.debug("New event in calendarImpl: {}", id2);
        assertNotEquals(id1, id2);
        context.getCalendarDAO().saveEvent(evt, calendarImpl.getEntity());

        CalendarEntity calendar2 = context.getCalendarDAO().loadCalendar(calendarImpl.getName());
        assertEquals(2, calendar2.getEvent().size());
        assertTrue("New event missing", calendar2.getEvent().contains(evt));
    }
}