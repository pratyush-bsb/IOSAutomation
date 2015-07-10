package com.bsb.hike.ios.tests.groupChat;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.UserProfileScreen;

public class SendMessageInGC extends HikeLibrary {
	
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
	public void test001_SendMessageToHikeContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create a group with hike contact, sms contact and unsaved contact. \n" +
				"2. Go to GC profile. \n" +
				"3. Send message to user and validate. \n");
		
		String groupName = "IOS automation group";
		String messageToSend = "Message sent through group chat user info.";
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_SMS_CONTACT_NAME_1);
		contactSelectionScreenObj.selectSearchedContact(HIKE_SMS_CONTACT_NAME_1);
//		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear("1234567890");
		contactSelectionScreenObj.selectFirstContactInResults();

		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		UserProfileScreen userProfileObj = groupProfileObj.goToUserProfile(HIKE_CONTACT_NAME_1);
		ChatThreadScreen chatThreadObj = userProfileObj.clickOnSendAMessage();
		chatThreadObj.sendMessage(messageToSend);
		
		String lastMessage = chatThreadObj.getLastMessage();
		
		Assert.assertEquals(lastMessage, messageToSend, "The message was not sent to user.");
	}
	
	@Test
	public void test002_SendMessageToUnsavedContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to GC created in above test. \n" +
				"2. Go to GC profile. \n" +
				"3. Send message to unsaved user and validate. \n");
		
		String groupName = "IOS automation group";
		String messageToSend = "Message sent through group chat user info.";
		//String user = "123456";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		UserProfileScreen userProfileObj = groupProfileObj.goToUserProfile(HIKE_SMS_CONTACT_NAME_1);
		Assert.assertTrue(isElementPresent(userProfileObj.getSendAMessage()), "Send a message button did not come for SMS user");
		ChatThreadScreen chatThreadObj = userProfileObj.clickOnSendAMessage();
		chatThreadObj.sendMessage(messageToSend);
		
		String lastMessage = chatThreadObj.getLastMessage();
		
		Assert.assertEquals(lastMessage, messageToSend, "The message was not sent to user.");
		
	}

	@Test
	public void test003_SendMessageToSMSContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to GC created in above test. \n" +
				"2. Go to GC profile. \n" +
				"3. Send message to SMS user and validate. \n");
		
		String groupName = "IOS automation group";
		String messageToSend = "Message sent through group chat user info.";
		String user = "1234567890";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		UserProfileScreen userProfileObj = groupProfileObj.goToUserProfile(user);
		Assert.assertTrue(isElementPresent(userProfileObj.getSendAMessage()), "Send a message button did not come for SMS user");
		ChatThreadScreen chatThreadObj = userProfileObj.clickOnSendAMessage();
		chatThreadObj.sendMessage(messageToSend);
		
		String lastMessage = chatThreadObj.getLastMessage();
		
		Assert.assertEquals(lastMessage, messageToSend, "The message was not sent to user.");
	}
}
