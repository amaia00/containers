package fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public class Annuaire extends Observable {
    private Map<String, Object> registry;

    public Annuaire() {
        this.registry = new HashMap<>();
    }

    public Object getRegistry(RegistryVariable variable) {
        return registry.get(variable.getContextName());
    }

    public void setRegistry(RegistryVariable variable, Object context) {
        setRegistry(variable.getContextName(), context);
    }

    public void setRegistry(String variable, Object context) {
        this.registry.put(variable, context);
        setChanged();
        notifyObservers(context);
    }
}
