package fr.univlyon1.tiw.tiw1.calendar.tp2.server.context;

import java.io.InvalidClassException;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public interface CalendarContext {

    Object getContextVariable(ContextVariable variable) throws InvalidClassException;

    Object getContextVariable(String variable) throws InvalidClassException;

    void setContextVariable(ContextVariable variable, Object contextVariable);

    void setContextVariable(String variable, Object contextVariable);
}
