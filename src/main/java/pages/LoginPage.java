package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;
import utils.Page;

import java.time.Duration;

public class LoginPage extends Page {

    private final By popUpExitButton = By.id("exit"),
               usernameInputField = By.id("username_inputfield"),
               passwordInputField = By.id("userpassword_field"),
               loginButton = By.id("login_buttontag"),
                signupSwitcherButton = By.xpath("//button[contains(., 'Create an account')]"),
                loginSwitcherButton = By.xpath("//button[contains(., '< Go back to login')]"),
                signupEmailField = By.id("signupemail_inputfield");

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

    public void switchToSignup() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(signupSwitcherButton));
        findElementBy(signupSwitcherButton).click();
    }

    public boolean isSignupBoxDisplayed() {
        return findElementBy(signupEmailField).isDisplayed();
    }

    public void switchToLogin() {
        findElementBy(loginSwitcherButton).click();
    }

    public MainPage clickOnLoginButton() {
        findElementBy(loginButton).click();
        return new MainPage();
    }
}
