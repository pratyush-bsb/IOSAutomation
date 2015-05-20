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
import com.bsb.hike.ios.screens.CameraScreen;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class GroupChatEvents extends HikeLibrary {
	
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
	public void test001_GroupChatCreationEvent() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Create new group with some members. \n" +
				"4. Ensure that apt event is shown in the screen.");
		
		String groupName = "IOS automation group";
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();
		
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		
		//get last message and check that it's a proper event
		String lastMessage = groupThreadObj.getLastMessage();
		Assert.assertTrue(lastMessage.startsWith("Group chat with"), "Proper event was not displayed for group chat creation.");
		
	}
	
	@Test
	public void test002_GroupChatImageChange() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Change group image. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.clickOnCameraIcon();
		CameraScreen cameraScreenObj = groupProfileObj.takePhoto();
		groupProfileObj = cameraScreenObj.clickPhoto(groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("changed the group image"), "The event for group image change was not fired");
	}
	
	@Test
	public void test003_GroupNameChange() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Change group name. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		String newGroupName = "IOS automation group new";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(newGroupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("changed the group name"), "The event for group name change was not fired.");
		
		//change name back for further cases.
		groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(groupName);
		
	}
	
	@Test
	public void test004_AddNewMember() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Add new member. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_CONTACT_NAME_2, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
	}
	
	@Test
	public void test005_AddDNDUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Open an existing group. \n" +
				"2. Add DND member. \n" +
				"3. Validate that proper event is fired in chat thread. \n");
		
		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_DND_NAME_1, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
	}
	
	@Test
	public void test006_ServerGroupChatCreation() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create a server side chat with self as member. \n" +
				"2. Check if message relayed. \n");
		
		String groupName = "IOS server automation group";
		String message = "Hello ! I am the group intiator";
		
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_2);
		groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_1, users, groupName);
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		String lastMessage = groupThreadObj.getLastChatMessage();
		
		Assert.assertEquals(lastMessage, message, "Server side group was not created with self as user");
		
	}
	
	@Test
	public void test007_ParticipantGroupImageChange() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in previous test. \n" +
				"2. change group image as participant. \n" +
				"3. Validate if notification sent.");
		
		String groupName = "IOS server automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.clickOnCameraIcon();
		CameraScreen cameraScreenObj = groupProfileObj.takePhoto();
		groupProfileObj = cameraScreenObj.clickPhoto(groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("changed the group image"), "The event for group image change was not fired");
		
	}
	
	@Test
	public void test008_ParticipantGroupNameChange() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in previous test. \n" +
				"2. change group name as participant. \n" +
				"3. Validate if notification sent.");
		
		String groupName = "IOS server automation group";
		String newGroupName = "IOS automation group new";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(newGroupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("changed the group name"), "The event for group name change was not fired.");
		
		//change name back for further cases.
		groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(groupName);
	}
	
	@Test
	public void test009_ParticipantAddsMember() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in previous test. \n" +
				"2. add new member as participant. \n" +
				"3. Validate if notification sent.");
		
		String groupName = "IOS server automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_CONTACT_NAME_3, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
	}
	
	@Test
	public void test010_ParticipantAddsDNDUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in previous test. \n" +
				"2. add new DND member as participant. \n" +
				"3. Validate if notification sent.");
		
		String groupName = "IOS server automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_DND_NAME_1, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
		
	}

}
