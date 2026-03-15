package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.MainPage;
import pages.ProfilePage;
import utils.EnvConfig;

import static org.testng.Assert.assertEquals;

public class EditProfilePictureSteps {

    private final MainPage mainPage = new MainPage();
    private ProfilePage profilePage;

    @When("user clicks on the edit profile link")
    public void clickOnEditProfileLink() {
        MainHelpers.ifOnMobileViewClickMenu(mainPage);
        profilePage = mainPage.clickOnEditProfileButton();
    }

    @And("user sees their current picture and username")
    public void verifyCurrentPictureAndUsername(DataTable table) {
        String username = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        profilePage.verifyUserPicture(username);
        profilePage.verifyUsername(username);
    }

    @And("clicks on the change picture link")
    public void clickOnChangePictureLink() {
        profilePage.clickOnChangePictureButton();
    }

    @And("selects picture")
    public void selectPicture() {
        profilePage.uploadPicture("src/main/data/files/profile_picture.png");
    }

    @And("clicks on the submit button")
    public void clickOnSubmitButton() {
        profilePage.clickOnProfilePictureSubmitButton();
        mainPage.waitForLoading();
    }

    @Then("user should see the successful changed message")
    public void verifySuccessfulChangedMessage() {
        assertEquals(profilePage.returnSubmitPictureMessage(), "Successfully Changed");
    }
}
