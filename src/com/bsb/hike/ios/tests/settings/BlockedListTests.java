package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class BlockedListTests extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	BlockedList blockScreenObj = null;
	int smsCount = 0;
	
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
	public void test001_ValidateBlockedListPageHeader() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Go to block list page and validate the header. \n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		blockScreenObj = settingScreenObj.clickOnBlockList();
		
		Assert.assertEquals(getTextByName(blockScreenObj.getHeader()), blockScreenObj.getHeaderString(), "Header text does not match!");
	}
	
	@Test
	public void test002_TapOnBlockList() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Validate elements of block list page. \n");
		
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "Plus icon to block contacts is not available");
		
		int blockedContacts = blockScreenObj.countOfBlockedContacts();
		if(blockedContacts > 0) {
			Assert.assertTrue(isElementPresent(blockScreenObj.getSearchForNumberOrName()));
		}
		Reporter.log("Number of blocked contacts : " + blockedContacts);
	}
	
	@Test
	public void test003_AddContactToBlockList() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on add button to add contact to block list. \n" +
				"2. validate elements and then cancel blocking users. \n");
		
		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());
		
		//verify presence of keyboard
		Assert.assertTrue(isKeyboardVisible());
		hideHikeKeyboard();
		Assert.assertFalse(isKeyboardVisible());
		Assert.assertEquals(getTextByName(blockScreenObj.getHeader()), blockScreenObj.getHeaderString(), "Header text does not match!");
		
		//click on first contact and check if done gets enabled
		clickOnElement(blockScreenObj.getFirstContactFound());
		Assert.assertEquals(blockScreenObj.getBlockStatus(0), "1", "The red button did not get activated for the user tapped to block.");
		Assert.assertTrue(isElementEnabled(blockScreenObj.getDoneButton()), "Done button did not get enabled after selecting a contact to block");
		
		//cancel adding and assert
		clickOnElement(blockScreenObj.getStopButton());
		Assert.assertFalse(isElementPresent(blockScreenObj.getDoneButton()), "Done button is coming even after cancelling blocking of users");
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "The plus sign did not come after cancelling user blocking process");

	}
	
	

}
