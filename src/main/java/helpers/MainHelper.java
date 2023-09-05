package helpers;

import config.Driver;
import pages.LoginPage;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

public class MainHelper {

    private LoginPage loginPage = new LoginPage();
    private MainPage mainPage = new MainPage();

    public void loginFlowTest(String username, String password) {
        loginPage.closeStartPopUp();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        mainPage.isGroupsLabelVisible();
        mainPage.isFriendsLabelVisible();
    }
}
