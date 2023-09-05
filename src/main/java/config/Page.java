package config;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Page{

    public void waitForVisibilityOf(WebElement element, Duration seconds) {
        WebDriverWait wait = new WebDriverWait(Driver.driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
