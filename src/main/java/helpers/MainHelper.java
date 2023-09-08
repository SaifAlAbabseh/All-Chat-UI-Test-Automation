package helpers;

import config.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import java.time.Duration;
import java.util.List;
import org.testng.Assert;

public class MainHelper {

    private LoginPage loginPage = new LoginPage();
    private MainPage mainPage = new MainPage();

    private WebDriverWait wait;

    public void loginPopupFlow() {
        List<WebElement> popUpElements = loginPage.getPopupElements();
        for (WebElement element: popUpElements)
            Assert.assertTrue(element.getText().contains("Desktop Version Download") || element.getText().contains("Android Version Download"));
        loginPage.closeStartPopUp();
    }

    public void setLoginFields(String username, String password) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
    }

    public void invalidLoginFlow(String username, String password) {
        setLoginFields(username, password);
        loginPage.clickOnLoginButtonForInvalid();
        wait = new WebDriverWait(Driver.driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginPage.getLoadingElement()));
        Assert.assertTrue(loginPage.isInvalidLogin());
    }

    public MainPage loginFlow(String username, String password) {
        setLoginFields(username, password);
        return loginPage.clickOnLoginButton();
    }

    public void afterLogoutFlow(LoginPage loginPage) {
        this.loginPage = loginPage;
        wait = new WebDriverWait(Driver.driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginPage.getLoadingElement()));
        loginPage.closeStartPopUp();
    }
}
