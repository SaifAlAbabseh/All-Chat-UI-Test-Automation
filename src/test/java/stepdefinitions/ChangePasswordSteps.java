package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.ProfilePage;
import utils.EnvConfig;

import static org.testng.Assert.assertEquals;

public class ChangePasswordSteps {

    private final ProfilePage profilePage = new ProfilePage();

    @And("user clicks on the change password link")
    public void clickOnChangePasswordLink() {
        profilePage.clickOnChangePasswordButton();
    }

    @And("types current password")
    public void typeCurrentPassword(DataTable table) {
        String password = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "password"));
        profilePage.typeCurrentPassword(password);
    }

    @And("types new password")
    public void typeNewPassword(DataTable table) {
        String password = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "password"));
        profilePage.typeNewPassword(password);
    }

    @And("confirms new password")
    public void confirmNewPassword(DataTable table) {
        String password = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "password"));
        profilePage.typeConfirmNewPassword(password);
    }

    @And("clicks on the change button")
    public void clickOnChangeButton() {
        profilePage.submitNewPassword();
    }

    @And("user should see the successful changed password message")
    public void verifySuccessfulChangedPasswordMessage() {
        assertEquals(MainHelpers.returnWindowAlertBoxText(), "Successfully changed password :)");
    }
}
