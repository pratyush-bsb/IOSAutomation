package com.bsb.hike.ios.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.support.bsb.hike.qa.apisupport.Hike2HikeMessageSupport;

public class ReceivingMessages extends HikeLibrary {

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
	public void test001_ReceiveMessageWhenNoChatThread() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start new chat with user with no chat roaster. \n" +
				"2. Send messages from server to self. \n" +
				"3. Validate the receiving of message. \n");

		String message = "This is a message sent from server";
		String sender = HIKE_NUMBER_1;
		String senderName = HIKE_CONTACT_NAME;

		goToHome();

		Hike2HikeMessageSupport hikeMessageSupportObj = new Hike2HikeMessageSupport();

		hikeMessageSupportObj.sendHikeMessage(sender, getDEFAULT_MSISDN(), message);
		WebElement senderElement = homeScreenMenuObj.getSpecificChatElement(senderName);

		Assert.assertTrue((senderElement != null), "Did not receive any message from server side.");

		String messageInView = homeScreenMenuObj.getLastMessageInView(senderName);

		Assert.assertEquals(messageInView, message, "The sent message is not in view in home screen.");

		Assert.assertEquals(homeScreenMenuObj.getUnreadMessageCounter(senderName), 1, "Unread counter is not updated after receiving a message");

		Assert.assertTrue(homeScreenMenuObj.checkTimestampAvailability(senderName), "Timestamp is not available for sender on homepage");	
	}

	@Test(priority=2)
	public void test002_ReceiveMessageWhenInChatThread() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start new chat with user with existing chat roster. \n" +
				"2. Send messages from server to self. \n" +
				"3. Validate the receiving of message. \n");

		String message = "This is a message sent from server with chat roster";
		String sender = HIKE_NUMBER_1;
		String senderName = HIKE_CONTACT_NAME;

		goToHome();

		Hike2HikeMessageSupport hikeMessageSupportObj = new Hike2HikeMessageSupport();

		hikeMessageSupportObj.sendHikeMessage(sender, getDEFAULT_MSISDN(), message);
		WebElement senderElement = homeScreenMenuObj.getSpecificChatElement(senderName);

		Assert.assertTrue((senderElement != null), "Did not receive any message from server side.");

		String messageInView = homeScreenMenuObj.getLastMessageInView(senderName);

		Assert.assertEquals(messageInView, message, "The sent message is not in view in home screen.");

		Assert.assertEquals(homeScreenMenuObj.getUnreadMessageCounter(senderName), 2, "Unread counter is not updated after receiving a message");

		Assert.assertTrue(homeScreenMenuObj.checkTimestampAvailability(senderName), "Timestamp is not available for sender on homepage");
	}
	
	@Test(priority=3)
	public void test003_ReadReceivedMessageAndValidate() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open chat with unread messages. \n" +
				"2. Read messages and go back to home screen. \n" +
				"3. Validate the reading of message. \n");

		String senderName = HIKE_CONTACT_NAME;

		goToHome();
		ChatThreadScreen chatThreadObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(senderName, false);
		homeScreenMenuObj = chatThreadObj.goBack();
		
		Assert.assertEquals(homeScreenMenuObj.getUnreadMessageCounter(senderName), 0, "Unread counter is not updated after receiving a message");
	}

}
