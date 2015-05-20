package com.bsb.hike.ios.tests.groupChat;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class ForwardMessageInGroup extends HikeLibrary {

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
	public void test001_ForwardingMessageValidate() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing group. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToForward = "This is a random string to forward";
		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.sendMessage(textToForward);
		groupThreadObj.longPressOnLastMessage();

		//assert presence of copy, delete, forward buttons
		Assert.assertTrue(isElementPresent(groupThreadObj.getCopyMessage()), "Copy message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(groupThreadObj.getDeleteMessage()), "Delete message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(groupThreadObj.getForwardMessage()), "Forward message did not appear after long press on element");
	}

	@Test
	public void test002_ForwardMessageToContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Select a contact to forward message to. \n" +
				"3. Ensure that message is pre populated in the text field. \n");

		String textToForward = "This is a random string to forward";
		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.sendMessage(textToForward);
		groupThreadObj.longPressOnLastMessage();
		StartANewChatScreen newChatScreenObj = groupThreadObj.clickOnForwardButton();

		Assert.assertTrue(isElementPresent(newChatScreenObj.getSearchOrEnterNumber()), "Text field to search number did not appear on clicking on 'Forward' button.");
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible when it should not be");
		newChatScreenObj.headersWhenForwardingMessage();
		ChatThreadScreen chatScreenObj = newChatScreenObj.selectContactToForwardMessage();

		Assert.assertEquals(getTextByName(chatScreenObj.getChatThreadHeader()), chatScreenObj.getUserName(), "Message not forwarded to the user selected to forward to.");

		Assert.assertEquals(chatScreenObj.getLastMessage(), textToForward, "Forwarded message is not pre populated.");
		
		chatScreenObj.clickOnSendMessage();

	}

}
