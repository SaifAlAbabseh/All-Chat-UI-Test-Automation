package pages;

import config.Driver;
import config.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class MainPage extends Page {

    private By groupsLabel = By.xpath("//*[contains(., 'Groups')]"),
                            friendsLabel = By.xpath("//*[contains(., 'Your Friends')]");

    public boolean isGroupsLabelVisible() {
        WebElement groupsLabelElement = Driver.driver.findElement(groupsLabel);
        return groupsLabelElement.isDisplayed();
    }

    public boolean isFriendsLabelVisible() {
        WebElement friendsLabelElement = Driver.driver.findElement(friendsLabel);
        return friendsLabelElement.isDisplayed();
    }
}
