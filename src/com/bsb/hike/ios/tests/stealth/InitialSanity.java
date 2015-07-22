package com.bsb.hike.ios.tests.stealth;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ChooseYourProfilePicturePopUp_NameEnteringScreen;
import com.bsb.hike.ios.popups.ConfirmYourNumberPopUpScreen;
import com.bsb.hike.ios.popups.HiddenModeFTUEPopUp;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.EnterPasscodeForStealth;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;

public class InitialSanity extends HikeLibrary {

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
	public void test001_DefaultModeOnSetup() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Perform a reset and sign in again. \n" +
				"2. Check that stealth popup is not there. \n");

		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountScreenObj = settingsScreenObj.clickOnAccount();
		WelcomeScreen welcomeScreenObj = accountScreenObj.resetAccount();
		Thread.sleep(10000);

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
		try {
			driver.switchTo().alert().accept();
			pushNotificationScreenObj.clickOnContinue_Btn();
		} catch(Exception e) {
			Reporter.log("Sync address book button did not come");
		}
		
		settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		accountScreenObj = settingsScreenObj.clickOnAccount();
		goToHome();
		Thread.sleep(5000);
		
		//check if 'hide your chats' tab is available.
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getHiddenChatHintButton()), "The tooltip to setup hidden chat was not displayed within 10 seconds of resetting and signin up account");
	}
	
	@Test
	public void test002_StealthModeSetupIntroPopup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on 'hi' button on left in home screen. \n" +
				"2. Validate the presence of popup to setup hidden mode. \n");
		
		HiddenModeFTUEPopUp hiddenModeFTUEObj = homeScreenMenuObj.clickOnHideButton(true);
		
		//validate
		Assert.assertTrue(isElementPresent(hiddenModeFTUEObj.getHiddenModeSubTitle_LBL()), "The sub title is not present in hidden mode FTUE popup");
		Assert.assertTrue(isElementPresent(hiddenModeFTUEObj.getIntroducingHiddenModeTitle_LBL()), "The title is not present in hidden mode FTUE popup");
		Assert.assertTrue(isElementPresent(hiddenModeFTUEObj.getQuickSetup_BTN()), "The 'quick setup' button is not available");
		clickOnElement(homeScreenMenuObj.getConversationHide());
		
	}
	
	@Test
	public void test003_StealthModeSetup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on 'hi' button on left in home screen. \n" +
				"2. Setup stealth mode and validate the same. \n");
		
		HiddenModeFTUEPopUp hiddenModeFTUEObj = homeScreenMenuObj.clickOnHideButton(true);
		EnterPasscodeForStealth createPasscodeObj = hiddenModeFTUEObj.clickOnQuickSetup();
		createPasscodeObj.enterPasscode();
		Assert.assertTrue(isElementPresent(createPasscodeObj.getDeletePasscodeButton()), "The confirm passcode screen did not appear");
		
		createPasscodeObj.enterPasscode();
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getHiddenModeSetupConfirmation()), "The popup for hidden mode setup completion did not come");
		clickOnElement(homeScreenMenuObj.getHiddenChatHintButton());
		
	}
	
	@Test
	public void test004_MarkAChatAsHidden() throws InterruptedException {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Ensure that an active chat is available. \n" +
				"2. Hide the chat by swiping left. \n" +
				"3. Validate the hiding of chat");
		
		String messageToSend = "Random message to start chat";
		
		/*StartANewChatScreen startNewChatObj = (StartANewChatScreen) homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatThreadObj.sendMessage(messageToSend);
		
		goToHome();*/
		Thread.sleep(10000);
		homeScreenMenuObj.toggleHideUnhideChat(HIKE_CONTACT_NAME);
		homeScreenMenuObj.clickOnHideButton(false);
		
		Assert.assertFalse((homeScreenMenuObj.getSpecificChatElement(HIKE_CONTACT_NAME) == null), "The chat was not hidden after hiding it through steatlh mode");
		
		
	}

}
