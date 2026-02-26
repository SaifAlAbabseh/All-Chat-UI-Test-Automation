package tests;

import base.TestBase;
import helpers.MainHelpers;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.AddFriendPage;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import utils.Driver;
import utils.Page;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.testng.Assert.*;

public class MainTest extends TestBase {

    private final LoginPage loginPage = new LoginPage();
    private MainPage mainPage;
    private AddFriendPage addFriendPage;
    private ProfilePage profilePage;

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
        mainPage.waitForLoading();
    }

    @Test(priority = 3, description = "Test the main page after the loading screen")
    public void testMainPage() {
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
        mainPage.waitForLoading();
        addFriendPage = mainPage.clickOnAddNewFriendButton();
    }

    @Test(priority = 4, description = "Test the add a new friend functionality")
    public void testAddFriend() {
        addFriendPage.typeUsername(testData.getProperty("friendUsername"));
        addFriendPage.verifySuggestionBox(testData.getProperty("friendUsername"));
        addFriendPage.clickOnAddButton();
        assertEquals(addFriendPage.returnAddNewFriendResult(), "Sent Friend Request");
    }

    @Test(priority = 5, description = "Test the accept friend request")
    public void testAcceptFriendRequest() {
        page.goBack();
        mainPage.waitForLoading();
        mainPage.logout();
        mainPage.waitForLoading();
        loginPage.clickOnPopUpExitButton();
        loginPage.setUsernameField(testData.getProperty("friendUsername"));
        loginPage.setPasswordField(testData.getProperty("password"));
        mainPage = loginPage.clickOnLoginButton();
        mainPage.waitForLoading();
        mainPage.clickOnNotificationsButton();
        mainPage.acceptFriendRequestFrom(testData.getProperty("username"));
        mainPage.waitForLoading();
        assertTrue(mainPage.verifyNewFriend(testData.getProperty("username")));
    }

    @Test(priority = 6, description = "Test edit user profile picture")
    public void testUserEditProfilePicture() {
        profilePage = mainPage.clickOnEditProfileButton();
        profilePage.verifyUserPicture(testData.getProperty("friendUsername"));
        profilePage.verifyUsername(testData.getProperty("friendUsername"));
        profilePage.clickOnChangePictureButton();
        profilePage.uploadPicture();
        profilePage.clickOnProfilePictureSubmitButton();
        mainPage.waitForLoading();
        assertEquals(profilePage.returnSubmitPictureMessage(), "Successfully Changed");
    }

    @Test(priority = 7, description = "Test edit user password")
    public void testUserChangePassword() {
        String password = testData.getProperty("password");
        page.goBack();
        profilePage.clickOnChangePasswordButton();
        profilePage.typeCurrentPassword(password);
        profilePage.typeNewPassword(password);
        profilePage.typeConfirmNewPassword(password);
        profilePage.submitNewPassword();
        assertEquals(MainHelpers.returnWindowAlertBoxText(), "Successfully changed password :)");
    }
}