package AllChatTest;

import base.BaseTest;
import config.Driver;
import helpers.MainHelper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import java.time.Duration;

public class MainTest extends BaseTest {

    private MainHelper helper = new MainHelper();
    private LoginPage loginPage = new LoginPage();
    private MainPage mainPage;

    private WebDriverWait wait;

    @Test(priority = 0)
    public void testLoginPageFirstPopup() {
        helper.loginPopupFlow();
    }
    /*@Test(priority = 1)
    public void testInvalidLogin() {
        helper.invalidLoginFlow("invalid", "invalid");
    }
     */
    @Test(priority = 2)
    public void testLogin() {
        mainPage = helper.loginFlow("automation", "automation");
        wait = new WebDriverWait(Driver.driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginPage.getLoadingElement()));
        Assert.assertTrue(mainPage.isGroupsLabelVisible());
        Assert.assertTrue(mainPage.isFriendsLabelVisible());
    }
    @Test(priority = 3)
    public void logout() {
        mainPage.clickOnLogoutButton();
        helper.afterLogoutFlow(loginPage);
    }

}
