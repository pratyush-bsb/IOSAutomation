package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.FavoritesScreen;
import com.bsb.hike.ios.screens.FreeSMS;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.InviteViaSMS;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class FreeSMSToIndia extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	AccountScreen accountScreenObj = null;
	
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
	public void test001_FreeSMSToIndiaButton() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Validate the tagline of 'free sms to india' button. \n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		Assert.assertTrue(isElementPresent(accountScreenObj.getFreeSMSToIndiaSwitch()), "Free SMS to India switch not available");
		
	}
	
	@Test(priority=2)
	public void test002_DefaultOnForIndian() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Since this is an Indian number, it should be on by default. \n");
		
		Assert.assertEquals(getTextByValue(accountScreenObj.getFreeSMSToIndiaSwitch()), "1", "Free SMS not activated for Indian user");
		
	}
	
	@Test(priority=3)
	public void test003_WhenEnableCase() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enable Free SMS. \n" +
				"2. Navigate to home. \n" +
				"3. Start a new chat. Validate.\n");
		
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		
		Assert.assertTrue(isElementPresent(startNewChatObj.getPeopleOnHikeTab()), "People on hike tab is not present");
		Assert.assertTrue(isElementPresent(startNewChatObj.getSmsContactsTab()), "SMS contacts tab is not present");
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName("test");
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		Assert.assertTrue(isElementPresent(contactSelectionScreenObj.getPeopleOnHikeTab()), "People on hike tab is not present");
		Assert.assertTrue(isElementPresent(contactSelectionScreenObj.getSmsContactsTab()), "SMS contacts tab is not present");		
		
	}
	
	@Test(priority=4)
	public void test004_InviteSMSUsers() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to settings > Free SMS. \n" +
				"3. Try to invite some users. Validate that all SMS contacts are listed.\n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		FreeSMS freeSmsScreenObj = settingScreenObj.clickOnFreeSMS();
		
		InviteViaSMS inviteSMSScreenObj = freeSmsScreenObj.inviteSMSUsers();
		
		Assert.assertTrue(inviteSMSScreenObj.getCountOfContacts() > 0, "Contacts were not displayed when clicked on 'Invite via SMS'");
		inviteSMSScreenObj.cancelInvite();
	}
	
	@Test(priority=5)
	public void test005_HikeToSMS() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to settings > Free SMS. \n" +
				"3. Go to Hike to SMS. Validate that all SMS and Hike contacts are listed.\n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		FreeSMS freeSmsScreenObj = settingScreenObj.clickOnFreeSMS();
		
		StartANewChatScreen newChatObj = freeSmsScreenObj.startHiking();
		
		Assert.assertTrue(isElementPresent(newChatObj.getPeopleOnHikeTab()), "People on hike tab is not present");
		Assert.assertTrue(isElementPresent(newChatObj.getSmsContactsTab()), "SMS contacts tab is not present");
		
	}
	
	@Test(priority=6)
	public void test006_ValidateInFavoritesTab() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to favorites tab. \n" +
				"3. Assert that 'people on sms' should not be available.\n");
		
		goToHome();
		FavoritesScreen favoritesScreenObj = homeScreenMenuObj.clickOnFavorites_Lbl();
		
		Assert.assertFalse(isElementPresent(favoritesScreenObj.getSmsContactsTab()), "SMS contacts tab appeared in favorites when it should not have");
		
	}
	
	@Test
	public void test007_ValidateInChatsTab() {
		
		
	}
	
	@Test(priority=8)
	public void test008_DisableFreeSMSAndCheck() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to settings > accounts tab. \n" +
				"3. Turn off free sms and verify.\n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		accountScreenObj.toggleFreeSMS();
		
		goToHome();
		StartANewChatScreen newChatObj = homeScreenMenuObj.clickOnComposeConversation();
		
		Assert.assertFalse(isElementPresent(newChatObj.getSmsContactsTab()), "SMS contacts tab appeared even when free sms is turned off");
	}
	
	@Test(priority=9)
	public void test009_ValidateInGroupChat() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to create new group page. \n" +
				"3. Verify that sms contacts should not be listed.\n");
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName("test");
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		Assert.assertFalse(isElementPresent(contactSelectionScreenObj.getSmsContactsTab()), "SMS contacts tab is not present");
		
	}
	
	@Test(priority=10)
	public void test010_InviteToSMSOff() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to settings > Free SMS. \n" +
				"3. Try to invite some users. Validate that all SMS contacts are listed.\n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		FreeSMS freeSmsScreenObj = settingScreenObj.clickOnFreeSMS();
		
		InviteViaSMS inviteSMSScreenObj = freeSmsScreenObj.inviteSMSUsers();
		
		Assert.assertTrue(inviteSMSScreenObj.getCountOfContacts() > 0, "Contacts were not displayed when clicked on 'Invite via SMS'");
		inviteSMSScreenObj.cancelInvite();
	}
	
	@Test(priority=11)
	public void test011_ValidateInFavoritesTab() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to home. \n" +
				"2. Navigate to favorites tab. \n" +
				"3. Assert that 'people on sms' should not be available.\n");
		
		goToHome();
		FavoritesScreen favoritesScreenObj = homeScreenMenuObj.clickOnFavorites_Lbl();
		
		Assert.assertFalse(isElementPresent(favoritesScreenObj.getSmsContactsTab()), "SMS contacts tab appeared in favorites when it should not have");
		
		//enable free sms again
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		accountScreenObj.toggleFreeSMS();
	}
	

}
