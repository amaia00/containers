package fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public enum RegistryVariable {
    CONTEXT_ROOT("server"),
    CONTEXT_APPLICATION("calendar"),
    CONTEXT_PERSISTENCE("dao"),
    CONTEXT_BUSINESS("events");

    private String context;


    RegistryVariable(String context) {
        this.context = context;
    }

    public String getContextName() {
        return context;
    }
}
