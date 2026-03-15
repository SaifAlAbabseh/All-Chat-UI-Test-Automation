package stepdefinitions;

import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.Page;

import static org.testng.Assert.assertTrue;

public class SignupSteps {

    private final Page page = new Page();
    private final LoginPage loginPage = new LoginPage();

    @And("switches to signup")
    public void switchToSignup() {
        loginPage.switchToSignup();
    }

    @Then("user should see signup box")
    public void verifySignupBox() {
        assertTrue(loginPage.isSignupBoxDisplayed(), "Expected to see the sign up container to be visible.");
    }
}
