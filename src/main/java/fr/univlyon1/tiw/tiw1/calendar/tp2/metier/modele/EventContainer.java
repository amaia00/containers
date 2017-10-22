package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele;

import fr.univlyon1.tiw.tiw1.calendar.tp2.server.annuaire.Annuaire;
import org.picocontainer.ComponentFactory;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AnnotatedFieldInjection;

import java.util.*;


/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/22/17.
 */
public class EventContainer extends DefaultPicoContainer implements  Observer {
    private Annuaire annuaire;
    private static final String EVENT = "Event";


    public EventContainer(ComponentFactory componentFactory, PicoContainer parent, Annuaire annuaire) {
        super(componentFactory, parent);
        this.annuaire = annuaire;
    }

    public EventContainer(DefaultPicoContainer parent, Annuaire annuaire) {
        super(new Caching().wrap(new AnnotatedFieldInjection()), parent);
        this.annuaire = annuaire;
        annuaire.addObserver(this);
    }

    public boolean add (Event event) {
        stop();
        addComponent(EVENT + getComponents().size(), event);
        start();

        return true;
    }

    public int remove (Event event) {
        List<Object> eventList = getComponents();
        Event tmp= null;
        int i = 0;
        int affectedEvents = 0;

        stop();
        for (Iterator<Object> iterator = eventList.iterator(); iterator.hasNext(); tmp = (Event) iterator.next()) {
            if (event.equals(tmp)) {
                removeComponent(EVENT + i);
                /*
                 On coupe le lien entre le conteneur et la Event
                 Plus aucune référence n'existe sur cet objet
                 On attend que le garbage collecting passe...
                 */
                removeComponentByInstance(tmp);
                affectedEvents++;
            } else {
                /*
                Il faut changer les noms de référence des composants suivant ceux qu'on a enlevés,
                sans quoi on aura un problème pour en rajouter d'autres...
                */
                if (affectedEvents > 0) {
                    addComponent(EVENT + ( i - affectedEvents),
                            getComponent(EVENT + i));
                    removeComponent(EVENT + i);
                }
            }

            i++;
        }

        start();
        return affectedEvents;
    }

    public List<Event> list() {
        List<Event> eventList = new ArrayList<>();
        List<Object> components = getComponents();
        for (Object component: components) {
            Event tmp = (Event) component;
            eventList.add(tmp);
        }

        return eventList;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO: ??
    }
}
