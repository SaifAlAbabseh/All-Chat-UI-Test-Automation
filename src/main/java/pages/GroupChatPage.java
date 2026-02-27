package pages;
import helpers.MainHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;
import utils.Page;

import java.time.Duration;

public class GroupChatPage extends Page {

    private final By groupSettingsButton = By.xpath("//a[@title='Group Info']"),
                        editPictureButton = By.xpath("//a[text()='Edit Picture']"),
                        pictureField = By.id("picField"),
                        pictureChangeButton = By.name("changePicButton"),
                        peopleAddButton = By.id("add_member_button"),
                        destroyGroupButton = By.xpath("//a[text()='Destroy Group']");
    private final String friendRow = "//tbody[@id='innerData']/tr[td/h2[text()='%s']]",
                        kickButton = "//tbody[@id='group_users_box']/tr[td/h2[text()='%s']]/td/a[@id='kicklink']";

    public void clickOnGroupSettingsButton() {
        findElementBy(groupSettingsButton).click();
    }

    public void clickOnEditPictureButton() {
        findElementBy(editPictureButton).click();
    }

    public void uploadPicture(String picturePath) {
        findElementBy(pictureField).sendKeys(MainHelpers.getFileAbsolutePath(picturePath));
    }

    public void clickOnChangePictureButton() {
        findElementBy(pictureChangeButton).click();
    }

    public void clickOnAddPeopleButton() {
        findElementBy(peopleAddButton).click();
    }

    public void addFriendToGroup(String friendUsername) {
        By friendRowElement = By.xpath(String.format(friendRow, friendUsername));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(friendRowElement));
        findElementBy(friendRowElement).findElement(By.id("addlink")).click();
    }

    public void removeFriendFromGroup(String friendUsername) {
        clickOnGroupSettingsButton();
        try {
            MainHelpers.waitFor(2);
            findElementBy(By.xpath(String.format(kickButton, friendUsername))).click();
        }
        catch(Exception e) {
            System.err.println("Could not find the newly added friend to group.");
        }
    }

    public void destroyGroup() {
        clickOnGroupSettingsButton();
        findElementBy(destroyGroupButton).click();
    }
}
