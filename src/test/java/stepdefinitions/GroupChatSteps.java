package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.GroupChatPage;
import pages.MainPage;
import utils.EnvConfig;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GroupChatSteps {

    private final MainPage mainPage = new MainPage();
    private GroupChatPage groupChatPage;

    @When("user clicks on chat button for group")
    public void clickOnChatButtonForGroup(DataTable table) {
        String groupName = MainHelpers.getDataTableValueAsString(table, 0, "name");
        groupChatPage = mainPage.clickEnterForGroup(groupName);
    }

    @When("user admin clicks on group settings button")
    public void clickOnGroupSettingsButton() {
        groupChatPage.clickOnGroupSettingsButton();
    }

    @And("clicks on edit group picture button")
    public void clickOnEditGroupPictureButton() {
        groupChatPage.clickOnEditPictureButton();
    }

    @And("selects group picture")
    public void selectGroupNewPicture() {
        groupChatPage.uploadPicture("src/main/data/files/profile_picture.png");
    }

    @And("clicks on change group picture button")
    public void clickOnChangeGroupPictureSubmitButton() {
        groupChatPage.clickOnChangePictureButton();
    }

    @Then("user admin should see the successfully changed group picture message")
    public void verifySuccessfulChangedGroupPictureMessage() {
        assertEquals(MainHelpers.returnWindowAlertBoxText(), "Successfully Changed");
    }

    @When("user admin clicks on invite friends button")
    public void clickOnInviteFriendsButton() {
        groupChatPage.clickOnAddPeopleButton();
    }

    @And("clicks invite button for friend")
    public void clickOnInviteButtonForFriend(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        groupChatPage.addFriendToGroup(friendUsername);
    }

    @Then("user admin clicks on kick button and sees friend removed")
    public void clickOnKickButtonForFriend(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        groupChatPage.removeFriendFromGroup(friendUsername);
    }

    @And("clicks on delete group button")
    public void clickOnDeleteGroupButton() {
        groupChatPage.destroyGroup();
        mainPage.waitForLoading();
    }

    @Then("user admin do not see the group anymore")
    public void verifyDeletedGroup(DataTable table) {
        String groupName = MainHelpers.getDataTableValueAsString(table, 0, "name");
        assertTrue(mainPage.verifyGroupHasBeenDeleted(groupName), String.format("Expected that the group <%s> to be deleted from the groups container.", groupName));
    }
}
