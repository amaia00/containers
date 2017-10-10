package fr.univlyon1.tiw.tiw1.calendar;

import fr.univlyon1.tiw.tiw1.calendar.modele.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUI {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarUI.class);
    private static final String defaultDirectory;

    static {
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
            defaultDirectory = "/C:/temp/";
        } else {
            defaultDirectory = "/tmp";
        }
    }

    //Variable globale (singleton)
    static Calendar calendar;

    private static Date parseDate(String s) throws ParseException {
        return new SimpleDateFormat(Config.defaultCfg.getProperty(Config.DATE_FORMAT)).parse(s);
    }

    public static void  main (String args[]) {
        calendar = new Calendar("My calendar", new File(defaultDirectory));
        String title = null;
        Date start = null;
        Date end = null;
        String desc = null;

        for (;;) {
            menu();
            int c = new String (readLine()).charAt(0) - '0';
            switch (c){
                case 1:
                    try {
                        System.out.print("Title: ");
                        title = readLine();
                        System.out.print("Description: ");
                        desc = readLine();
                        System.out.print("Start: ");
                        start = parseDate(readLine());
                        System.out.print("End: ");
                        end = parseDate(readLine());
                    } catch (ParseException e) {
                        start = new Date();
                        end = start;
                    } catch (Exception e) {
                        LOG.error("Error in Add menu.", e);
                    }

                    calendar.addEvent(title, start, end, desc);
                    break;

                case 2:
                    try {
                        System.out.print("Title: ");
                        title = readLine();
                        System.out.print("Description: ");
                        desc = readLine();
                        System.out.print("Start: ");
                        start = parseDate(readLine());
                        System.out.print("End: ");
                        end = parseDate(readLine());
                    } catch (ParseException e) {
                        start = new Date();
                        end = start;
                    } catch (Exception e) {
                        LOG.error("Error in Delete menu.", e);
                    }

                    calendar.removeEvent(title, start, end, desc);
                    break;

                case 3:
                    System.out.println("Events in calendar:\n");
                    System.out.println(calendar.getInfos());
                    break;

                case 4:
                    calendar.synchronizeEvents();
                    System.out.println("Event list synchronized with DAO.\n\n");
                    break;

                case 5:
                    return;
            }
        }
    }

    public static void menu() {
        System.out.println("Menu\n\n");
        System.out.println("1)\tAdd an event\n");
        System.out.println("2)\tDelete an event\n");
        System.out.println("3)\tList all events\n");
        System.out.println("4)\tRe-initialize calendar\n");
        System.out.println("5)\tExit\n");
    }

//	 ---------------------------------------------
//   Code trouve a : http://www.wellho.net/resources/ex.php4?item=j703/WellHouseInput.java
    public static String readLine() {
        BufferedReader standard = new BufferedReader(new InputStreamReader(System.in));
        try{
            String inline = standard.readLine();
            return inline;
        } catch (Exception e) {
            return ("data entry error");
        }
    }
}