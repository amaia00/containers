package fr.univlyon1.tiw.tiw1.calendar.tp2.client;

import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.dto.EventDTO;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.modele.ObjectNotFoundException;
import fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util.Command;
import fr.univlyon1.tiw.tiw1.calendar.tp2.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CalendarUI {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarUI.class);
    private Server calendarServerImpl;

    public CalendarUI(Server serverImpl) {
        this.calendarServerImpl = serverImpl;
    }

    public void start() {
        while (true) {
            CalendarUI.showMenu();
            int option = CalendarUI.readLine().charAt(0) - '0';

            if (option == 5)
                return;
            try {
                this.processOption(option);
            } catch (ObjectNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processOption(int option) throws ObjectNotFoundException {
        String title;
        String description;
        String start;
        String end;

        switch (option) {
            case 1:
            case 2:
                System.out.print("Title: ");
                title = readLine();
                System.out.print("Description: ");
                description = readLine();
                System.out.print("Start: ");
                start = readLine();
                System.out.print("End: ");
                end = readLine();

                EventDTO eventDTO = new EventDTO(title, description, start, end, null);

                if (option == 1) calendarServerImpl.processRequest(Command.ADD_EVENT, eventDTO);
                else {
                    try {
                        calendarServerImpl.processRequest(Command.REMOVE_EVENT, eventDTO);
                    } catch (ObjectNotFoundException e) {
                        LOG.warn(e.getMessage());
                    }
                }
                break;

            case 3:
                System.out.println("Events in calendar:\n");
                System.out.println(calendarServerImpl.processRequest(Command.LIST_EVENTS));
                break;

            case 4:
                calendarServerImpl.processRequest(Command.INIT_EVENT);
                System.out.println("Event list synchronized with DAO.\n\n");
                break;

            default:
                break;
        }

    }

    private static void showMenu() {
        System.out.println("Menu\n\n");
        System.out.println("1)\tAdd an event\n");
        System.out.println("2)\tDelete an event\n");
        System.out.println("3)\tList all events\n");
        System.out.println("4)\tRe-initialize calendar\n");
        System.out.println("5)\tExit\n");
    }

    //	 ---------------------------------------------
    //   Code trouve a : http://www.wellho.net/resources/ex.php4?item=j703/WellHouseInput.java
    private static String readLine() {
        BufferedReader standard = new BufferedReader(new InputStreamReader(System.in));
        try {
            return standard.readLine();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ("data entry error");
        }
    }
}