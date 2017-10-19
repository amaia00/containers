package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.TestCalendarBuilder;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;

public class JSONCalendarDAOTest {

    private static final Schema schema = null;
    private Calendar calendar1;
    private JSONCalendarDAO jDao;

    @BeforeClass
    public static void setupBeforeClass() throws IOException {
        try (InputStream inputStream = JSONCalendarDAO.class.getResourceAsStream("/calendar-schema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
        }
    }

    @Before
    public void setup() throws ParseException, ObjectNotFoundException {
        calendar1 = TestCalendarBuilder.calendar1();
        jDao = new JSONCalendarDAO(new File("target/test-data")); // FIXME: adapter éventuellement la construction du DAO
    }

    @Test @Ignore // FIXME: Supprimer @Ignore une fois la classe JSONAgendaDAO complétée
    public void testJSONSchema() throws IOException {
        StringWriter sw = new StringWriter();
        jDao.marshall(calendar1,sw);
        String json = sw.toString();
        schema.validate(new JSONObject(json));
    }
}
