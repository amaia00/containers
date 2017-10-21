package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.Writer;

public interface ICalendarMarshaller {

    void saveCalendar(CalendarEntity calendarImpl);

    /**
     * Writes an calendarImpl to an output stream
     *
     * @param calendarImpl the calendarImpl to write
     * @param output the output stream to write the calendarImpl to
     * @throws IOException in case of IO problem
     */
    void marshall(CalendarEntity calendarImpl, Writer output) throws IOException;

    /**
     * Reads an calendar from a stream
     *
     * @param input the stream to read from
     * @return the calendar read
     * @throws JAXBException if the calendar cannot be read from the stream
     */
    CalendarEntity unmarshall(StreamSource input) throws JAXBException;
}
