package com.bsb.hike.ios.tests.settings;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ChooseYourProfilePicturePopUp_NameEnteringScreen;
import com.bsb.hike.ios.popups.ConfirmYourNumberPopUpScreen;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;
import com.support.bsb.hike.qa.apisupport.Hike2HikeMessageSupport;

public class DeleteAccount extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	AccountScreen accountScreenObj = null;
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
	public void test001_VerifyDeleteTab() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Assert validity of 'Delete Account' tagline. \n");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		Assert.assertTrue(isElementPresent(accountScreenObj.getDeleteAccountButton()), "Delete button is not available");
		Assert.assertEquals(getTextByName(accountScreenObj.getDeleteAccountTagline()), accountScreenObj.getDeleteAccountTaglineString(), "The tagline of the 'Delete account' tab did not match");
		
	}
	
	@Test
	public void test002_TapOnDeleteAccount() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on delete account. \n" +
				"2. Assert elements of confirmation popup. \n");
		
		//tap on delete button
		clickOnElement(accountScreenObj.getDeleteAccountButton());
		
		Assert.assertTrue(isElementPresent(accountScreenObj.getConfirmDeleteAccountButton()), "Alert popup did not occur with confirm button");
		Assert.assertTrue(isElementPresent(accountScreenObj.getCancel()), "Alert popup did not occur with cancel button");
	}
	
	@Test
	public void test003_CancelDeletion() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Cancel deleting account. \n" +
				"2. Assert that popup is gone and we are in account page. \n");
		
		clickOnElement(accountScreenObj.getCancel());
		
		Assert.assertFalse(isElementPresent(accountScreenObj.getConfirmDeleteAccountButton()));
		Assert.assertTrue(isElementPresent(accountScreenObj.getDeleteAccountButton()));
	}
	
	@Test
	public void test004_ConfirmDeletion() throws InterruptedException {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Confirm deleting account. \n" +
				"2. Delete the account and create again. \n");
		
		//recieve one message to verify after resigning up
		Hike2HikeMessageSupport hikeMessageSupportObj = new Hike2HikeMessageSupport();

		hikeMessageSupportObj.sendHikeMessage(HIKE_NUMBER_1, getDEFAULT_MSISDN(), "Text message");
		
		//block some user to verify in next test
		settingScreenObj = accountScreenObj.goBackToSettingsScreen();
		BlockedList blockedScreenObj = settingScreenObj.clickOnBlockList();
		blockedScreenObj.blockContact(HIKE_CONTACT_NAME_1);
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		smsCount = settingScreenObj.getFreeSMSCount();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		//delete account
		WelcomeScreen welcomeScreenObj = accountScreenObj.deleteAccount();
		
		Thread.sleep(10000);
		setDEFAULT_MSISDN();
		setPin();
		LoginPhoneNumberScreen loginPhoneNumberObj = welcomeScreenObj.clickOnGetStartedBTN();
		
		loginPhoneNumberObj.clickOnPhoneNumberField();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		PinEnteringScreen pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmBtn();
		
		pinEnteringScreenObj.clickOnPinTextField();
		LoginAboutYouScreen aboutYouScreenObj = pinEnteringScreenObj.setPin(DEFAULT_PIN);
		
		aboutYouScreenObj.clickOnNameEnteringScreen();
		aboutYouScreenObj.setName(DEFAULT_NAME);
		aboutYouScreenObj.clickOnNextBtn();
		
		PushNotificationsScreen pushNotificationScreenObj = ChooseYourProfilePicturePopUp_NameEnteringScreen.clickOnNoBtn();
		
		pushNotificationScreenObj.clickOnContinue_Btn();
		
	}

	@Test
	public void test005_VerifySettingsPersist() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Verify block list remains. \n" +
				"2. Verify the count of free SMSs. \n");
		
		goToHome();
		
		//check if previous chats retained
		ChatThreadScreen chatThreadObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false);
		Assert.assertTrue((chatThreadObj == null), "Previous chats were retained after re signing after deleting of account");
		
		//check if sms count retained
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		int newCount = settingScreenObj.getFreeSMSCount();
		if (smsCount > 100 && newCount == 100) {
			Reporter.log("SMS counts reduced to 100 as previously the count was more than 100");
		} else if (smsCount <= 100 && (smsCount == newCount)) {
			Reporter.log("SMS remained same before and after deleting account.");
		} else {
			Assert.fail("SMS count showed uncanny behaviour. Unequal numbers.");
		}
		
	}
	
}
