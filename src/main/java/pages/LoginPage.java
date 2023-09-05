package pages;

import config.Driver;
import config.Page;
import org.openqa.selenium.By;

public class LoginPage extends Page {

    By popUpModalExitButton = By.id("exit");

    public void closeStartPopUp() {
        Driver.driver.findElement(popUpModalExitButton).click();
    }
}
