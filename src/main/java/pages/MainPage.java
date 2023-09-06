package pages;

import config.Driver;
import config.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends Page {

    private By groupsLabel = By.xpath("//*[contains(., 'Groups')]"),
                            friendsLabel = By.xpath("//*[contains(., 'Your Friends')]"),
                            logoutButton = By.xpath("//button[contains(., 'Logout')]");

    private LoginPage loginPage = new LoginPage();
    private WebDriverWait wait;

    public boolean isGroupsLabelVisible() {
        WebElement groupsLabelElement = Driver.driver.findElement(groupsLabel);
        return groupsLabelElement.isDisplayed();
    }

    public boolean isFriendsLabelVisible() {
        WebElement friendsLabelElement = Driver.driver.findElement(friendsLabel);
        return friendsLabelElement.isDisplayed();
    }

    public void clickOnLogoutButton() {
        WebElement logoutButtonElement = Driver.driver.findElement(logoutButton);
        logoutButtonElement.click();
    }

    public By getGroupsLabel() {
        return groupsLabel;
    }

    public By getLogoutButton() {
        return logoutButton;
    }
}
