package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;
import utils.Page;

import java.time.Duration;

public class MainPage extends Page {

    private By loadingBox = By.id("loading_box_outer_id"),
               addNewFriendLink = By.id("addLink"),
               editProfileLink = By.id("editLink"),
               usernameLabel = By.xpath("//div[@class='profileBox']/h2[@style='color:yellow']"),
               menuIcon = By.id("m"),
                friendsBox = By.id("innerData"),
                addNewFriendButton = By.id("addLink"),
                logoutButton = By.xpath("//button[contains(., 'Logout')]"),
                notificationsButton = By.xpath("//button[@title='Notifications']"),
                notificationsBox = By.xpath("//div[@id='notificationsBox']"),
                friendRequestAcceptButton = By.name("acceptFriendRequestButton"),
                friendRequestRejectButton = By.name("rejectFriendRequestButton"),
                editProfileButton = By.id("editLink");

    private final String friendRowElement = "//table//tr[td[1]/h4[contains(., '%s')]]";

    public WebElement getAddNewFriendLink() {
        return findElementBy(addNewFriendLink);
    }

    public By getAddNewFriendLink2() {
        return addNewFriendLink;
    }

    public WebElement getEditProfileLink() {
        return findElementBy(editProfileLink);
    }

    public WebElement getUsernameLabel() {
        return findElementBy(usernameLabel);
    }

    public boolean verifyUsername(String username) {
        return findElementBy(usernameLabel).getText().contains(username);
    }

    private By returnActualFriendRowElement(String friendUsername) {
        return By.xpath(String.format("//div[@class='friendRow']//a[@href='Delete_Friend/?name=%s']", friendUsername));
    }

    public void removeFriendIfExists(String friendUsername) {
        try {
            findElementBy(friendsBox).findElement(returnActualFriendRowElement(friendUsername)).click();
        }
        catch(Exception ignore) {}
    }

    public boolean verifyNewFriend(String friendUsername) {
        try {
            findElementBy(friendsBox).findElement(returnActualFriendRowElement(friendUsername));
            return true;
        }
        catch(Exception e) {
            System.err.println("Could not find the new added friend row");
        }
        return false;
    }

    public AddFriendPage clickOnAddNewFriendButton() {
        findElementBy(addNewFriendButton).click();
        return new AddFriendPage();
    }

    public boolean isMenuDisplayed() {
        return findElementBy(menuIcon).isDisplayed();
    }

    public void clickOnMenu() {
        findElementBy(menuIcon).click();
    }

    public void logout() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        findElementBy(logoutButton).click();
    }

    public void waitForLoading() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.invisibilityOfElementLocated(loadingBox));
    }

    public void clickOnNotificationsButton() {
        findElementBy(notificationsButton).click();
    }

    public void acceptFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        findElementBy(notificationsBox).findElement(friendRow).findElement(friendRequestAcceptButton).click();
    }

    public void rejectFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        findElementBy(notificationsBox).findElement(friendRow).findElement(friendRequestRejectButton).click();
    }

    public ProfilePage clickOnEditProfileButton() {
        findElementBy(editProfileButton).click();
        return new ProfilePage();
    }
}
