package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;

public class NotificationsInSettings extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	NotificationsScreen notificationObj = null;
	
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
	public void test001_OpenNotifications() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Go to notifications page. \n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		notificationObj = settingScreenObj.clickOnNotifications();
		Assert.assertEquals(getTextByName(notificationObj.getHeader()), notificationObj.getHeaderString(), "The header string did not match");
		Assert.assertEquals(getTextByName(notificationObj.getNotificationTone()), notificationObj.getDefaultNotificationToneName(), "Notification tone is not set as default");
		Assert.assertEquals(getTextByValue(notificationObj.getMessageAlertSwitch()), "1", "Message alerts is not enabled by default");
		Assert.assertEquals(getTextByValue(notificationObj.getInChatSounds()), "1", "In chat alerts is not enabled by default");
		Assert.assertEquals(getTextByValue(notificationObj.getVibrationSwitch()), "1", "Vibration is not enabled by default");
		Assert.assertEquals(getTextByValue(notificationObj.getMessagePreviewSwitch()), "1", "Message Preview is not enabled by default");
		Assert.assertEquals(getTextByValue(notificationObj.getDisableNudgeSwitch()), "1", "Disable nudge is not enabled by default");
		Assert.assertEquals(getTextByValue(notificationObj.getContactJoiningSwitch()), "1", "Contact joining hike alert is not enabled by default");
		
	}
	
	@Test(priority=2)
	public void test002_GoBackAndVerify() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go back to settings page. \n" +
				"2. Verify that settings page is opened. \n");
		
		settingScreenObj = notificationObj.goBackToSettingsPage();
		
		Assert.assertEquals(getTextByName(settingScreenObj.getSettingsTitle_LBL()), settingScreenObj.getHeaderValue(), "Settings page did not come after clicking back in notifications page");
		
	}

}
