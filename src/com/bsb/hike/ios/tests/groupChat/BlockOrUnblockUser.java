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
import com.bsb.hike.ios.popups.UserBlockedConfirmationToast;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class BlockOrUnblockUser extends HikeLibrary {
	
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
	public void test001_BlockAUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start a new chat. \n" +
				"2. Block a user. \n" +
				"3. Send some messages and validate. \n");
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatThreadObj.blockUser();
		UserBlockedConfirmationToast blockedToast = new UserBlockedConfirmationToast();
		
		//assert presence of block toast
		Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "The unblock button did not come.");
		
		//tap on type box and check if keyboard visible.
		clickOnElement(chatThreadObj.getInitialTypeBoxMessage());
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible when it should not be.");
		
	}
	
	@Test
	public void test002_UnblockUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start a new chat. \n" +
				"2. Unblock a user. \n" +
				"3. Send some messages and validate. \n");
		
		UserBlockedConfirmationToast blockedToast = new UserBlockedConfirmationToast();
		
		//check if blocked
		Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "User is not blocked when he/she should have been.");
		blockedToast.unblockUser();
		ChatThreadScreen chatThreadObj = new ChatThreadScreen(HIKE_CONTACT_NAME);
		//Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "User still blocked after clicking on unblock button.");
		
		clickOnElement(chatThreadObj.getInitialTypeBoxMessage());
		Assert.assertTrue(isKeyboardVisible(), "keyboard is not coming as popup to type in!");
		
	}

	@Test
	public void test003_BlockUserFromGroup() throws InterruptedException {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Block a user. \n" +
				"2. Start group chat with blocked user and other users. \n" +
				"3. Validate the block situation. \n");
		
		String serverGroupName = "IOS server side group";
		goToHome();
		StartANewChatScreen startNewChatObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChatObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatThreadObj.blockUser();
		UserBlockedConfirmationToast blockedToast = new UserBlockedConfirmationToast();
		
		//assert presence of block toast
		Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "The unblock button did not come.");
		
		//make server side group
		GroupChatMessageSupport groupChatMessageSupportObj = new GroupChatMessageSupport();
		List<String> usersInGroup = new ArrayList<String>();
		usersInGroup.add(getDEFAULT_MSISDN());
		usersInGroup.add(HIKE_NUMBER_2);
		groupChatMessageSupportObj.createGroupAndSendMessage(HIKE_NUMBER_3, usersInGroup, serverGroupName);
		
		groupChatMessageSupportObj = new GroupChatMessageSupport();
		usersInGroup.clear();
		usersInGroup.add(getDEFAULT_MSISDN());
		usersInGroup.add(HIKE_NUMBER_2);
		groupChatMessageSupportObj.createGroupAndSendMessage(HIKE_NUMBER_1, usersInGroup, serverGroupName);
		
		//ensure that no group chat received
		Thread.sleep(3000);
		goToHome();
		chatThreadObj = homeScreenMenuObj.clickOnFirstChat();
		//chatThreadObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(serverGroupName, true);
		Assert.assertTrue(!(serverGroupName.equalsIgnoreCase(chatThreadObj.getUserName())), "Group notification came to blocked user");
		
		//unblock and still no notification should come
		SettingsScreen settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		BlockedList blockedListObj = settingScreenObj.clickOnBlockList();
		blockedListObj.unblockContact(HIKE_CONTACT_NAME);
		goToHome();
		Thread.sleep(3000);
		chatThreadObj = homeScreenMenuObj.clickOnFirstChat();
		Assert.assertTrue(!(serverGroupName.equalsIgnoreCase(chatThreadObj.getUserName())), "Group notification came to blocked user");
	}
	
	/*@Test
	public void test004_BlockedParticipantInGroup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Block a user. \n" +
				"2. Start group chat with blocked user and other users. \n" +
				"3. Validate the block situation. \n");
		
		String serverGroupName = "IOS server side group";
		
		
		
	}*/
}
