package pages;

import helpers.MainHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;
import utils.Page;

import java.time.Duration;

public class AddFriendPage extends Page {

    private final By usernameField = By.id("friendUsername_field"),
                    addButton = By.id("addFriendButton"),
                    suggestionBox = By.id("sug_box"),
                    addFriendResultLabel = By.id("addfriendLabel");

    public void typeUsername(String username) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        findElementBy(usernameField).sendKeys(username.substring(0, username.length() - 3));
    }

    public void verifySuggestionBox(String username) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sug_row']")));
        By userRow = By.xpath(String.format("//div[@class='sug_row']//h3[contains(., '%s')]", username));
        findElementBy(suggestionBox).findElement(userRow).click();
    }

    public void clickOnAddButton() {
        findElementBy(addButton).click();
    }

    public String returnAddNewFriendResult() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(addFriendResultLabel));
        return findElementBy(addFriendResultLabel).getText();
    }
}
