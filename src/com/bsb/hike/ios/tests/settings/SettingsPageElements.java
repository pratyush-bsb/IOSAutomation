package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class SettingsPageElements extends HikeLibrary {
	
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
	public void test001_goToSettingsPage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Assert presence of all elements. \n");
		
		goToHome();
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		
		Assert.assertEquals(getTextByName(settingsObj.getSettingsTitle_LBL()), settingsObj.getHeaderValue(), "The header label does not match.");
		Assert.assertTrue(isElementPresent(settingsObj.getNotifications_LBL()));
		Assert.assertTrue(isElementEnabled(settingsObj.getBlockedList_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getAccount_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getFreeSMS_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getSystemHealth_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getMedia_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getEnterKeyIsSend_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getFAQ_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getContact_LBL()));
		Assert.assertTrue(isElementPresent(settingsObj.getMadeWithLoveFooter()));
				
	}

}
