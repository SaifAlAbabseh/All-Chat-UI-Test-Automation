package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.MainPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateChatGroup {

    private final MainPage mainPage = new MainPage();

    @When("user clicks on create group button")
    public void clickOnCreateGroupButton() {
        mainPage.clickOnCreateGroupButton();
    }

    @And("types group name and selects picture")
    public void typeGroupName(DataTable table) {
        String groupName = MainHelpers.getDataTableValueAsString(table, 0, "name");
        mainPage.typeGroupInfo(groupName, "src/main/data/files/profile_picture.png");
    }

    @And("clicks on the create group submit button")
    public void clickOnCreateGroupSubmitButton() {
        mainPage.clickOnCreateGroupSubmitButton();
    }

    @Then("user should see the successful created group message")
    public void verifyCreatedGroupSuccessfulMessage(DataTable table) {
        String groupName = MainHelpers.getDataTableValueAsString(table, 0, "name");
        assertEquals(MainHelpers.returnWindowAlertBoxText(), String.format("Successfully created group: %s", groupName));
        mainPage.waitForLoading();
    }

    @And("user should see the new created group in the groups box")
    public void verifyCreatedGroupVisibility(DataTable table) {
        String groupName = MainHelpers.getDataTableValueAsString(table, 0, "name");
        assertTrue(mainPage.verifyGroupHasBeenCreated(groupName), String.format("Expected to see the new added group <%s> in the groups container", groupName));
    }
}
