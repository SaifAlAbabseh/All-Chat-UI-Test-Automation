package AllChatTest;

import base.BaseTest;
import config.Driver;
import org.testng.annotations.Test;
import pages.LoginPage;

public class MainTest extends BaseTest {

    private LoginPage loginPage = new LoginPage();

    @Test
    public void testLogin() {
        Driver.driver.get("https://all-chat.000webhostapp.com/");
        loginPage.closeStartPopUp();
    }
}
