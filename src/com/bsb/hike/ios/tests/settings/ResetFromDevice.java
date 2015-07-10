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
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;

public class ResetFromDevice extends HikeLibrary {
	
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
	public void test001_ValidateResetTagline() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Go to account page. \n" +
				"3. Validate the tagline of reset account tab.");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		Assert.assertEquals(getTextByValue(accountScreenObj.getResetAccountTagline()), accountScreenObj.getResetAccountTaglineString(), "The tagline string did not match in reset account");
		
	}

	@Test
	public void test002_TapOnResetAccount() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on reset account. \n" +
				"2. Assert elements of confirmation popup. \n");
		
		//tap on delete button
		clickOnElement(accountScreenObj.getResetAccountButton());
		
		Assert.assertTrue(isElementPresent(accountScreenObj.getResetAccountButton()), "Alert popup did not occur with confirm button");
		Assert.assertTrue(isElementPresent(accountScreenObj.getCancel()), "Alert popup did not occur with cancel button");
	}
	
	@Test
	public void test003_CancelResettingAccount() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Cancel resetting account. \n" +
				"2. Assert that popup is gone and we are in account page. \n");
		
		clickOnElement(accountScreenObj.getCancel());
		
		Assert.assertFalse(isElementPresent(accountScreenObj.getCancel()));
		Assert.assertTrue(isElementPresent(accountScreenObj.getResetAccountButton()));
	}
	
	@Test
	public void test004_ConfirmResetAccount() throws InterruptedException {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Confirm resetting account. \n" +
				"2. Reset the account and create again. \n");
		
		//delete account
		WelcomeScreen welcomeScreenObj = accountScreenObj.resetAccount();
		
		//Thread.sleep(10000);
		setDEFAULT_MSISDN();
		setPin();
		singleTapWithTwoFingers(welcomeScreenObj.getSignUpLogo());
		welcomeScreenObj.selectStagingEnvironment();
		LoginPhoneNumberScreen loginPhoneNumberObj = welcomeScreenObj.clickOnGetStartedBTN();
		
		loginPhoneNumberObj.clickOnPhoneNumberField();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		PinEnteringScreen pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		
		pinEnteringScreenObj.clickOnPinTextField();
		LoginAboutYouScreen aboutYouScreenObj = pinEnteringScreenObj.setPin(DEFAULT_PIN);
		
		aboutYouScreenObj.clickOnNameEnteringScreen();
		aboutYouScreenObj.setName(DEFAULT_NAME);
		aboutYouScreenObj.clickOnNextBtn();
		
		PushNotificationsScreen pushNotificationScreenObj = ChooseYourProfilePicturePopUp_NameEnteringScreen.clickOnNoBtn();
		
		pushNotificationScreenObj.clickOnContinue_Btn();
		
		//check if sms count is same
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
