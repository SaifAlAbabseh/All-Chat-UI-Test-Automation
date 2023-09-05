package AllChatTest;

import base.BaseTest;
import config.Driver;
import helpers.MainHelper;
import org.testng.annotations.Test;

public class MainTest extends BaseTest {

    private MainHelper helper = new MainHelper();

    @Test
    public void testLogin() {
        Driver.driver.get("https://all-chat.000webhostapp.com/");
        helper.loginFlowTest("automation", "automation");
    }
}
