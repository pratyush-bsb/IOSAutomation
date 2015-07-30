package com.bsb.hike.ios.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class DeletingChatAndMessage extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
		//driver.launchApp();
	}

	@AfterClass
	public void tearDown() throws Exception{
		driver.quit();

	}


	/*@Test
	public void test001_DeleteAllExistingChats() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Iterate over all chats. \n" +
				"2. Delete all chats. \n" +
				"3. Ensure 'please make my day' text is visible.\n");
		
		homeScreenMenuObj.deleteAllExistingChats();
		
	}*/
	
	@Test(priority=1)
	public void test002_ClearChatFromInsideChat() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a chat. \n" +
				"2. Send some messages. \n" +
				"3. Clear messages from options.\n" +
				"4. Ensure that the chat thread is empty.");
		
		String textToEnter = "This is a random text to clear.";
		
		StartANewChatScreen newChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToEnter);
		chatScreenObj.sendMessage(textToEnter);
		chatScreenObj.sendMessage(textToEnter);
		chatScreenObj.clearChat();
		
		Assert.assertTrue(chatScreenObj.getLastMessage().startsWith("Chat with"), "All messages were not cleared.");
	}
	
	@Test(priority=2)
	public void test003_DeletingSingleMessageAndValidate() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a chat. \n" +
				"2. Send some messages. \n" +
				"3. Clear single message.\n" +
				"4. Go back and verify that last message deleted.");
		
		int counter = 1;
		String textToEnter = "Send message";
		goToHome();
		StartANewChatScreen newChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		
		chatScreenObj.sendMessage(textToEnter + counter++);
		chatScreenObj.sendMessage(textToEnter + counter++);
		chatScreenObj.sendMessage(textToEnter + counter);
		
		goToHome();
		String currentLastMessage = homeScreenMenuObj.getLastMessageInView(chatScreenObj.getUserName());
		Assert.assertEquals(textToEnter + counter, currentLastMessage, "Last message in the chat thread does not match the one in home screen preview");
		
		//go to chat and delete last message
		chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(chatScreenObj.getUserName(), false);
		chatScreenObj.deleteLastMessage();
		currentLastMessage = chatScreenObj.getLastMessage();
		goToHome();
		
		//fetch user's last message sent
		String updatedLastMessage = homeScreenMenuObj.getLastMessageInView(chatScreenObj.getUserName());
		Assert.assertEquals(currentLastMessage, updatedLastMessage, "Last message in the chat thread does not match the one in home screen preview");
	}
	
	@Test(priority=3)
	public void test004_ValidateStickerAsLastMessage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a chat. \n" +
				"2. Send a sticker. \n" +
				"3. Verify 'Via' share button.\n" +
				"4. Delete and verify tha message preview is changed on home screen.");
		
		goToHome();
		StartANewChatScreen newChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendSticker();
		chatScreenObj.longPressOnLastMessage();
		Assert.assertTrue(isElementPresent(chatScreenObj.getShareViaMessage()), "Share via option did not appear after long press on sticker");
		chatScreenObj.deleteLastMessage();
		String lastMessageInThread = chatScreenObj.getLastMessage();
		goToHome();
		
		String messageInHomeView = homeScreenMenuObj.getLastMessageInView(chatScreenObj.getUserName());
		//TODO will fail if sticker is still a last message
		Assert.assertEquals(lastMessageInThread, messageInHomeView, "Message in home view did not get updated after deleting sticker");
	}
}
