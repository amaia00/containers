package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Util;
import org.picocontainer.Startable;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Event implements Startable {
    private static final Logger LOG = LoggerFactory.getLogger(Event.class);
    @Inject private String title;
    @Inject private String description;
    @Inject private Date start;
    @Inject private Date end;
    @Inject private String id;

    public Event() {

    }

    public Event(EventDTO eventDTO, Date start, Date end) {
        this.title = eventDTO.getTitle();
        this.description = eventDTO.getDescription();
        this.id = eventDTO.getId();
        this.start = start;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
//        if (this == o)
//            return true;

        if (o == null)
            return false;

        if (this.getClass() != o.getClass())
            return false;

        Event event = (Event) o;

        try {
            long diffStart = Util.getDateDiff(this.start, event.getStart(), TimeUnit.MINUTES);
            long diffEnd = Util.getDateDiff(this.end, event.getEnd(), TimeUnit.MINUTES);

            return this.title.equals(event.title) &&
                    this.description.equals(event.description) &&
                    diffStart < 5 && diffEnd < 5;
        } catch (NullPointerException e) {
            LOG.warn(e.getMessage());
        }


        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }
}
