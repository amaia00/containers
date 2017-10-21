package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public enum Command {
    ADD_EVENT("CalendarAdd"),
    REMOVE_EVENT("CalendarRemove"),
    SYNC_EVENTS("CalendarSync"),
    LIST_EVENTS("CalendarList"),
    FIND_EVENT("CalendarFind");

    private String commande;


    Command(String commande) {
        this.commande = commande;
    }

    public String getCommande() {
        return commande;
    }
}
