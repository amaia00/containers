package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.CalendarEntity;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
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
        this.jaxbC = JAXBContext.newInstance(CalendarEntity.class);
        this.marshaller = this.jaxbC.createMarshaller();
        this.unmarshaller = this.jaxbC.createUnmarshaller();
    }

    private File fileFromCalendarName(String calendarName) {
        return new File(directory, calendarName + ".xml");
    }

    // FIXME: The first time than we add a new event the method save override all the events in the existent file.
    @Override
    public void saveCalendar(CalendarEntity calendarImpl) {
        File outputFile = fileFromCalendarName(calendarImpl.getName());
        marshall(calendarImpl, outputFile);

    }

    @Override
    public void deleteCalendar(CalendarEntity calendar) {
        File toDelete = fileFromCalendarName(calendar.getName());
        if (toDelete.exists()) {
            boolean deleted = toDelete.delete();
            if (!deleted) {
                LOG.error("Error: the file ".concat(toDelete.getName()).concat(" could not be deleted"));
            }
        }
    }

    @Override
    public CalendarEntity loadCalendar(String name) throws CalendarNotFoundException {
        StreamSource xml = new StreamSource(fileFromCalendarName(name));
        try {
            return unmarshall(xml);
        } catch (JAXBException e) {
            throw new CalendarNotFoundException(e);
        }
    }

    @Override
    public void saveEvent(String name, Event event) {
        CalendarEntity calendar = null;
        try {
            calendar = loadCalendar(name);
        } catch (CalendarNotFoundException e) {
            LOG.error(e.getMessage());
        }
        if (!calendar.getEvent().contains(event)) {
            calendar.getEvent().add(event);
        }
        saveCalendar(calendar);
    }

    @Override
    public void deleteEvent(String name, Event event) {
        CalendarEntity calendar = null;
        try {
            calendar = loadCalendar(name);
            Event evn = findEvent(event, calendar);
            calendar.getEvent().remove(evn);
        } catch (CalendarNotFoundException e) {
            LOG.error(e.getMessage());
        }

        saveCalendar(calendar);
    }

    @Override
    public Event findEvent(Event event, CalendarEntity calendar) {
        Event evn = null;

        Optional<Event> eventOptional = calendar.getEvent().stream().filter(e -> e.equals(event)).findFirst();
        if (eventOptional.isPresent())
            evn = eventOptional.get();

        return evn;
    }

    @Override
    public void marshall(CalendarEntity calendar, File output) {
//        try {
//            marshaller.marshal(calendar, output);
//        } catch (JAXBException e) {
//            throw new IOException(e);
//        }

        JAXBElement<CalendarEntity> je2 = new JAXBElement<>(new QName("http://master-info.univ-lyon1.fr/TIW/TIW1/calendar",
                "calendar"), CalendarEntity.class, calendar);
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(je2, output);
        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        }
    }

    public void marshall(CalendarEntity calendar, Writer output) {
//        try {
//            marshaller.marshal(calendar, output);
//        } catch (JAXBException e) {
//            throw new IOException(e);
//        }

        JAXBElement<CalendarEntity> je2 = new JAXBElement<>(new QName("http://master-info.univ-lyon1.fr/TIW/TIW1/calendar",
                "calendar"), CalendarEntity.class, calendar);
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "");
//            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, );
            marshaller.marshal(je2, output);
        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public CalendarEntity unmarshall(StreamSource xml) throws JAXBException {

        JAXBElement<CalendarEntity> je1 = unmarshaller.unmarshal(xml, CalendarEntity.class);
        return je1.getValue();

    }
}
