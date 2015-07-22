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

public class CopyPastingMessage extends HikeLibrary {

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
	
	@Test
	public void test001_ValidateCopyButtonPresence() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToCopy = "This is a random string to copy";

		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToCopy);
		chatScreenObj.longPressOnLastMessage();

		Assert.assertTrue(isElementPresent(chatScreenObj.getCopyMessage()), "Copy message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getDeleteMessage()), "Delete message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getForwardMessage()), "Forward message did not appear after long press on element");

	}
	
	@Test
	public void test002_CopyAMessage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on any message. \n" +
				"3. Press on copy and check that the copy button disappears. \n");

		String textToCopy = "This is a random string to copy";

		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToCopy);
		chatScreenObj.longPressOnLastMessage();
		chatScreenObj.clickOnCopyButton();
		
		Assert.assertFalse(isElementPresent(chatScreenObj.getCopyMessage()), "Copy button still present after tapping on the button.");
	}
	
	@Test
	public void test003_ValidatePasteButtonPresence() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on type message bar. \n" +
				"3. Validate if the paste button appears. \n" +
				"3. Send the pasted text. \n" +
				"4. Assert that the pasted text is sent.");
		
		String textToCopy = "This is a random string to copy";
		
		goToHome();
		
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME_1);
		longPress(chatScreenObj.getChatMessageTypeBox());
		Assert.assertTrue(isElementPresent(pasteButton), "Paste button did not come after long press on type box");
		
		clickOnElement(pasteButton);
		Assert.assertEquals(getTextByValue(chatScreenObj.getChatMessageTypeBox()), textToCopy, "Same message did not get copied");
		chatScreenObj.clickOnSendMessage();
		String lastMessage = chatScreenObj.getLastMessage();
		Assert.assertEquals(lastMessage, textToCopy, "Same message did not get copied and sent.");
		
	}
	
}
