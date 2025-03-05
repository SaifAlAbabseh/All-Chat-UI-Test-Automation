package pages;

import org.openqa.selenium.By;
import utils.Page;

public class LoginPage extends Page {

    private final By popUpExitButton = By.id("exit"),
               usernameInputField = By.id("username_inputfield"),
               passwordInputField = By.id("userpassword_field"),
               loginButton = By.id("login_buttontag");

    public void navigateToLoginPage(String URL) {
        visit(URL);
    }

    public String getLoginPageTitle() {
        return getPageTitle();
    }

    public void clickOnPopUpExitButton() {
        findElementBy(popUpExitButton).click();
    }

    public By getPopUpExitButton() {
        return popUpExitButton;
    }

    public void setUsernameField(String username) {
        findElementBy(usernameInputField).sendKeys(username);
    }

    public void setPasswordField(String password) {
        findElementBy(passwordInputField).sendKeys(password);
    }

    public MainPage clickOnLoginButton() {
        findElementBy(loginButton).click();
        return new MainPage();
    }
}
