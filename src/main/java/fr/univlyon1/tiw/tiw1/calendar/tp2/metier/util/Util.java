package fr.univlyon1.tiw.tiw1.calendar.tp2.metier.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/22/17.
 */
public class Util {

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
