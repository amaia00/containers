package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public enum Command {
    ADD_EVENT("add"),
    REMOVE_EVENT("remove"),
    INIT_EVENT("synchronize"),
    LIST_EVENTS("list"),
    FIND_EVENT("find");

    private String commande;


    Command(String commande) {
        this.commande = commande;
    }

    public String getCommande() {
        return commande;
    }
}
