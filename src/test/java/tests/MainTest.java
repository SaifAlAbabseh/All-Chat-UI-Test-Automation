package tests;

import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.Driver;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.testng.Assert.*;

public class MainTest extends TestBase {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    @Test(priority = 1, description = "Test the login flow")
    public void testLoginPage() {
        loginPage.navigateToLoginPage(testData.getProperty("URL"));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(loginPage.getPopUpExitButton()));
        assertEquals(loginPage.getLoginPageTitle(), testData.getProperty("title"));
        loginPage.clickOnPopUpExitButton();
        loginPage.setUsernameField(testData.getProperty("username"));
        loginPage.setPasswordField(testData.getProperty("password"));
        loginPage.clickOnLoginButton();
    }

    @Test(priority = 2, description = "Test the main page after the loading screen")
    public void testMainPage() {
        // Wait for the loading box to disappear
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.invisibilityOfElementLocated(mainPage.getLoadingBox()));
        // Done waiting for the loading screen to be disappeared, and we can check the main page
        //If on mobile view, there's a menu we need to click on to continue our tests
        if(mainPage.isMenuDisplayed()) {
            mainPage.clickOnMenu();
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.visibilityOfElementLocated(mainPage.getAddNewFriendLink2()));
        }
        assertTrue(mainPage.getAddNewFriendLink().isDisplayed());
        assertTrue(mainPage.getEditProfileLink().isDisplayed());
        assertTrue(false);
        //mainPage.getUsernameLabel().isDisplayed()
    }
}