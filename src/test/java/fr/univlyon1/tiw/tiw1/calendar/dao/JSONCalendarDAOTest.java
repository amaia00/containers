package fr.univlyon1.tiw.tiw1.calendar.dao;

import fr.univlyon1.tiw.tiw1.calendar.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.modele.TestCalendarBuilder;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

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
    public void setup() {
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
