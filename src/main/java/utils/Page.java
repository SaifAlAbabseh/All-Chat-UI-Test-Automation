package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Page {

    public void visit(String URL) {
        Driver.getDriver().get(URL);
    }

    public WebElement findElementBy(By elementSearchCriteria) {
        return Driver.getDriver().findElement(elementSearchCriteria);
    }

    public List<WebElement> findElementsBy(By elementsSearchCriteria) {
        return Driver.getDriver().findElements(elementsSearchCriteria);
    }

    public String getPageTitle() {
        return Driver.getDriver().getTitle();
    }
}
