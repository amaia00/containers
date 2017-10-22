package fr.univlyon1.tiw.tiw1.calendar.tp2.config;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/22/17.
 */
public enum ConfigType {
    FILE_NAME("file-name"),
    PATH_FILE("path-file"),
    FORMAT_DATE("format-date");

    private String name;


    ConfigType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
