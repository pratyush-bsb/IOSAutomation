package com.bsb.hike.ios.tests.stealth;

import io.appium.java_client.MobileBy;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ResetHiddenMode;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.EnterPasscodeForStealth;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class GroupChatThread extends HikeLibrary {

	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	String groupID;

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
	public void test001_HidingAGroupChat() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Hide a group chat thread. \n" +
				"2. Enable stealth mode and verify validity. \n");

		String groupName = "IOS server side stealth group";

		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_1);
		groupID = groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_3, users, groupName);
		groupChatServerObj.sendMessageInGC(HIKE_NUMBER_3, groupID);
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		homeScreenMenuObj.toggleHideUnhideChat(groupName);
		homeScreenMenuObj.clickOnHideButton(false);

		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(groupName, true) == null, "The user is visible even after hiding the chat");

	}

	@Test
	public void test002_MuteAndHideAGroup() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Mute a group chat thread. \n" +
				"2. Enable stealth mode and verify validity. \n");

		String groupName = "IOS server side stealth group";

		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();

		GroupThreadScreen groupChatObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupChatObj.muteGroup();
		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);

		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(groupName, true) == null, "The user is visible even after hiding the chat");

		//TODO Add another better check case?

	}

	@Test
	public void test003_VisibilityInForwardScreen() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Mark a group as hidden. \n" +
				"2. Try to forward some random message. \n" +
				"3. Observe the visibility of the hidden group in list of contacts");

		String groupName = "IOS server side stealth group";

		//group is already hidden in last test. try to forward some message
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage("random text");
		chatScreenObj.longPressOnLastMessage();

		newChatScreenObj = chatScreenObj.clickOnForwardButton();
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(newChatScreenObj.getAllContactsPrefix());
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(newChatScreenObj.getContactNameSuffix())).getAttribute("name");
				if(contactName.equalsIgnoreCase(groupName)) {
					Assert.assertTrue(false, "Group appeared in forward contacts screen when it is in hidden mode");
				}
			}
		} catch (Exception e) {

		}

	}
	
	@Test
	public void test004_DeleteHiddenGroup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Mark a group as hidden. \n" +
				"2. Delete the hidden group. \n" +
				"3. Observe the visibility of the deleted group.");
		
		String groupName = "IOS server side stealth group";
		
		//group is already hidden. Unhide and delete
		goToHome();
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		
		homeScreenMenuObj.leaveGroup(groupName);
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		groupChatServerObj.sendMessageInGC(HIKE_NUMBER_3, groupID);
		
		Assert.assertTrue(homeScreenMenuObj.goToSpecificUserThread(groupName, true) == null, "The user is visible even after leaving the group");
		
	}
	
	@Test
	public void test005_ResetHiddenMode() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Mark a group as hidden. \n" +
				"2. Delete the hidden group. \n" +
				"3. Observe the visibility of the deleted group.");
		
		String groupName = "IOS server side stealth group";
		
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_1);
		groupID = groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_3, users, groupName);
		groupChatServerObj.sendMessageInGC(HIKE_NUMBER_3, groupID);
		homeScreenMenuObj.clickOnHideButton(false);
		EnterPasscodeForStealth enterPasscodeObj = new EnterPasscodeForStealth();
		enterPasscodeObj.enterPasscode();
		homeScreenMenuObj.toggleHideUnhideChat(groupName);
		homeScreenMenuObj.clickOnHideButton(false);
		
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		AccountScreen accountsScreenObj = settingsObj.clickOnAccount();
		ResetHiddenMode resetHiddenModeObj = accountsScreenObj.clickOnResetHiddenMode();
		homeScreenMenuObj = resetHiddenModeObj.confirmResetHiddenMode();
		
		
	}

}
