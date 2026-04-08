package hooks;

import io.cucumber.java.*;
import utils.Driver;
import utils.DriverManager;
import utils.ScreenActions;

public class BaseHooks {

    @Before
    public static void setUp() {
        try {
            Driver.initDriver();
            Driver.printWindowSize();
        }
        catch (Exception e) {
            System.err.println("Could not start web driver, ERROR: " + e.getMessage());
        }
//        ScreenActions.startVideoRecording(false);
    }

    @After(order = 1)
    public static void createScreenshotOnFailure(Scenario scenario) {
        if(scenario.isFailed()) {
            String whichPlatform = (Boolean)DriverManager.driverConfig.get()[0] ? "mobile view" : "desktop view";
            ScreenActions.takeScreenshot((String)DriverManager.driverConfig.get()[1], whichPlatform, scenario.getName());
        }
    }

    @After(order = 0)
    public static void tearDown() {
//        ScreenActions.stopVideoRecording();
        Driver.quitDriver();
    }
}
