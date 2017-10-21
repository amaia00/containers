package fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public enum RegistryVariable {
    CONTEXT_ROOT("racine"),
    CONTEXT_APPLICATION("application"),
    CONTEXT_PERSISTENCE("persistence"),
    CONTEXT_BUSINESS("metier");

    private String context;


    RegistryVariable(String context) {
        this.context = context;
    }

    public String getContextName() {
        return context;
    }
}
