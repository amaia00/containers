package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/17.
 */
public interface Server {

    /**
     *
     * @param command
     * @return
     */
    String processRequest(Command command) throws ObjectNotFoundException;


    /**
     *
     * @param command
     * @param object
     * @return
     */
    String processRequest(Command command, Object object) throws ObjectNotFoundException;
}
