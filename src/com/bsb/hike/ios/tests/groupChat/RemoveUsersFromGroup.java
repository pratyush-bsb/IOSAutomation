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
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class RemoveUsersFromGroup extends HikeLibrary {

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
	public void test001_OwnerRemovesHikeUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Remove Hike user. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.removeUser(HIKE_CONTACT_NAME_2);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("left the group chat!"), "The event for removing user was not fired");
	}
	
	@Test
	public void test002_OwnerRemovesSMSUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Remove SMS user. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.removeUser(HIKE_SMS_CONTACT_NAME_1);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("left the group chat!"), "The event for removing user was not fired");
	}
	
	
	@Test
	public void test003_OwnerRemovesAllUsers() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Remove all users. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupThreadObj = groupProfileObj.removeAllUsers(groupName);
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(!lastMessage.equalsIgnoreCase("This group chat has ended"), "The event for removing all users was not fired");
	}
	
	@Test
	public void test004_ParticipantRemovesHikeUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create a server side group. \n" +
				"2. Remove Hike user. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS server automation group";
		
		goToHome();
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_SMS_CONTACT_NUMBER_1);
		groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_1, users, groupName);
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		boolean status = groupProfileObj.participantRemovesUser(HIKE_CONTACT_NAME);
		if(!status) {
			Reporter.log("Participant was not able to remove user");
		}
		groupThreadObj = groupProfileObj.goBackToThread();
		
	}
	
	@Test
	public void test005_ParticipantRemovesSMSUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to server group created in above test. \n" +
				"2. Remove SMS user. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS server automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		boolean status = groupProfileObj.participantRemovesUser(HIKE_SMS_CONTACT_NAME_1);
		if(!status) {
			Reporter.log("Participant was not able to remove user");
		}
		groupThreadObj = groupProfileObj.goBackToThread();
	}
}
