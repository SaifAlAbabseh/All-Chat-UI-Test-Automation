package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Paths;

public class Driver {

    private static WebDriver driver;

    public Driver(String browserName, boolean headlessMode, boolean mobileMode) {
        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if(headlessMode) options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }
        else if(browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if(headlessMode) options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        }
        else if(browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if(headlessMode) options.addArguments("--headless=new");
            driver = new EdgeDriver(options);
        }
        Dimension windowSize = (mobileMode)?new Dimension(500, 900):new Dimension(1920, 1080);
        driver.manage().window().setSize(windowSize);
    }
    public static WebDriver getDriver() {
        return driver;
    }
}