package pages;

import helpers.MainHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.Page;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfilePage extends Page {

    private final By changePictureButton = By.xpath("//a[@href='Edit_Profile/']"),
                        changePasswordButton = By.xpath("//a[@href='Change_Password/']"),
                        fileInputElement = By.id("picField"),
                        profilePictureSubmitButton = By.name("changeButton"),
                        profilePictureSubmitResultMessage = By.id("message"),
                        currentPasswordField = By.name("currentPass"),
                        newPasswordField = By.name("newPass"),
                        confirmNewPasswordField = By.name("confirmNewPass"),
                        changePasswordSubmitButton = By.name("changePassButton");

    public void verifyUserPicture(String username) {
        String userPictureBoxElement = "img[src$='?u=%s']";
        WebElement userPicture = findElementBy(By.cssSelector(String.format(userPictureBoxElement, username)));
        String picWidth = userPicture.getAttribute("width");
        String picHeight = userPicture.getAttribute("height");
        Assert.assertEquals(picWidth, "100");
        Assert.assertEquals(picHeight, "100");
    }

    public void verifyUsername(String username) {
        String usernameElement = "//h2[contains(., '%s')]";
        try {
            findElementBy(By.xpath(String.format(usernameElement, username)));
        }
        catch(Exception ignore) {
            System.err.println("Username element in profile page could not be found");
        }
    }

    public void clickOnChangePictureButton() {
        findElementBy(changePictureButton).click();
    }

    public void clickOnChangePasswordButton() {
        findElementBy(changePasswordButton).click();
    }

    public void uploadPicture(String picturePath) {
        findElementBy(fileInputElement).sendKeys(MainHelpers.getFileAbsolutePath(picturePath));
    }

    public void clickOnProfilePictureSubmitButton() {
        findElementBy(profilePictureSubmitButton).click();
    }

    public String returnSubmitPictureMessage() {
        return findElementBy(profilePictureSubmitResultMessage).getText();
    }

    public void typeCurrentPassword(String currentPassword) {
        findElementBy(currentPasswordField).sendKeys(currentPassword);
    }

    public void typeNewPassword(String newPassword) {
        findElementBy(newPasswordField).sendKeys(newPassword);
    }

    public void typeConfirmNewPassword(String confirmNewPassword) {
        findElementBy(confirmNewPasswordField).sendKeys(confirmNewPassword);
    }

    public void submitNewPassword() {
        findElementBy(changePasswordSubmitButton).click();
    }
}
