package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import java.time.Duration;
import java.util.List;

public class Page {

    public void visit(String URL) {
        Driver.getDriver().get(URL);
    }

    public WebElement findElementBy(By elementSearchCriteria) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(elementSearchCriteria));
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(elementSearchCriteria));
        return Driver.getDriver().findElement(elementSearchCriteria);
    }

    public List<WebElement> findElementsBy(By elementsSearchCriteria) {
        return Driver.getDriver().findElements(elementsSearchCriteria);
    }

    public String getPageTitle() {
        return Driver.getDriver().getTitle();
    }

    public void goBack() {
        By backButton = By.xpath("//a[img[@alt='Back Button']]");
        findElementBy(backButton).click();
    }

    public LoginPage navigateToLoginPage(String URL) {
        visit(URL);
        return new LoginPage();
    }
}
