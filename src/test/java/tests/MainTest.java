package tests;

import base.TestBase;
import helpers.MainHelpers;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.*;
import utils.Driver;
import utils.EnvConfig;
import java.time.Duration;

import static org.testng.Assert.*;

public class MainTest extends TestBase {

    private final LoginPage loginPage = new LoginPage();
    private MainPage mainPage;
    private AddFriendPage addFriendPage;
    private ProfilePage profilePage;
    private final ChatPage chatPage = new ChatPage();
    private UserChatPage userChatPage;
    private GroupChatPage groupChatPage;

    @Test(priority = 1, description = "Test the signup flow")
    public void testSignup() {
        loginPage.navigateToLoginPage(EnvConfig.get("AC_URL"));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(loginPage.getPopUpExitButton()));
        loginPage.clickOnPopUpExitButton();
        loginPage.switchToSignup();
        assertTrue(loginPage.isSignupBoxDisplayed());
        loginPage.switchToLogin();
    }

    @Test(priority = 2, description = "Test the login flow")
    public void testLoginPage() {
        assertEquals(loginPage.getLoginPageTitle(), EnvConfig.get("AC_TITLE"));
        loginPage.setUsernameField(EnvConfig.get("AC_USERNAME"));
        loginPage.setPasswordField(EnvConfig.get("AC_PASSWORD"));
        mainPage = loginPage.clickOnLoginButton();
        mainPage.waitForLoading();
    }

    @Test(priority = 3, description = "Test the main page after the loading screen")
    public void testMainPage() {
        // Done waiting for the loading screen to be disappeared, and we can check the main page
        //If on mobile view, there's a menu we need to click on to continue our tests
        MainHelpers.ifOnMobileViewClickMenu(mainPage);
        assertTrue(mainPage.getAddNewFriendLink().isDisplayed());
        assertTrue(mainPage.getEditProfileLink().isDisplayed());
        assertTrue(mainPage.getUsernameLabel().isDisplayed());
        assertTrue(mainPage.verifyUsername(EnvConfig.get("AC_USERNAME")));
        MainHelpers.ifOnMobileViewCloseMenu(mainPage);
    }

//    @Test(priority = 4, description = "Test the add a new friend functionality")
//    public void testAddFriend() {
//        mainPage.removeFriendIfExists(EnvConfig.get("AC_FRIEND_USERNAME"));
//        mainPage.waitForLoading();
//        MainHelpers.ifOnMobileViewClickMenu(mainPage);
//        addFriendPage = mainPage.clickOnAddNewFriendButton();
//        addFriendPage.typeUsername(EnvConfig.get("AC_FRIEND_USERNAME"));
//        addFriendPage.verifySuggestionBox(EnvConfig.get("AC_FRIEND_USERNAME"));
//        addFriendPage.clickOnAddButton();
//        MainHelpers.waitFor(2);
//        mainPage.waitForLoading();
//        assertEquals(addFriendPage.returnAddNewFriendResult(), "Sent Friend Request");
//    }
//
//    @Test(priority = 5, description = "Test the accept friend request")
//    public void testAcceptFriendRequest() {
//        MainHelpers.waitFor(2);
//        page.goBack();
//        mainPage.waitForLoading();
//        MainHelpers.ifOnMobileViewClickMenu(mainPage);
//        mainPage.logout();
//        mainPage.waitForLoading();
//        loginPage.clickOnPopUpExitButton();
//        loginPage.setUsernameField(EnvConfig.get("AC_FRIEND_USERNAME"));
//        loginPage.setPasswordField(EnvConfig.get("AC_PASSWORD"));
//        mainPage = loginPage.clickOnLoginButton();
//        mainPage.waitForLoading();
//        MainHelpers.ifOnMobileViewClickMenu(mainPage);
//        mainPage.clickOnNotificationsButton();
//        mainPage.acceptFriendRequestFrom(EnvConfig.get("AC_USERNAME"));
//        mainPage.waitForLoading();
//        assertTrue(mainPage.verifyNewFriend(EnvConfig.get("AC_USERNAME")));
//    }
//
//    @Test(priority = 6, description = "Test edit user profile picture")
//    public void testUserEditProfilePicture() {
//        MainHelpers.ifOnMobileViewClickMenu(mainPage);
//        profilePage = mainPage.clickOnEditProfileButton();
//        profilePage.verifyUserPicture(EnvConfig.get("AC_FRIEND_USERNAME"));
//        profilePage.verifyUsername(EnvConfig.get("AC_FRIEND_USERNAME"));
//        profilePage.clickOnChangePictureButton();
//        profilePage.uploadPicture("src/main/data/files/profile_picture.png");
//        profilePage.clickOnProfilePictureSubmitButton();
//        mainPage.waitForLoading();
//        assertEquals(profilePage.returnSubmitPictureMessage(), "Successfully Changed");
//    }
//
//    @Test(priority = 7, description = "Test edit user password")
//    public void testUserChangePassword() {
//        String password = EnvConfig.get("AC_PASSWORD");
//        page.goBack();
//        profilePage.clickOnChangePasswordButton();
//        profilePage.typeCurrentPassword(password);
//        profilePage.typeNewPassword(password);
//        profilePage.typeConfirmNewPassword(password);
//        profilePage.submitNewPassword();
//        assertEquals(MainHelpers.returnWindowAlertBoxText(), "Successfully changed password :)");
//    }
//
//    @Test(priority = 8, description = "Test user chat")
//    public void testUserChat() {
//        page.goBack();
//        page.goBack();
//        mainPage.waitForLoading();
//        String message = "Hello, this is test automation message";
//        userChatPage = mainPage.clickChatForFriend(EnvConfig.get("AC_USERNAME"));
//        int currentMessagesCount = chatPage.returnCurrentMessagesCount();
//        chatPage.typeMessage(message);
//        chatPage.clickOnSendButton();
//        chatPage.verifySentMessage(message);
//        int messageCountAfter = chatPage.returnCurrentMessagesCount();
//        assertEquals(messageCountAfter, currentMessagesCount + 1);
//    }
//
//    @Test(priority = 9, description = "Test create group chat")
//    public void testCreateGroupChat() {
//        page.goBack();
//        mainPage.waitForLoading();
//        mainPage.clickOnCreateGroupButton();
//        String groupName = "testAuto";
//        mainPage.typeGroupInfo(groupName, "src/main/data/files/profile_picture.png");
//        mainPage.clickOnCreateGroupSubmitButton();
//        assertEquals(MainHelpers.returnWindowAlertBoxText(), String.format("Successfully created group: %s", groupName));
//        mainPage.waitForLoading();
//        assertTrue(mainPage.verifyGroupHasBeenCreated(groupName));
//    }
//
//    @Test(priority = 10, description = "Test group chat")
//    public void testGroupChat() {
//        String groupName = "testAuto";
//        groupChatPage = mainPage.clickEnterForGroup(groupName);
//        String message = "Hello, this is test automation message";
//        int currentMessagesCount = chatPage.returnCurrentMessagesCount();
//        chatPage.typeMessage(message);
//        chatPage.clickOnSendButton();
//        chatPage.verifySentMessage(message);
//        int messageCountAfter = chatPage.returnCurrentMessagesCount();
//        assertEquals(messageCountAfter, currentMessagesCount + 1);
//
//        // Test changing group picture
//        groupChatPage.clickOnGroupSettingsButton();
//        groupChatPage.clickOnEditPictureButton();
//        groupChatPage.uploadPicture("src/main/data/files/profile_picture.png");
//        groupChatPage.clickOnChangePictureButton();
//        assertEquals(MainHelpers.returnWindowAlertBoxText(), "Successfully Changed");
//
//        // Test invite friend to group
//        groupChatPage.clickOnAddPeopleButton();
//        groupChatPage.addFriendToGroup(EnvConfig.get("AC_USERNAME"));
//        groupChatPage.removeFriendFromGroup(EnvConfig.get("AC_USERNAME"));
//
//        // Test delete group
//        groupChatPage.destroyGroup();
//        mainPage.waitForLoading();
//        assertTrue(mainPage.verifyGroupHasBeenDeleted(groupName));
//    }
}