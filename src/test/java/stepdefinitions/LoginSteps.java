package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.LoginPage;
import pages.MainPage;
import utils.EnvConfig;
import utils.Page;

import static org.testng.Assert.*;

public class LoginSteps {

    private final Page page = new Page();

    private LoginPage loginPage;
    private MainPage mainPage;

    @Given("user opens the site login page")
    public void openLoginPage(DataTable table) {
        String url = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "url"));
        loginPage = page.navigateToLoginPage(url);
        String title = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "title"));
        assertEquals(loginPage.getLoginPageTitle(), title);
    }

    @When("user closes popup box")
    public void closePopupBox() {
        loginPage.clickOnPopUpExitButton();
    }

    @And("user enters valid credentials")
    public void enterValidCredentials(DataTable table) {
        String username = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        String password = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "password"));
        loginPage.setUsernameField(username);
        loginPage.setPasswordField(password);
    }

    @And("clicks login")
    public void clickLogin() {
        mainPage = loginPage.clickOnLoginButton();
        mainPage.waitForLoading();
    }

    @Then("user should be navigated to the main page")
    public void verifyMainPage(DataTable table) {
        MainHelpers.ifOnMobileViewClickMenu(mainPage);
        assertTrue(mainPage.getAddNewFriendLink().isDisplayed(), "Expected to see add new friend link visible.");
        assertTrue(mainPage.getEditProfileLink().isDisplayed(), "Expected to see edit profile link visible.");
        assertTrue(mainPage.getUsernameLabel().isDisplayed(), "Expected to see the username label visible.");

        String username = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        assertTrue(mainPage.verifyUsername(username), "Expected to see the username label content: " + username);
        MainHelpers.ifOnMobileViewCloseMenu(mainPage);
    }

    @And("switches back to login")
    public void switchToLogin() {
        loginPage.switchToLogin();
    }
}
