package pages;

import config.Driver;
import config.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class LoginPage extends Page {

    private By popUpModalExitButton = By.id("exit"),
                                    usernameField = By.id("username_inputfield"),
                                    passwordField = By.id("userpassword_field"),
                                    loginButton = By.id("login_buttontag"),
                                    invalidLoginLabel = By.id("invalidforlogin"),
                                    popUpElements = By.className("otherPlatformDownloadLink"),
                                    loadingElement = By.id("loading_box_outer_id");


    public List<WebElement> getPopupElements() {
        return Driver.driver.findElements(popUpElements);
    }
    public void closeStartPopUp() {
        Driver.driver.findElement(popUpModalExitButton).click();
    }
    public void setUsername(String username) {
        Driver.driver.findElement(usernameField).sendKeys(username);
    }
    public void setPassword(String password) {
        Driver.driver.findElement(passwordField).sendKeys(password);
    }

    public boolean isInvalidLogin() {
        return Driver.driver.findElement(invalidLoginLabel).isDisplayed();
    }

    public void clickOnLoginButtonForInvalid() {
        Driver.driver.findElement(loginButton).click();
    }

    public MainPage clickOnLoginButton() {
        Driver.driver.findElement(loginButton).click();
        return new MainPage();
    }

    public By getPopUpModalExitButton() {
        return popUpModalExitButton;
    }

    public By getInvalidLoginLabel() {
        return invalidLoginLabel;
    }

    public By getLoginButton() {
        return loginButton;
    }

    public By getLoadingElement() {
        return loadingElement;
    }
}
