package pages;

import helpers.MainHelpers;
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
                editProfileButton = By.id("editLink"),
                createGroupButton = By.id("create_group_button"),
                createGroupNameField = By.name("group_name"),
                createGroupPictureField = By.id("picField"),
                createGroupSubmitButton = By.name("create_group_button"),
                groupEnterButton = By.xpath("//tr/td/a[text()='Enter']");

    private final String friendRowElement = "//table//tr[td[1]/h4[contains(., '%s')]]",
                        groupRowByName = "//tbody[@id='groupsInnerData']/tr/td/h2[text()='%s']";

    public WebElement getAddNewFriendLink() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(addNewFriendLink));
        return findElementBy(addNewFriendLink);
    }

    public By getAddNewFriendLink2() {
        return addNewFriendLink;
    }

    public WebElement getEditProfileLink() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(editProfileLink));
        return findElementBy(editProfileLink);
    }

    public WebElement getUsernameLabel() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(usernameLabel));
        return findElementBy(usernameLabel);
    }

    public boolean verifyUsername(String username) {
        return findElementBy(usernameLabel).getText().contains(username);
    }

    private By returnActualFriendRowElement(String friendUsername) {
        return By.xpath(String.format("//div[@class='friendRow'][a[@href='Delete_Friend/?name=%s']]", friendUsername));
    }

    public void removeFriendIfExists(String friendUsername) {
        try {
            By friendRowDeleteButton = By.xpath(String.format("a[@href='Delete_Friend/?name=%s']", friendUsername));
            By friendRow = returnActualFriendRowElement(friendUsername);
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(friendRow));
            findElementBy(friendsBox).findElement(friendRow).findElement(friendRowDeleteButton).click();
        }
        catch(Exception ignore) {
            System.err.println("Could not click on delete button for the existing test friend");
        }
    }

    public UserChatPage clickChatForFriend(String friendUsername) {
        By friendRowChatButton = By.xpath(String.format("a[@href='Chat/?with=%s']", friendUsername));
        By friendRow = returnActualFriendRowElement(friendUsername);
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(friendRow));
        findElementBy(friendsBox).findElement(friendRow).findElement(friendRowChatButton).click();
        return new UserChatPage();
    }

    public boolean verifyNewFriend(String friendUsername) {
        try {
            By friendRow = returnActualFriendRowElement(friendUsername);
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(friendRow));
            findElementBy(friendsBox).findElement(friendRow);
            return true;
        }
        catch(Exception e) {
            System.err.println("Could not find the new added friend row");
        }
        return false;
    }

    public AddFriendPage clickOnAddNewFriendButton() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(addNewFriendButton));
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
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(notificationsButton));
        findElementBy(notificationsButton).click();
    }

    public void acceptFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(friendRow));
        findElementBy(notificationsBox).findElement(friendRow).findElement(friendRequestAcceptButton).click();
    }

    public void rejectFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(friendRow));
        findElementBy(notificationsBox).findElement(friendRow).findElement(friendRequestRejectButton).click();
    }

    public ProfilePage clickOnEditProfileButton() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(editProfileButton));
        findElementBy(editProfileButton).click();
        return new ProfilePage();
    }

    public void clickOnCreateGroupButton() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(createGroupButton));
        findElementBy(createGroupButton).click();
    }

    public void typeGroupInfo(String groupName, String groupImagePath) {
        findElementBy(createGroupNameField).sendKeys(groupName);
        findElementBy(createGroupPictureField).sendKeys(MainHelpers.getFileAbsolutePath(groupImagePath));
    }

    public void clickOnCreateGroupSubmitButton() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(createGroupSubmitButton));
        findElementBy(createGroupSubmitButton).click();
    }

    public boolean verifyGroupHasBeenCreated(String groupName) {
        try {
            By groupRow = By.xpath(String.format(groupRowByName, groupName));
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(groupRow));
            findElementBy(groupRow);
            return true;
        }
        catch(Exception e) {
            System.err.println("Could not find the newly created group");
        }
        return false;
    }

    public boolean verifyGroupHasBeenDeleted(String groupName) {
        try {
            MainHelpers.waitFor(2);
            findElementBy(By.xpath(String.format(groupRowByName, groupName)));
            return false;
        }
        catch(Exception ignore) {
            return true;
        }
    }

    public GroupChatPage clickEnterForGroup(String groupName) {
        By groupRow = By.xpath(String.format("//tbody[@id='groupsInnerData']/tr[td/h2[text()='%s']]", groupName));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.elementToBeClickable(groupRow));
        findElementBy(groupRow).findElement(groupEnterButton).click();
        return new GroupChatPage();
    }
}
