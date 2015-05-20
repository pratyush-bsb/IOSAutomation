package com.bsb.hike.ios.tests.groupChat;

import java.util.ArrayList;
import java.util.List;

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
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class MuteGroup extends HikeLibrary {

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
	public void test001_OwnerMutesGC() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Create new group with some members. \n" +
				"4. Mute group as owner.");

		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		if(groupThreadObj == null) {
			//the group does not exist. create
			goToHome();
			NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
			newGroupObj.typeGroupName(groupName);
			GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
			contactSelectionScreenObj.selectFirstContactInResults();
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
			contactSelectionScreenObj.selectFirstContactInResults();

			groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		}

		groupThreadObj.muteGroup();

		//assert that 'conversation muted' notification appears
		Assert.assertTrue(isElementPresent(groupThreadObj.getMutedConversationImage()), "The notification that group is muted did not come after muting group");

	}

	@Test
	public void test002_ParticipantMutesGC() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create group from server side and add self as participant. \n" +
				"2. Check for notification in UI. \n" +
				"3. Mute group and check validity. \n");

		String serverGroupName = "IOS server automation group";
		goToHome();

		GroupChatMessageSupport groupChatMessageSupportObj = new GroupChatMessageSupport();
		List<String> usersInGroup = new ArrayList<String>();
		usersInGroup.add(getDEFAULT_MSISDN());
		usersInGroup.add(HIKE_NUMBER_2);
		groupChatMessageSupportObj.createGroupAndSendMessage(HIKE_NUMBER_1, usersInGroup, serverGroupName);

		//group created from server side. mute and check validity.
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(serverGroupName, true);
		groupThreadObj.muteGroup();

		Assert.assertTrue(isElementPresent(groupThreadObj.getMutedConversationImage()), "The notification that group is muted did not come after muting group");

	}

	@Test
	public void test003_ResetAndCheckIfGroupMuted() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Reset account and create again. \n" +
				"2. Go to user side group. \n" +
				"3. Check if group still muted. \n");

		String groupName = "IOS automation group";
		goToHome();

		//reset account
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountScreenObj = settingsScreenObj.clickOnAccount();
		WelcomeScreen welcomeScreenObj = accountScreenObj.resetAccount();

		//make account again
		setDEFAULT_MSISDN();
		setPin();
		Thread.sleep(10000);
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

		homeScreenMenuObj = pushNotificationScreenObj.clickOnContinue_Btn();
		Thread.sleep(10000);
		homeScreenMenuObj.removeHiddenChatHintButton();

		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		//group should be muted
		Assert.assertFalse(isElementPresent(groupThreadObj.getMutedConversationImage()), "The notification that group is muted came after resetting account");
		//unmute group for further cases
		groupThreadObj.unmuteGroup();
	}

	@Test
	public void test004_ResetAndCheckIfServerGroupMuted() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Reset account and create again. \n" +
				"2. Go to server side group. \n" +
				"3. Check if group still muted. \n");

		String groupName = "IOS server automation group";
		goToHome();
		//reset account
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountScreenObj = settingsScreenObj.clickOnAccount();
		WelcomeScreen welcomeScreenObj = accountScreenObj.resetAccount();

		//make account again
		setDEFAULT_MSISDN();
		setPin();
		Thread.sleep(10000);
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

		homeScreenMenuObj = pushNotificationScreenObj.clickOnContinue_Btn();
		Thread.sleep(10000);
		homeScreenMenuObj.removeHiddenChatHintButton();
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		//group should be muted
		Assert.assertFalse(isElementPresent(groupThreadObj.getMutedConversationImage()), "The notification that group is muted came after resetting account");

	}

	@Test
	public void test005_DeleteAndCheckIfServerGroupMuted() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Delete account and create again. \n" +
				"2. Go to server side group. \n" +
				"3. Check if group still muted. \n");

		String groupName = "IOS server automation group";
		goToHome();

		//delete account
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountScreenObj = settingsScreenObj.clickOnAccount();
		WelcomeScreen welcomeScreenObj = accountScreenObj.deleteAccount();

		//sign up again
		setDEFAULT_MSISDN();
		setPin();
		Thread.sleep(10000);
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

		homeScreenMenuObj = pushNotificationScreenObj.clickOnContinue_Btn();
		Thread.sleep(10000);
		homeScreenMenuObj.removeHiddenChatHintButton();

		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		//group should be muted
		Assert.assertTrue(!isElementPresent(groupThreadObj.getMutedConversationImage()), "The notification that group is muted came after resetting account");
		//groupThreadObj.unmuteGroup();
	}

}
