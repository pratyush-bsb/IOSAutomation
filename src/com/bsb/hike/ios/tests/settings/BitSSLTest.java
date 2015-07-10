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

public class BitSSLTest extends HikeLibrary {

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
	public void test001_ValidateSSLHeaderAndTagline() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Navigate to accounts screen." +
				"3. Validate the header and tagline. \n");

		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();

		Assert.assertTrue(isElementPresent(accountScreenObj.getBitSSLSwitch()), "128 SSl bit switch is not available.");
		Assert.assertEquals(getTextByValue(accountScreenObj.getBitSSLSwitch()), "0", "128 bit SSl is enable by default for Indian user.");
	}

	@Test
	public void test002_ChangeSettingsAndReset() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enable 128-bit SSL. \n" +
				"2. Reset account." +
				"3. Validate that setting should retain. \n");

		accountScreenObj.toggle128bitSSL();
		WelcomeScreen welcomeScreenObj = accountScreenObj.resetAccount();

		//make account again
		setDEFAULT_MSISDN();
		setPin();
		//Thread.sleep(10000);
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

		homeScreenMenuObj = pushNotificationScreenObj.clickOnContinue_Btn();
		try {
			driver.switchTo().alert().accept();
			pushNotificationScreenObj.clickOnContinue_Btn();
		} catch(Exception e) {
			Reporter.log("Sync address book button did not come");
		}
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingScreenObj.clickOnAccount();
		
		Assert.assertEquals(getTextByValue(accountScreenObj.getBitSSLSwitch()), "0", "128 bit SSl was not enabled after resetting.");
		
		//disable for future cases
		accountScreenObj.toggle128bitSSL();
		
	}

}
