package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Optional;

public class XMLCalendarDAO implements ICalendarDAO, ICalendarMarshaller {

    private static final Logger LOG = LoggerFactory.getLogger(XMLCalendarDAO.class);

    private File directory;
    private JAXBContext jaxbC;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLCalendarDAO(File directory) throws JAXBException, IOException {
        this.directory = directory;
        if (directory.exists() && !directory.isDirectory()) {
            throw new IOException(directory + " should be a directory");
        } else if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                throw new IOException("Error while creating directory " + directory);
            }
        }
        this.jaxbC = JAXBContext.newInstance(Calendar.class, Event.class);
        this.marshaller = this.jaxbC.createMarshaller();
        this.unmarshaller = this.jaxbC.createUnmarshaller();
    }

    private File fileFromCalendarName(String calendarName) {
        return new File(directory, calendarName + ".xml");
    }

    @Override
    public void saveCalendar(Calendar calendar) {
        File outputFile = fileFromCalendarName(calendar.getName());
        try (FileWriter fw = new FileWriter(outputFile)) {
            marshall(calendar, fw);
            fw.flush();
        } catch (IOException e) {
            LOG.error("Error while saving the calendar " + calendar.getName(), e);
        }
    }

    @Override
    public void deleteCalendar(Calendar calendar) {
        File toDelete = fileFromCalendarName(calendar.getName());
        if (toDelete.exists()) {
            boolean deleted = toDelete.delete();
            if (!deleted) {
                LOG.error("Error: the file ".concat(toDelete.getName()).concat(" could not be deleted"));
            }
        }
    }

    @Override
    public Calendar loadCalendar(String name) throws CalendarNotFoundException {
        System.out.println("loadCalendar ".concat(name));
        File inputFile = fileFromCalendarName(name);
        if (inputFile.canRead()) {
            try (FileReader fr = new FileReader(inputFile)) {
                return unmarshall(fr);
            } catch (IOException e) {
                throw new CalendarNotFoundException(e);
            }
        } else {
            throw new CalendarNotFoundException("Error: the file " + inputFile + " cannot be read");
        }
    }

    @Override
    public void saveEvent(Event event, Calendar calendar) {
        if (! calendar.getEvents().contains(event)) {
            calendar.getEvents().add(event);
        }
        saveCalendar(calendar);
    }

    @Override
    public void deleteEvent(Event event, Calendar calendar) {
        Event evn = findEvent(event, calendar);
        calendar.getEvents().remove(evn);
        saveCalendar(calendar);
    }

    @Override
    public Event findEvent(Event event, Calendar calendar) {
        Event evn = null;

        Optional<Event> eventOptional = calendar.getEvents().stream().filter(e -> e.equals(event)).findFirst();
        if (eventOptional.isPresent())
            evn = eventOptional.get();

        return evn;
    }

    @Override
    public void marshall(Calendar calendar, Writer output) throws IOException {
        try {
            marshaller.marshal(calendar, output);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Calendar unmarshall(Reader input) throws IOException {
        try {
            return (Calendar) unmarshaller.unmarshal(input);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }
}
