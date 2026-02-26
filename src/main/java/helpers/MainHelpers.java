package helpers;

import org.openqa.selenium.Alert;
import utils.Driver;

public class MainHelpers {

    public static String returnWindowAlertBoxText() {
        Alert alert = Driver.getDriver().switchTo().alert();
        return alert.getText();
    }
}
