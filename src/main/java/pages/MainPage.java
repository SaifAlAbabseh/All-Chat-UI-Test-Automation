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
                groupEnterButton = By.xpath("//div[@class='groupRow']/a[starts-with(@href, 'Group/?group_id=')]"),
                mobileViewMenuClosebutton = By.id("exit_menu_button");

    private final String friendRowElement = "//table//tr[td[1]/h4[contains(., '%s')]]",
                        groupRowByName = "//div[@class='groupRow']/h2[text()='%s']";

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

    private By returnActualFriendRowElement(String whichOperation, String friendUsername) {
        String operationElement = String.format("//div[@class='friendRow']/a[@href='%s/?%s=%s']", whichOperation, (whichOperation.equals("Chat")) ? "with" : "name", friendUsername);
        return By.xpath(operationElement);
    }

    public void removeFriendIfExists(String friendUsername) {
        try {
            By friendDeleteButton = returnActualFriendRowElement("Delete_Friend", friendUsername);
            findElementBy(friendDeleteButton).click();
        }
        catch(Exception ignore) {
            System.err.println("Could not click on delete button for the existing test friend or there was no friend as expected.");
        }
    }

    public UserChatPage clickChatForFriend(String friendUsername) {
        By friendChatButton = returnActualFriendRowElement("Chat", friendUsername);
        findElementBy(friendChatButton).click();
        return new UserChatPage();
    }

    public boolean verifyNewFriend(String friendUsername) {
        try {
            By friendChatButton = returnActualFriendRowElement("Chat", friendUsername);
            findElementBy(friendChatButton);
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
        try {
            WebElement menu = findElementBy(menuIcon);
            return menu.isDisplayed();
        }
        catch(Exception ignore) {}
        return false;
    }

    public void clickOnMenu() {
        findElementBy(menuIcon).click();
    }

    public void logout() {
        findElementBy(logoutButton).click();
    }

    public void waitForLoading() {
        try {
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(loadingBox));
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.invisibilityOfElementLocated(loadingBox));
        }
        catch(Exception e) {
            System.err.println("Could not see the loading container.. maybe it is as expected.");
        }
    }

    public void clickOnNotificationsButton() {
        findElementBy(notificationsButton).click();
    }

    public void acceptFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        findElementBy(friendRow).findElement(friendRequestAcceptButton).click();
    }

    public void rejectFriendRequestFrom(String requesterUsername) {
        By friendRow = By.xpath(String.format(friendRowElement, requesterUsername));
        findElementBy(friendRow).findElement(friendRequestRejectButton).click();
    }

    public ProfilePage clickOnEditProfileButton() {
        findElementBy(editProfileButton).click();
        return new ProfilePage();
    }

    public void clickOnCreateGroupButton() {
        findElementBy(createGroupButton).click();
    }

    public void typeGroupInfo(String groupName, String groupImagePath) {
        findElementBy(createGroupNameField).sendKeys(groupName);
        findElementBy(createGroupPictureField).sendKeys(MainHelpers.getFileAbsolutePath(groupImagePath));
    }

    public void clickOnCreateGroupSubmitButton() {
        findElementBy(createGroupSubmitButton).click();
    }

    public boolean verifyGroupHasBeenCreated(String groupName) {
        try {
            By groupRow = By.xpath(String.format(groupRowByName, groupName));
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
        By groupRow = By.xpath(String.format("//div[@id='groupsInnerData']/div[h2[text()='%s']]", groupName));
        findElementBy(groupRow).findElement(groupEnterButton).click();
        return new GroupChatPage();
    }

    public void closeMenu() {
        findElementBy(mobileViewMenuClosebutton).click();
    }
}
