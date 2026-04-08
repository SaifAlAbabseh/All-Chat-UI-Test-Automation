package utils;

public class DriverManager {

    public static final ThreadLocal<Object[]> driverConfig = new ThreadLocal<>();

    public static void setUp(String mobileMode, String browser, String headlessMode) {
        driverConfig.set(new Object[]{
                Boolean.parseBoolean(mobileMode != null ? mobileMode : System.getProperty("mobileMode")),
                browser != null ? browser : System.getProperty("browser"),
                Boolean.parseBoolean(headlessMode != null ? headlessMode : System.getProperty("headlessMode"))
        });
    }
}
