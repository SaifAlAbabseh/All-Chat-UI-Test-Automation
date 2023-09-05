package pages;

import config.Driver;
import config.Page;
import org.openqa.selenium.By;

public class LoginPage extends Page {

    private By popUpModalExitButton = By.id("exit"),
                                    usernameField = By.id("username_inputfield"),
                                    passwordField = By.id("userpassword_field"),
                                    loginButton = By.id("login_buttontag");

    public void closeStartPopUp() {
        Driver.driver.findElement(popUpModalExitButton).click();
    }
    public void setUsername(String username) {
        Driver.driver.findElement(usernameField).sendKeys(username);
    }
    public void setPassword(String password) {
        Driver.driver.findElement(passwordField).sendKeys(password);
    }
    public MainPage clickOnLoginButton() {
        Driver.driver.findElement(loginButton).click();
        return new MainPage();
    }
}
