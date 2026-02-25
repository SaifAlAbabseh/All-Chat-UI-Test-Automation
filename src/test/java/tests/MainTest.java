package tests;

import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.AddFriendPage;
import pages.LoginPage;
import pages.MainPage;
import utils.Driver;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.testng.Assert.*;

public class MainTest extends TestBase {

    private final LoginPage loginPage = new LoginPage();
    private MainPage mainPage;
    private AddFriendPage addFriendPage;

    @Test(priority = 1, description = "Test the signup flow")
    public void testSignup() {
        loginPage.navigateToLoginPage(testData.getProperty("URL"));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(loginPage.getPopUpExitButton()));
        loginPage.clickOnPopUpExitButton();
        loginPage.switchToSignup();
        assertTrue(loginPage.isSignupBoxDisplayed());
        loginPage.switchToLogin();
    }

    @Test(priority = 2, description = "Test the login flow")
    public void testLoginPage() {
        assertEquals(loginPage.getLoginPageTitle(), testData.getProperty("title"));
        loginPage.setUsernameField(testData.getProperty("username"));
        loginPage.setPasswordField(testData.getProperty("password"));
        mainPage = loginPage.clickOnLoginButton();
    }

    @Test(priority = 3, description = "Test the main page after the loading screen")
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
        assertTrue(mainPage.getUsernameLabel().isDisplayed());
        assertTrue(mainPage.verifyUsername(testData.getProperty("username")));
        mainPage.removeFriendIfExists(testData.getProperty("friendUsername"));
        addFriendPage = mainPage.clickOnAddNewFriendButton();
    }

    @Test(priority = 4, description = "Test the add a new friend functionality")
    public void testAddFriend() {
        addFriendPage.typeUsername(testData.getProperty("friendUsername"));
        addFriendPage.verifySuggestionBox(testData.getProperty("friendUsername"));
        addFriendPage.clickOnAddButton();
        assertEquals(addFriendPage.returnAddNewFriendResult(), "Sent Friend Request");
    }
}