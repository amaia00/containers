package fr.univlyon1.tiw.tiw1.calendar.dao;

public class CalendarNotFoundException extends Exception {
    
    public CalendarNotFoundException() {
        super();
    }

    public CalendarNotFoundException(String message) {
        super(message);
    }

    public CalendarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarNotFoundException(Throwable cause) {
        super(cause);
    }
}
