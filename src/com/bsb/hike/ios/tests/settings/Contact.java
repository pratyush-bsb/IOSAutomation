package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.FeedbackMail;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class Contact extends HikeLibrary {
	
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

	@Test
	public void test001_ValidateMailDraftElements() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings. \n" +
				"2. Click on 'contact' option." +
				"3. Verify that the email draft is pre loaded and the body is preloaded. \n");
		
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		FeedbackMail feedbackMailObj = settingsObj.clickOnContact();
		String bodyContact = feedbackMailObj.getMessageBody();
		
		Assert.assertEquals(getTextByValue(feedbackMailObj.getToField()), feedbackMailObj.getToFieldPrefilled(), "The found 'To' field value did not match with the expected one.");
		Assert.assertEquals(getTextByValue(feedbackMailObj.getSubjectField()), feedbackMailObj.getSubjectFieldPrefilled(), "The found 'Subject' field value did not match with the expected one.");
		
		Assert.assertTrue(bodyContact.contains("hike Version"), "The body of mail does not contain hike version");
		Assert.assertTrue(bodyContact.contains("Build"), "The body of mail does not contain build number");
		Assert.assertTrue(bodyContact.contains("iOS Version"), "The body of mail does not contain iOS version");
		Assert.assertTrue(bodyContact.contains("Phone Number"), "The body of mail does not contain phone number");
		Assert.assertTrue(bodyContact.contains("Device Model"), "The body of mail does not contain device model");
		Assert.assertTrue(bodyContact.contains("Sent from my iPhone"), "The body of mail does not contain the text 'Sent from my iPhone'.");
		
	}

}
