package com.bsb.hike.ios.tests.stealth;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.EnterPasscodeForStealth;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.support.bsb.hike.qa.apisupport.Hike2HikeMessageSupport;

public class OneToOneChatThread extends HikeLibrary {
	
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
	public void test001_ChatWithH2HContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Hide a chat thread. \n" +
				"2. Enable stealth mode and verify validity. \n");
		
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.searchContactByNameAndStartChat(HIKE_CONTACT_NAME_1);
		chatThreadObj.sendMessage("Testing string for stealth");
		
		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		homeScreenMenuObj.toggleHideUnhideChat(HIKE_CONTACT_NAME_1);
		homeScreenMenuObj.clickOnHideButton(false);
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false) == null, "The user is visible even after hiding the chat");
		
	}

	@Test
	public void test002_ChatWithH2SContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Hide a chat thread. \n" +
				"2. Enable stealth mode and verify validity. \n");
		
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.searchContactByNameAndStartChat(HIKE_SMS_CONTACT_NAME_1);
		chatThreadObj.sendMessage("Testing string for stealth");
		
		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		homeScreenMenuObj.toggleHideUnhideChat(HIKE_SMS_CONTACT_NAME_1);
		homeScreenMenuObj.clickOnHideButton(false);
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_SMS_CONTACT_NAME_1, false) == null, "The user is visible even after hiding the chat");
		
	}
	
	@Test
	public void test003_HideHikeDailyThread() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Hide Hike Daily thread. \n" +
				"2. Enable stealth mode and verify validity. \n");
		
		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		homeScreenMenuObj.toggleHideUnhideChat(HIKE_DAILY);
		homeScreenMenuObj.clickOnHideButton(false);
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_DAILY, false) == null, "The user is visible even after hiding the chat");
		
	}
	
	@Test
	public void test004_BlockAContactInStealthMode() {
		
		//HIKE_CONTACT_NAME_1 is hidden, block it.
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Block an already hidden contact. \n" +
				"2. Enable stealth mode and verify validity. \n");
		
		goToHome();
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		BlockedList blockedListObj = settingsScreenObj.clickOnBlockList();
		blockedListObj.blockContact(HIKE_CONTACT_NAME_1);
		goToHome();
		
		//try to recieve message from blocked contact
		Hike2HikeMessageSupport hikeMessageSupportObj = new Hike2HikeMessageSupport();

		hikeMessageSupportObj.sendHikeMessage(HIKE_NUMBER_2, getDEFAULT_MSISDN(), "Testing blocked contact");
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false) == null, "The user is visible even after hiding the chat");
		
		//unblock contact for future cases
		settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		blockedListObj = settingsScreenObj.clickOnBlockList();
		blockedListObj.unblockContact(HIKE_CONTACT_NAME_1);
		goToHome();
		
	}
	
	/*@Test
	public void test005_*/
}
