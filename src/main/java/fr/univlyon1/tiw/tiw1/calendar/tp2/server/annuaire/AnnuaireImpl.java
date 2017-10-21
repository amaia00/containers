package fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public class AnnuaireImpl extends Observable implements Annuaire{
    private Map<String, CalendarContext> registry;

    public AnnuaireImpl() {
        this.registry = new HashMap<>();
    }

    @Override
    public CalendarContext getRegistry(RegistryVariable variable) {
        return registry.get(variable.getContextName());
    }

    @Override
    public void setRegistry(RegistryVariable variable, CalendarContext context) {
        this.registry.put(variable.getContextName(), context);
    }

}
