package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Driver {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void printWindowSize() {
        // Using Selenium API
        Dimension size = driver.get().manage().window().getSize();
        System.out.println("Window size via Selenium API: " + size.width + "x" + size.height);

        // Using JavaScript (actual viewport)
        JavascriptExecutor js = (JavascriptExecutor) driver.get();
        Long width = (Long) js.executeScript("return window.innerWidth;");
        Long height = (Long) js.executeScript("return window.innerHeight;");
        System.out.println("Inner viewport size via JS: " + width + "x" + height);
    }

    public static void initDriver() {

        final Object[] driverConfig = DriverManager.driverConfig.get();

        final String windowSizeInner = (Boolean)driverConfig[0] ? "--window-size=500,900" : "--window-size=1920,1080";
        final Dimension windowSize = (Boolean)driverConfig[0] ? new Dimension(500, 900) : new Dimension(1920, 1080);
        final String browserName = (String)driverConfig[1];
        final boolean headlessMode = (Boolean)driverConfig[2];

        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            if(headlessMode) options.addArguments("--headless");
            options.addArguments(windowSizeInner);
            driver.set(new ChromeDriver(options));
        }
        else if(browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            if(headlessMode) options.addArguments("--headless");
            options.addArguments(windowSizeInner);
            driver.set(new FirefoxDriver(options));
        }
        else if(browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            if(headlessMode) options.addArguments("--headless");
            options.addArguments(windowSizeInner);
            driver.set(new EdgeDriver(options));
        }

        driver.get().manage().window().setSize(windowSize);
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}