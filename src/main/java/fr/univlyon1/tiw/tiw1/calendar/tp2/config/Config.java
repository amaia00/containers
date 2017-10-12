package fr.univlyon1.tiw.tiw1.calendar.tp2.config;

import java.util.Properties;

public class Config {
    private Properties defaultCfg = new Properties();

    public static final String DATE_FORMAT = "date_format";
    public static final String DIRECTORY_NAME = "file_name";
    public static final String CALENDAR_NAME = "calendar_name";

    public Config(String name, String directory) {
        defaultCfg.setProperty(DATE_FORMAT, "EEE MMM dd kk:mm:ss zzz yyyy");
        defaultCfg.setProperty(DIRECTORY_NAME, directory);
        defaultCfg.setProperty(CALENDAR_NAME, name);
    }
    public Config(String name, String format, String directory) {
        //EEE MMM dd kk:mm:ss zzz yyyy
        defaultCfg.setProperty(DATE_FORMAT,format);
        defaultCfg.setProperty(DIRECTORY_NAME, directory);
        defaultCfg.setProperty(CALENDAR_NAME, name);
    }

    public void changeDateFormat(String format) {
        defaultCfg.setProperty(DATE_FORMAT, format);
    }

    public String getProperty(String property) {
        return defaultCfg.getProperty(property);
    }

}