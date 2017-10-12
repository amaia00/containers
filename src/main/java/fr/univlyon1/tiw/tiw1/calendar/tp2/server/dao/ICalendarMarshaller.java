package fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Calendar;

import java.io.*;

public interface ICalendarMarshaller {

    /**
     * Writes an calendar to an output stream
     *
     * @param calendar the calendar to write
     * @param output the output stream to write the calendar to
     * @throws IOException in case of IO problem
     */
    void marshall(Calendar calendar, Writer output) throws IOException;

    /**
     * Reads an calendar from a stream
     *
     * @param input the stream to read from
     * @return the calendar read
     * @throws IOException if the calendar cannot be read from the stream
     */
    Calendar unmarshall(Reader input) throws IOException;
}
