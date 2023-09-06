package base;

import config.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void initDriver() {
        new Driver();
        Driver.driver.get("https://all-chat.000webhostapp.com/");
    }

    @AfterClass
    public void quitDriver() {
        Driver.driver.quit();
    }

}
