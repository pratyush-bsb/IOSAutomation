package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class AccountPageInSettings extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingsObj = null;
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
	public void test001_AccountPageElements() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings page from home page. \n" +
				"2. Assert presence of all elements. \n");
		
		goToHome();
		settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		
		
		//assert elements
		Assert.assertEquals(getTextByName(settingsObj.getAccount_LBL()), settingsObj.getAccountLabelText(), "The label text did not match");
		
	}
	
	@Test(priority=2)
	public void test002_GoToAccountPage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to account from settings page. \n" +
				"2. Assert presence of all elements. \n");
		
		accountScreenObj = settingsObj.clickOnAccount();
		
		Assert.assertEquals(getTextByName(accountScreenObj.getAccountScreenHeader()), accountScreenObj.getAccountScreenHeaderString(), "Header text does not match");
		Assert.assertTrue(isElementPresent(accountScreenObj.getBackButton()), "Back button not present");
		Assert.assertTrue(isElementPresent(accountScreenObj.getFreeSMSToIndiaSwitch()), "Free SMS to India switch not available");
		Assert.assertTrue(isElementPresent(accountScreenObj.getBitSSLSwitch()), "128 bit SSL switch not available");
		Assert.assertTrue(isElementPresent(accountScreenObj.getLastSeenSwitch()), "Last seen switch not available");
		Assert.assertTrue(isElementPresent(accountScreenObj.getResetAccountButton()), "Reset account button not available");
		Assert.assertTrue(isElementPresent(accountScreenObj.getDeleteAccountButton()), "Delete account button not available");
		
	}
	
	@Test(priority=3)
	public void test003_GoBackToSettingsPage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go back to settings page. \n" +
				"2. Assert that back goes to settings page. \n");
		
		settingsObj = accountScreenObj.goBackToSettingsScreen();
		
		Assert.assertEquals(getTextByName(settingsObj.getSettingsTitle_LBL()), settingsObj.getHeaderValue(), "Settings page did not load after coming back from Account page");

	}

}
