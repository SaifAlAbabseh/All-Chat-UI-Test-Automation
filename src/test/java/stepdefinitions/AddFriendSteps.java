package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.AddFriendPage;
import pages.MainPage;
import utils.EnvConfig;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AddFriendSteps {

    private final MainPage mainPage = new MainPage();
    private AddFriendPage addFriendPage;

    @When("user sees friend is already added and remove them")
    public void removeAlreadyAddedFriend(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        mainPage.removeFriendIfExists(friendUsername);
        mainPage.waitForLoading();
    }

    @And("clicks on add friend link")
    public void clickOnAddFriendLink() {
        MainHelpers.ifOnMobileViewClickMenu(mainPage);
        addFriendPage = mainPage.clickOnAddNewFriendButton();
    }

    @And("types friend username")
    public void typeFriendUsername(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        addFriendPage.typeUsername(friendUsername);
    }

    @And("picks friend from suggestions box")
    public void pickSuggestedUser(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        addFriendPage.verifySuggestionBox(friendUsername);
    }

    @And("clicks on the add button")
    public void clickOnAddButton() {
        addFriendPage.clickOnAddButton();
        MainHelpers.waitFor(2);
        mainPage.waitForLoading();
    }

    @Then("user should see sent friend request message")
    public void verifySuccessfulSentFriendRequest(DataTable table) {
        assertEquals(addFriendPage.returnAddNewFriendResult(), "Sent Friend Request");
        // Verify friend request email has been sent to the user
        String expectedEmailSubject = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "subject"));
        String body = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "body"));
        String username = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        String friendEmail = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "email"));
        String appPassword = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "app_password"));

        String expectedBody = String.format(body, username);
        MainHelpers.verifyEmail(friendEmail, appPassword, expectedEmailSubject, 30, expectedBody);
    }

    @And("clicks on the notifications button")
    public void clickOnNotificationsButton() {
        MainHelpers.ifOnMobileViewClickMenu(mainPage);
        mainPage.clickOnNotificationsButton();
    }

    @And("clicks accept for the friend request from user")
    public void acceptFriendRequest(DataTable table) {
        String requesterUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        mainPage.acceptFriendRequestFrom(requesterUsername);
        mainPage.waitForLoading();
    }

    @Then("user sees the new added friend")
    public void verifyNewAddedFriend(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        assertTrue(mainPage.verifyNewFriend(friendUsername), String.format("Expected to see the new friend <%s> be visible on main page.", friendUsername));
    }
}
