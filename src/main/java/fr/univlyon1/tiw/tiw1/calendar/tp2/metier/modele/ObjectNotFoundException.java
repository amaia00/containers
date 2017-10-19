package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/12/17.
 */
public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
