package fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Event;
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

    private Calendar calendar;
    private XMLCalendarDAO xDao;

    private static Schema schema;

    @BeforeClass
    public static void setupClass() throws SAXException {
        schema = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(XMLCalendarDAO.class.getResourceAsStream("/calendar-schema.xsd")));
    }

    @Before
    public void setup() throws JAXBException, IOException, ParseException {
        calendar = TestCalendarBuilder.calendar1();
        xDao = new XMLCalendarDAO(new File("target/test-data"));
    }

    @Test
    public void testSchema() throws IOException {
        Validator validator = schema.newValidator();
        StringWriter sw = new StringWriter();
        xDao.marshall(calendar, sw);
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
        xDao.saveCalendar(calendar);
        Calendar calendar2 = xDao.loadCalendar(calendar.getName());
        assertEquals(calendar.getName(), calendar2.getName());
        for (Event evt : calendar.getEvents()) {
            assertTrue("Event " + evt.getId() + " not found in serialized calendar",
                    calendar2.getEvents().contains(evt));
        }
        for (Event evt : calendar2.getEvents()) {
            assertTrue("Event " + evt.getId() + " not present in initial calendar",
                    calendar.getEvents().contains(evt));
        }
    }

    @Test
    public void testAddEvent() throws CalendarNotFoundException, ParseException {
        xDao.saveCalendar(calendar);
        String id1 = calendar.getEvents().iterator().next().getId();
        LOG.debug("Event in calendar: {}", id1);
        Event evt = TestCalendarBuilder.ajouteTPJava(calendar);
        Iterator<Event> it2 = calendar.getEvents().iterator();
        it2.next();
        String id2 = it2.next().getId();
        LOG.debug("New event in calendar: {}", id2);
        assertNotEquals(id1, id2);
        xDao.saveEvent(evt, calendar);
        Calendar calendar2 = xDao.loadCalendar(calendar.getName());
        assertEquals(2, calendar2.getEvents().size());
        assertTrue("New event missing", calendar2.getEvents().contains(evt));
    }
}