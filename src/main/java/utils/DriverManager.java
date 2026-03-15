package utils;

public class DriverManager {

    public static final boolean mobileMode = Boolean.parseBoolean(System.getProperty("mobileMode"));
    public static final String browserName = System.getProperty("browser");
    public static final boolean headlessMode = Boolean.parseBoolean(System.getProperty("headlessMode"));
}
