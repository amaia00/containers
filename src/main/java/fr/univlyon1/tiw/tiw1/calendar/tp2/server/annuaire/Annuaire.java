package fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.context.CalendarContext;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public interface Annuaire {

    CalendarContext getRegistry(RegistryVariable variable);

    void setRegistry(RegistryVariable variable, CalendarContext context);
}
