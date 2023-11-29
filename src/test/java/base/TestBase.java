package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.Driver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    protected Properties testData;

    @Parameters({"browser", "mobile"})
    @BeforeClass
    public void setUp(String browser, boolean mobile) {
        new Driver(browser, mobile);
        try {
            FileInputStream fileReader = new FileInputStream("src/main/data/main_data.properties");
            testData = new Properties();
            testData.load(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        Driver.getDriver().quit();
    }
}