package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class JSONCalendarDAO implements ICalendarDAO, ICalendarMarshaller {

    private File directory;

    public JSONCalendarDAO(File directory) {
        this.directory = directory;
    }

    @Override
    public void marshall(CalendarEntity calendarImpl, Writer output) throws IOException {
        //TODO: à implémenter

    }

    @Override
    public void saveCalendar(CalendarEntity calendarImpl) {
        //TODO: à implémenter
    }

    @Override
    public void deleteCalendar(CalendarEntity calendarImpl) {
        //TODO: à implémenter
    }

    @Override
    public CalendarEntity loadCalendar(String name) throws CalendarNotFoundException {
        //TODO: à implémenter
        return null;
    }

    @Override
    public void saveEvent(Event event, CalendarEntity calendarImpl) {
        //TODO: à implémenter
    }

    @Override
    public void deleteEvent(Event event, CalendarEntity calendarImpl) {
        //TODO: à implémenter
    }

    @Override
    public Event findEvent(Event event, CalendarEntity calendarImpl) {
        //TODO à implémenter
        return null;
    }

    @Override
    public void marshall(CalendarEntity calendar, File output) throws IOException {
        //TODO: à implémenter
    }

    @Override
    public CalendarEntity unmarshall(StreamSource xml) throws JAXBException {
        return null;
    }
}
