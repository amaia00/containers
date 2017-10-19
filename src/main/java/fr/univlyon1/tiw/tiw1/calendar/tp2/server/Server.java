package fr.univlyon1.tiw.tiw1.calendar.tp2.server;

import fr.univlyon1.tiw.tiw1.calendar.tp2.config.Config;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dao.XMLCalendarDAO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.Calendar;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/17.
 */
public class Server {

    public Calendar CALENDAR;

    public Server (Config config) {
        MutablePicoContainer configContainer = new DefaultPicoContainer();
        configContainer.addComponent(config);
        configContainer.addComponent(SimpleDateFormat.class);

        MutablePicoContainer xmlContainer = new DefaultPicoContainer(configContainer);
        xmlContainer.addComponent(XMLCalendarDAO.class);

        MutablePicoContainer serverContainer = new DefaultPicoContainer().as(Characteristics.CACHE);
        serverContainer.addComponent(ArrayList.class);
        serverContainer.addComponent(Calendar.class);

        CALENDAR = serverContainer.getComponent(Calendar.class);
        CALENDAR.start();
    }

    public Calendar getCalendar() {
        return CALENDAR;
    }
}
