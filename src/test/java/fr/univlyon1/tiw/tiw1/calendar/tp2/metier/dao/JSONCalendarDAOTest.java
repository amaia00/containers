package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContext;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContextImpl;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.ContextVariable;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.TestCalendarBuilder;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;

public class JSONCalendarDAOTest {

    private static final Schema schema = null;
    private Calendar calendarImpl1;
    private static CalendarContext context;

    @BeforeClass
    public static void setupBeforeClass() throws IOException {
        try (InputStream inputStream = JSONCalendarDAO.class.getResourceAsStream("/calendar-schema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);

        }
    }

    // FIXME: adapter éventuellement la construction du DAO
    @Before
    public void setup() throws ParseException, ObjectNotFoundException {
        context = new CalendarContextImpl();
        context.setContextVariable(ContextVariable.DAO, new JSONCalendarDAO(new File("target/test-data")));

        calendarImpl1 = TestCalendarBuilder.calendar1(context);
        //jDao = new JSONCalendarDAO(new File("target/test-data")); // FIXME: adapter éventuellement la construction du DAO
    }

    @Test @Ignore // FIXME: Supprimer @Ignore une fois la classe JSONAgendaDAO complétée
    public void testJSONSchema() throws IOException {
        StringWriter sw = new StringWriter();
        ((ICalendarDAO)context.getContextVariable(ContextVariable.DAO)).marshall(calendarImpl1.getEntity(),sw);
        String json = sw.toString();
        schema.validate(new JSONObject(json));
    }
}
