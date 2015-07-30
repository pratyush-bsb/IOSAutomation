package com.bsb.hike.ios.tests;

import io.appium.java_client.MobileBy;

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

public class ChatWithUnsavedNumber extends HikeLibrary {
	
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
	public void test001_SearchForUnsavedNumber() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Tap on new chat icon. \n" +
				"2. Search for an unsaved hike user. \n" +
				"3. Ensure the presence of by default elements.\n");
		
		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		newChatScreenObj.clickOnSearchTab();
		enterText(newChatScreenObj.getSearchOrEnterNumber(), HIKE_NOT_SAVED_USER);
		String numberTag = getTextByName(MobileBy.IosUIAutomation(newChatScreenObj.getFirstContact() + newChatScreenObj.getContactNumberSuffix()));
		Assert.assertEquals(numberTag, "Tap to chat", "Tap to chat did not come as part of results");
		newChatScreenObj.cancelTyping();
	}
	
	@Test(priority=2)
	public void test002_StartChatWithUnsavedNumber() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Tap on new chat icon. \n" +
				"2. Search for an unsaved hike user. \n" +
				"3. Start chat with searched user.\n" +
				"4. Validate presence of elements");
		
		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		newChatScreenObj.clickOnSearchTab();
		enterText(newChatScreenObj.getSearchOrEnterNumber(), HIKE_NOT_SAVED_USER);
		ChatThreadScreen chatScreenObj = newChatScreenObj.clickOnFirstContact();
		
		Assert.assertEquals(getTextByName(chatScreenObj.getChatThreadHeader()), HIKE_NOT_SAVED_USER, "Chat did not start with the same user as searched for.");
		chatScreenObj.clickOnOverflowButton();
		Assert.assertTrue(isElementPresent(chatScreenObj.getProfileButton()), "Profile button did not appear in menu.");
		Assert.assertTrue(isElementPresent(chatScreenObj.getCallButton()), "Call button did not appear in menu.");
		Assert.assertTrue(isElementPresent(chatScreenObj.getMoreButton()), "More button did not appear in menu.");
		chatScreenObj.clickOnMoreOption();
		Assert.assertTrue(isElementPresent(chatScreenObj.getAddToContacts()), "Add to contacts button did not appear in menu.");
	}
	
}
