package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public enum ContextVariable {
    ENTITY("CalendarEntity"),
    DAO("CalendarDAO");

    private String variable;


    ContextVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}
