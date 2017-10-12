package fr.univlyon1.tiw.tiw1.calendar.tp2.server.modele;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/12/17.
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException() {
        super();
    }

    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventNotFoundException(Throwable cause) {
        super(cause);
    }
}
