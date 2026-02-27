package pages;

import helpers.MainHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.Page;

import java.util.List;

public class ChatPage extends Page {

    private final By messageField = By.id("messageField"),
            sendMessageButton = By.id("sendButton"),
            messagesElements = By.xpath("//tr[td[@class='messageDate']]"),
            messagesRowsElements = By.xpath("//td[@colspan=2]/p");

    public void typeMessage(String message) {
        findElementBy(messageField).sendKeys(message);
    }

    public void clickOnSendButton() {
        findElementBy(sendMessageButton).click();
    }

    public int returnCurrentMessagesCount() {
        int count = 0;
        try {
            count = findElementsBy(messagesElements).size();
        }
        catch(Exception ignore) {
            System.out.println("No messages currently between these two users.");
        }
        return count;
    }

    public void verifySentMessage(String message) {
        MainHelpers.waitFor(2);
        List<WebElement> messagesRows = findElementsBy(messagesRowsElements);
        Assert.assertEquals(messagesRows.get(messagesRows.size() - 1).getText(), message);
    }
}
