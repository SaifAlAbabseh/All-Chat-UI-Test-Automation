package base;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.Driver;
import utils.Page;
import utils.ScreenActions;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public class TestBase {

    protected final Page page = new Page();
    private final boolean mobileMode = Boolean.parseBoolean(System.getProperty("mobileMode"));
    private final String browser = System.getProperty("browser");

    @BeforeClass(description = "Initialize the web driver, load the test data, and start recording a video")
    public void setUp() throws MalformedURLException {
        new Driver(browser, mobileMode);
        Driver.printWindowSize();
//        ScreenActions.startVideoRecording(this.getClass().getName(), false);
    }

    @AfterMethod(description = "Take a screenshot for every failed test case")
    public void createScreenshotOnFailure(ITestResult result, Method method) {
        if(!result.isSuccess()) {
            boolean mobile = Boolean.parseBoolean(System.getProperty("mobileMode"));
            String whichPlatform = (mobile)?"mobile view":"desktop view";
            ScreenActions.takeScreenshot(browser, whichPlatform, method.getName());
        }
    }

    @AfterClass(description = "Stop the video record and quit from the web driver")
    public void tearDown() {
//        ScreenActions.stopVideoRecording();
        Driver.getDriver().quit();
    }
}