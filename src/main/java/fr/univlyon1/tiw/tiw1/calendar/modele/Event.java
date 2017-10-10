package fr.univlyon1.tiw.tiw1.calendar.modele;

import fr.univlyon1.tiw.tiw1.calendar.Config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
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

    private String formatDate(Date d) {
        return new SimpleDateFormat(Config.defaultCfg.getProperty(Config.DATE_FORMAT)).format(d);
    }

    public String getInfos() {
        String infos = "Title: " + getTitle();
        infos += "\nDescription: " + getDescription();
        infos += "\nStart: " + formatDate(getStart());
        infos += "\nEnd: " + formatDate(getEnd());
        return infos;
    }

    // Pour la comparaison dans la suppression
    //On approxime les dates à la précision du format près, ça suffit bien...
    @Override
    public boolean equals(Object o) {
        Event e = (Event) o;
        return this.title.equals(e.title) && this.description.equals(e.description)
                && formatDate(this.start).equals(formatDate(e.start)) && formatDate(this.end).equals(formatDate(e.end));
    }
}
