package fr.univlyon1.tiw.tiw1.calendar;

import java.util.Properties;

public class Config {
    public static final Properties defaultCfg = new Properties();
    public static final String DATE_FORMAT = "date_format";

    static {
        defaultCfg.setProperty(DATE_FORMAT,"EEE MMM dd kk:mm:ss zzz yyyy");
    }
}