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
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.bsb.hike.ios.screens.UserProfileScreen;
import com.support.bsb.hike.qa.apisupport.FriendsActionSupport;

public class SMSChat extends HikeLibrary {
	
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
	
	@Test(priority=1)
	public void test001_NewChatValidity() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start new chat with sms user \n" +
				"2. Validate elements on page. \n" +
				"3. Validate the contact button is disabled. \n" +
				"4. Validate 'on SMS' text in profile page. \n");
		
		goToHome();
		FriendsActionSupport friendsActionSupportObj = new FriendsActionSupport();
		friendsActionSupportObj.setSmsCount(getDEFAULT_MSISDN(), "1000");
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = startNewChatObj.startNewSmsChat();
		UserProfileScreen profileScreenObj = chatScreenObj.clickOnProfile();
		
		Assert.assertEquals(getTextByName(profileScreenObj.getOnHikeSince()), "on SMS", "For SMS user, 'on SMS' text did not appear");
		Assert.assertTrue(isElementPresent(profileScreenObj.getInviteToHikeButton()), "Invite to Hike button not present for sms contact");
		chatScreenObj = profileScreenObj.goBack();
		
		Assert.assertEquals(getTextByName(chatScreenObj.getFreeSmsTypeBoxMessage()), chatScreenObj.getFreeSmsTypeBoxString(), "Initial message does not match with 'Free SMS...'");
		Assert.assertTrue(isElementPresent(chatScreenObj.getStickerButton()), "Sticker button not present in chat thread for sms user.");
		Assert.assertTrue(isElementPresent(chatScreenObj.getEmojiButton()), "Emoji button not present in chat thread for sms user.");
		Assert.assertTrue(isElementPresent(chatScreenObj.getAttachmentButton()), "Attachment button not present in chat thread for sms user.");
		chatScreenObj.assertInviteToHikeButton();
		
		chatScreenObj.clickOnAttachment();
		Assert.assertFalse(isElementEnabled(chatScreenObj.getContactButton()), "Contact button not disabled for SMS user.");
	}
	
	@Test(priority=2)
	public void test002_StartTypingAndValidateBehavior() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start typing a message \n" +
				"2. Attach button should change to send button. \n" +
				"3. Word count should come. \n" +
				"4. Send message and check that attach button is visible again." +
				"5. Tap on back button should take back to home.");
		
		String textToType = "This is a random text to check that the message is being entered and the message count also comes on top of the send button.";
		
		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewSmsChat();
		Assert.assertTrue(isKeyboardVisible(), "Keyboard not visible on starting a new chat");
		
		chatScreenObj.typeInMessageBox(textToType);
		Assert.assertTrue(isElementPresent(chatScreenObj.getSendMessageButton()), "Attach button did not change to send message button");
		Assert.assertTrue(getTextByName(chatScreenObj.getMessageCount()).contains("/#"), "Message count is not available for sms user.");
		chatScreenObj.clickOnSendMessage();
		Assert.assertTrue(isElementPresent(chatScreenObj.getAttachmentButton()), "Send message did not change to attach button after sending message");
		chatScreenObj.goBack();
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getConversationHide()), "Back button did not take to home.");
	}
	
	@Test(priority=3)
	public void test003_SendMessageAndCheckCount() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings and fetch sms count. \n" +
				"2. Send message with 140 characters and validate. \n" +
				"3. Send message with more than 140 characters and validate. \n" +
				"4. Set sms count as 0 from server side." +
				"5. Send free sms and validate.");
		
		String textToType = "This is a random text to check that the message is being entered and the message count also comes on top of the send button.";
		String textToTypeLonger = "This is a random text to check that the message is being entered and the message count also comes on top of the send button. This is a longer message and the same should be checked and verfied for from the settings. More than one sms in this and hence more than one free sms should be deducted from the count";

		goToHome();
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		int freeSMSCount = settingsScreenObj.getFreeSMSCount();
		
		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewSmsChat();
		chatScreenObj.typeInMessageBox(textToType);
		int numberOfMessagesTyped = chatScreenObj.countOfMessagesTyped();
		chatScreenObj.clickOnSendMessage();
		
		goToHome();
		settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		int updatedFreeSMSCount = settingsScreenObj.getFreeSMSCount();
		
		Assert.assertEquals(updatedFreeSMSCount, (freeSMSCount - numberOfMessagesTyped), "Message count did not decrease aptly after sending free sms");
		
		goToHome();
		newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		chatScreenObj = newChatScreenObj.startNewSmsChat();
		chatScreenObj.typeInMessageBox(textToTypeLonger);
		numberOfMessagesTyped = chatScreenObj.countOfMessagesTyped();
		chatScreenObj.clickOnSendMessage();
		
		freeSMSCount = updatedFreeSMSCount;
		
		goToHome();
		settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		updatedFreeSMSCount = settingsScreenObj.getFreeSMSCount();
		
		Assert.assertEquals(updatedFreeSMSCount, (freeSMSCount - numberOfMessagesTyped+1), "Message count did not decrease aptly after sending free sms");
		
		
	}
	
	@Test(priority=4)
	public void test004_SendMessageWith0FreeCount() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Set message count as 0 from server side. \n" +
				"2. Validate that 0 has been set as message count. \n" +
				"3. Try to send a message. \n" +
				"4. Message should not be sent.");
		
		String text = "SMS message to send with count 0";
		
		goToHome();
		FriendsActionSupport friendsActionSupportObj = new FriendsActionSupport();
		friendsActionSupportObj.setSmsCount(getDEFAULT_MSISDN(), "0");
		
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		int freeSMSCount = settingsScreenObj.getFreeSMSCount();
		
		Assert.assertEquals(freeSMSCount, 0, "SMS count was not set as 0 from server side");
		
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.startNewSmsChat();
		chatThreadObj.sendMessage(text);
	}
	
}
