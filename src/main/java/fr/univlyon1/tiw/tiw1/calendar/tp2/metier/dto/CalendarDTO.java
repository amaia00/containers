package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/11/17.
 */
public class CalendarDTO implements Serializable {
    private static final long serialVersionUID = -325556009490888411L;
    private Collection<EventDTO> events = new ArrayList<>();

    public CalendarDTO() {
        /* On n'utilise pas encore */
    }

}
