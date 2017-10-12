package fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.dto.EventDTO;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(propOrder = {"title", "start", "end", "description"})
public class Event {
    private String title;
    private String description;
    private Date start;
    private Date end;
    private String id;

    public Event() {

    }

    public Event(EventDTO eventDTO, Date start, Date end) {
        this.title = eventDTO.getTitle();
        this.description = eventDTO.getDescription();
        this.id = eventDTO.getId();
        this.start = start;
        this.end = end;
    }

    public Event(String title, String description, Date start, Date end, String id) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.id = id;
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

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Pour la comparaison dans la suppression
    // On approxime les dates à la précision du format près, ça suffit bien...
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (this.getClass() != o.getClass())
            return false;

        Event event = (Event) o;

        long durationStart = this.start.getTime() - event.getStart().getTime();
        long durationEnd = this.end.getTime() - event.getEnd().getTime();

        // TODO comparaison de temps
        return this.title.equals(event.title) &&
                this.description.equals(event.description);
    }
}
