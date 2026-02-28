package helpers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Driver;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class MainHelpers {

    public static String returnWindowAlertBoxText() {
        MainHelpers.waitFor(2);
        Alert alert = Driver.getDriver().switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public static void waitFor(int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
            wait.until(d -> false);
        }
        catch(Exception ignore) {}
    }

    public static String getFileAbsolutePath(String relativePath) {
        Path relativeActualPath = Paths.get(relativePath);
        return relativeActualPath.toAbsolutePath().toString();
    }

    public static void ifOnMobileViewClickMenu(MainPage mainPage) {
        if(mainPage.isMenuDisplayed()) {
            mainPage.clickOnMenu();
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.visibilityOfElementLocated(mainPage.getAddNewFriendLink2()));
        }
    }

    public static void ifOnMobileViewCloseMenu(MainPage mainPage) {
        if(mainPage.isMenuDisplayed()) {
            mainPage.closeMenu();
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(6)).until(ExpectedConditions.invisibilityOfElementLocated(mainPage.getAddNewFriendLink2()));
        }
    }
}
