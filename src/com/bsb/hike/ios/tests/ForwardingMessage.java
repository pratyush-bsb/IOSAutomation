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

public class ForwardingMessage extends HikeLibrary {

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
	public void test001_ForwardAMessageValidate() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToForward = "This is a random string to forward";

		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToForward);
		chatScreenObj.longPressOnLastMessage();

		Assert.assertTrue(isElementPresent(chatScreenObj.getCopyMessage()), "Copy message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getDeleteMessage()), "Delete message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getForwardMessage()), "Forward message did not appear after long press on element");

	}

	@Test
	public void test002_ForwardMessageToContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Select a contact to forward message to. \n" +
				"3. Ensure that message is pre populated in the text field. \n");

		String textToForward = "This is a random string to forward";
		
		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToForward);
		chatScreenObj.longPressOnLastMessage();
		newChatScreenObj = chatScreenObj.clickOnForwardButton();
		
		Assert.assertTrue(isElementPresent(newChatScreenObj.getSearchOrEnterNumber()), "Text field to search number did not appear on clicking on 'Forward' button.");
		newChatScreenObj.headersWhenForwardingMessage();
		chatScreenObj = newChatScreenObj.selectContactToForwardMessage();
		
		Assert.assertEquals(getTextByName(chatScreenObj.getChatThreadHeader()), chatScreenObj.getUserName(), "Message not forwarded to the user selected to forward to.");
		
		Assert.assertEquals(chatScreenObj.getLastMessage(), textToForward, "Message is not forwarded.");

	}
	
	@Test
	public void test003_EditForwardedMessageSanityCheck() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Add and remove text from the pre populated field. \n" +
				"2. Send button should be activated. \n" +
				"3. Tap on send button should send the message. \n");
		
		String textToForward = "This is a random string to forward";
		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false);
		chatScreenObj.longPressOnLastMessage();
		StartANewChatScreen newChatScreenObj = chatScreenObj.clickOnForwardButton();
		newChatScreenObj.selectContactToEditandForwardMessage();
		//chatScreenObj = new ChatThreadScreen(HIKE_CONTACT_NAME_3);
		
		Assert.assertEquals(getTextByValue(newChatScreenObj.getEditForwardingMessageWindow()), textToForward.substring(0, textToForward.length()-7), "The edited texts do not match");
		
		//add some text and check
		newChatScreenObj.typeInMessageBox("forward");
		Assert.assertEquals(getTextByValue(newChatScreenObj.getEditForwardingMessageWindow()), textToForward, "The edited texts do not match");
		
		Assert.assertTrue(isElementPresent(newChatScreenObj.getForwardButton()), "Send button is not available");
		newChatScreenObj.clickOnForwardMessage();
	}

}
