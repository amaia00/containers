package fr.univlyon1.tiw.tiw1.calendar.tp2.server.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele.Event;

import java.io.*;

public class JSONCalendarDAO implements ICalendarDAO, ICalendarMarshaller {

    private File directory;

    public JSONCalendarDAO(File directory) {
        this.directory = directory;
    }

    @Override
    public void marshall(Calendar calendar, Writer output) throws IOException {
        //TODO: à implémenter

    }

    @Override
    public Calendar unmarshall(Reader input) throws IOException {
        //TODO: à implémenter
        return null;
    }

    @Override
    public void saveCalendar(Calendar calendar) {
        //TODO: à implémenter
    }

    @Override
    public void deleteCalendar(Calendar calendar) {
        //TODO: à implémenter
    }

    @Override
    public Calendar loadCalendar(String name) throws CalendarNotFoundException {
        //TODO: à implémenter
        return null;
    }

    @Override
    public void saveEvent(Event event, Calendar calendar) {
        //TODO: à implémenter
    }

    @Override
    public void deleteEvent(Event event, Calendar calendar) {
        //TODO: à implémenter
    }

    @Override
    public Event findEvent(Event event, Calendar calendar) {
        //TODO à implémenter
        return null;
    }
}
