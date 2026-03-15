package stepdefinitions;

import helpers.MainHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.ChatPage;
import pages.MainPage;
import pages.UserChatPage;
import utils.EnvConfig;

import static org.testng.Assert.assertEquals;

public class UserChatSteps {

    private final MainPage mainPage = new MainPage();
    private final ChatPage chatPage = new ChatPage();
    private UserChatPage userChatPage;

    private int currentMessagesCount;

    @When("user clicks on chat button with friend")
    public void clickOnChatButton(DataTable table) {
        String friendUsername = EnvConfig.get(MainHelpers.getDataTableValueAsString(table, 0, "username"));
        userChatPage = mainPage.clickChatForFriend(friendUsername);
    }

    @And("types message")
    public void typeMessage(DataTable table) {
        currentMessagesCount = chatPage.returnCurrentMessagesCount();
        String message = MainHelpers.getDataTableValueAsString(table, 0, "message");
        chatPage.typeMessage(message);
    }

    @And("clicks on the send message button")
    public void clickOnSendMessageButton() {
        chatPage.clickOnSendButton();
    }

    @Then("user should see the new sent message in the chat box")
    public void verifyNewSentMessage(DataTable table) {
        String message = MainHelpers.getDataTableValueAsString(table, 0, "message");
        chatPage.verifySentMessage(message);
        int messageCountAfter = chatPage.returnCurrentMessagesCount();
        assertEquals(messageCountAfter, currentMessagesCount + 1);
    }
}
