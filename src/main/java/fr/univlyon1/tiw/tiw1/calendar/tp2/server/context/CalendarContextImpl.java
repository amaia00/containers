package fr.univlyon1.tiw.tiw1.calendar.tp2.server.context;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public class CalendarContextImpl implements CalendarContext{
    private Map<String, Object> contextVariables;

    public CalendarContextImpl() {
        this.contextVariables = new HashMap<>();
    }

    @Override
    public Object getContextVariable(ContextVariable variable) throws InvalidClassException {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();

        String className = elements[2].getClassName();

        // FIXME: remove naivemethodaccessor (?) for test purposes
        if (className.matches("(.*).Calendar(Add|Remove|List|Find|Sync)") || className.contains("Main") || className.equals("sun.reflect.NativeMethodAccessorImpl"))
            return contextVariables.get(variable.getVariable());

        throw new InvalidClassException("The caller class should be a calendar. Founded class: ".concat(className) );
    }

    @Override
    public void setContextVariable(ContextVariable variable, Object contextVariable) {
        this.contextVariables.put(variable.getVariable(), contextVariable);
    }
}
