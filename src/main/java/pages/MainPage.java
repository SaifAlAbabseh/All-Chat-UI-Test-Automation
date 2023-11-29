package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Page;

public class MainPage extends Page {

    private By loadingBox = By.id("loading_box_outer_id"),
               addNewFriendLink = By.id("addLink"),
               editProfileLink = By.id("editLink"),
               usernameLabel = By.xpath("//div[@class='profileBox']/h2[@style='color:yellow']"),
               menuIcon = By.id("m");

    public By getLoadingBox() {
        return loadingBox;
    }

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

    public boolean isMenuDisplayed() {
        return findElementBy(menuIcon).isDisplayed();
    }

    public void clickOnMenu() {
        findElementBy(menuIcon).click();
    }
}
