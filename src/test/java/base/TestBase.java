package base;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.Driver;
import utils.ScreenActions;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class TestBase {

    protected Properties testData;

    @Parameters({"browser", "mobile", "includeAudio"})
    @BeforeClass(description = "Initialize the web driver, load the test data, and start recording a video")
    public void setUp(String browser, boolean mobile, boolean includeAudio) {
        new Driver(browser, mobile);
        loadTestData();
        //ScreenActions.startVideoRecording(this.getClass().getName(), includeAudio);
    }

    @Parameters({"browser", "mobile"})
    @AfterMethod(description = "Take a screenshot for every failed test case")
    public void createScreenshotOnFailure(ITestResult result, Method method, String browser, boolean mobile) {
        if(!result.isSuccess()) {
            System.out.println("SaifSaifSaifSaifSaif");
            String whichPlatform = (mobile)?"mobile view":"desktop view";
            ScreenActions.takeScreenshot(browser, whichPlatform, method.getName());
        }
    }

    @AfterClass(description = "Stop the video record and quit from the web driver")
    public void tearDown() {
        //ScreenActions.stopVideoRecording();
        Driver.getDriver().quit();
    }

    private void loadTestData() {
        try {
            FileInputStream fileReader = new FileInputStream("src/main/data/main_data.properties");
            testData = new Properties();
            testData.load(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}