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
import com.bsb.hike.ios.screens.ForwardScreen;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;

public class SettingUpWrongPassword extends HikeLibrary {
	
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
	public void test001_ResetAppToRemoveStealthSetup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Reset the app to remove stealth setup done in last test. \n");
		
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountObj = settingsObj.clickOnAccount();
		WelcomeScreen welcomeScreenObj = accountObj.resetAccount();
		
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
		try {
			driver.switchTo().alert().accept();
			pushNotificationScreenObj.clickOnContinue_Btn();
		} catch(Exception e) {
			Reporter.log("Sync address book button did not come");
		}
	}
	
	@Test
	public void test002_ConfirmationPasswordWrongWhileSetup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start process of setting up Stealth. \n" +
				"2. Enter wrong password when entering in confirmation screen.\n");
		
		goToHome();
		HiddenModeFTUEPopUp hiddenModePopup = homeScreenMenuObj.clickOnHideButton(true);
		EnterPasscodeForStealth enterPasscodeObj = hiddenModePopup.clickOnQuickSetup();
		enterPasscodeObj.enterPasscode();
		
		//enter wrong passcode on confirmation screen
		enterPasscodeObj.enterIncompletePasscode();
		Assert.assertTrue(isElementPresent(enterPasscodeObj.getHeader()), "Not on 'Create Passcode' screen when we should be");
		
		//cancelling entering passcode
		homeScreenMenuObj = enterPasscodeObj.cancelEnteringPasscode();
	}
	
	@Test
	public void test003_WrongAttemptsAfterSetup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start process of setting up Stealth. \n" +
				"2. Enter wrong password after setup.\n");

		String messageToSend = "Random message to start chat";
		
		HiddenModeFTUEPopUp hiddenModePopup = homeScreenMenuObj.clickOnHideButton(true);
		EnterPasscodeForStealth enterPasscodeObj = hiddenModePopup.clickOnQuickSetup();
		enterPasscodeObj.enterPasscode();
		enterPasscodeObj.enterPasscode();
		
		//hide a chat
		
		StartANewChatScreen startNewChatObj = (StartANewChatScreen) homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatThreadObj.sendMessage(messageToSend);
		
		goToHome();
		homeScreenMenuObj.toggleHideUnhideChat(HIKE_CONTACT_NAME);
		homeScreenMenuObj.clickOnHideButton(false);
		
		//disable stealth
		homeScreenMenuObj.clickOnHideButton(false);
		
		//enable stealth
		homeScreenMenuObj.clickOnHideButton(true);
		
		//enter wrong password
		enterPasscodeObj.enterWrongPasscode();
		
		Assert.assertFalse(isElementPresent(enterPasscodeObj.getHeader()), "The 'enter passcode' screen is still present after inputting wrong password");
		Assert.assertTrue(isElementPresent(homeScreenMenuObj.getConversationHide()), "The home screen did not launch after entering wrong stealth password");
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false) == null, "The hidden chats became visible after entering wrong password");
		
	}
	
	@Test
	public void test003_ChangePassword() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings > account. \n" +
				"2. Reset stealth password.\n");
		
		goToHome();
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountsObj = settingsObj.clickOnAccount();
		
		Assert.assertTrue(isElementPresent(accountsObj.getResetHiddenMode()), "Reset hidden mode option not available after setting up stealth mode");
		Assert.assertTrue(isElementPresent(accountsObj.getChangePasscode()), "Change passcode button not available after setting up stealth mode");
		Assert.assertTrue(isElementPresent(accountsObj.getHideChatsOnAppExitToggle()), "Hide chats on app exit toggle switch not available after setting up stealth mode");
		
		accountsObj.changeStealthPasscode();
		Assert.assertTrue(isElementPresent(accountsObj.getChangePasscode()), "After changing passcode, app did not come back to Account screen");

	}
	
	@Test
	public void test004_ResetHiddenMode() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings > account. \n" +
				"2. Reset stealth mode.\n");
		
		goToHome();
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountsObj = settingsObj.clickOnAccount();
		
		homeScreenMenuObj = accountsObj.resetHiddenMode();
		Assert.assertTrue(getTextByName(homeScreenMenuObj.getPopupHomeScreenText()).contains("All hidden chats will be deleted after"), "Apt popup giving 2 minute timer did not appear.");
		homeScreenMenuObj.removeHiddenChatHintButton();
		
		Assert.assertFalse(getTextByName(homeScreenMenuObj.getPopupHomeScreenText()).contains("All hidden chats will be deleted after"), "Apt popup giving 2 minute timer did not appear.");
		
	}
	
	@Test
	public void test005_LockUnlockInStealthMode() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings > account. \n" +
				"2. start chatting with hidden mode ueaj.\n");
		
		
		//enter into stealth mode
		homeScreenMenuObj.clickOnHideButton(true);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, true) != null, "The hidden user did not get visible after unhiding chats");
		
		driver.lockScreen(2000);
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, true) == null, "The hidden user got visible after locking and unlocking chats");
		
	}

	@Test
	public void test006_VisibilityOfStealthContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Make a user as hidden. \n" +
				"2. Check user visibility in 1-1 and GC.\n");
		
		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		clickOnElement(newChatScreenObj.getSearchOrEnterNumber());
		enterText(newChatScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		
		Assert.assertTrue(isElementPresent(newChatScreenObj.getNoResultsBy()), "Results were found for a hidden contact in 1-1 chat");
		
		newChatScreenObj.cancelTyping();
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName("Test group");
		GroupContactSelectionScreen contactSelectionObj = newGroupObj.clickOnNextButton();
		contactSelectionObj.searchForAContact(HIKE_CONTACT_NAME);
		
		Assert.assertTrue(contactSelectionObj.getTotalCountOfContacts() > 0, "The hidden contact was not visible in group chat contacts selection");
		
	}
	
	@Test
	public void test007_VisibilityOfStealthContactOnForwardScreen() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Make a user as hidden. \n" +
				"2. Check user visibility in Forward screen.\n");
		
		goToHome();
		
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME_1);
		chatScreenObj.sendMessage("Testing stealth visibility");
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		
		clickOnElement(forwardScreenObj.getContactsTab());
		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		
		Assert.assertTrue(isElementPresent(forwardScreenObj.getNoResultsBy()), "Results were shown when trying to forward message to hidden contact");
		forwardScreenObj.cancelTyping();
		
	}
	
}
