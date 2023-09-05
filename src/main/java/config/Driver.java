package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    public static WebDriver driver;

    public Driver() {
        //setOptions(new String[] {"--headless"});
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private ChromeOptions setOptions(String[] options) {
        ChromeOptions chromeOptions = new ChromeOptions();
        for (int i = 0; i < options.length; i++)
            chromeOptions.addArguments(options[i]);
        return chromeOptions;
    }
}
