package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto;

import java.io.Serializable;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/12/17.
 */
public class EventDTO implements Serializable {
    private static final long serialVersionUID = 8426459855782732758L;

    private String title;
    private String description;
    private String start;
    private String end;
    private String id;


    /**
     * @param title
     * @param description
     * @param start
     * @param end
     * @param id
     */
    public EventDTO(String title, String description, String start, String end, String id) {
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nTitle: " + title +
                "\nDescription: " + description +
                "\nStart: " + start +
                "\nEnd: " + end;
    }
}
