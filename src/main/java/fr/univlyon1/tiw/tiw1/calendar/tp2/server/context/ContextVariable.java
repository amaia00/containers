package fr.univlyon1.tiw.tiw1.calendar.tp2.server.context;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public enum ContextVariable {
    ENTITY("CalendarEntity"),
    DAO("CalendarDAO"),
    CONFIG("Config"),
    REQUEST("Request");

    private String variable;


    ContextVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}
