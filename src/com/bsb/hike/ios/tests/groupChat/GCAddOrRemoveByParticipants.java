package com.bsb.hike.ios.tests.groupChat;

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
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class GCAddOrRemoveByParticipants extends HikeLibrary {
	
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
	public void test001_AddParticipantByParticipant() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create a group chat from server side. \n" +
				"2. Go to GC profile. \n" +
				"3. Add participants by participant. \n");
		
		String groupName = "IOS automation server group";
		
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_2);
		groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_1, users, groupName);
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		if(groupThreadObj == null) {
			Assert.assertTrue(false, "Server side group was not created/reflected in the UI. Failing test");
		}
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		GroupContactSelectionScreen groupContactSelectionObj = groupProfileObj.addMember();
		groupProfileObj = groupContactSelectionObj.addNewMember(HIKE_CONTACT_NAME_2, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("Added"), "User added by participant notification was not sent.");
	}
	
	@Test
	public void test002_AddSMSUserByParticipant() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to server chat created in above test. \n" +
				"2. Go to GC profile. \n" +
				"3. Add SMS user by participant. \n");
		
		String groupName = "IOS automation server group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		if(groupThreadObj == null) {
			Assert.assertTrue(false, "Server side group was not created/reflected in the UI. Failing test");
		}
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		GroupContactSelectionScreen groupContactSelectionObj = groupProfileObj.addMember();
		groupProfileObj = groupContactSelectionObj.addNewMember(HIKE_SMS_CONTACT_NAME_1, groupName);
		
		Assert.assertTrue(groupProfileObj.verifyPresenceOfParticularUser(HIKE_SMS_CONTACT_NAME_1), "The SMS user was not added to GC info list.");
		Assert.assertEquals(groupProfileObj.getUserTypeForUser(HIKE_SMS_CONTACT_NAME_1), "on SMS", "For SMS user, in GC, 'on SMS' designation was not displayed");
		groupThreadObj = groupProfileObj.goBackToThread();
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "User added by participant notification was not sent.");
	}
	
	@Test
	public void test003_AddDNDUserByParticipant() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to server chat created in above test. \n" +
				"2. Go to GC profile. \n" +
				"3. Add DND user by participant. \n");
		
		String groupName = "IOS automation server group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		if(groupThreadObj == null) {
			Assert.assertTrue(false, "Server side group was not created/reflected in the UI. Failing test");
		}
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		//GroupContactSelectionScreen groupContactSelectionObj = groupProfileObj.addMember();
		//groupProfileObj = groupContactSelectionObj.addNewMember(HIKE_DND_NAME_1, groupName);
		
		Assert.assertTrue(groupProfileObj.verifyPresenceOfParticularUser(HIKE_DND_NAME_1), "The DND user was not added to GC info list.");
		Assert.assertEquals(groupProfileObj.getUserTypeForUser(HIKE_DND_NAME_1), "on SMS", "For DND user, in GC, 'on SMS' designation was not displayed");
		groupThreadObj = groupProfileObj.goBackToThread();
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("is on DND"), "User added by participant notification was not sent.");
	}
	
	@Test
	public void test004_ParticipantRemovesParticipant() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to server chat created in above test. \n" +
				"2. Go to GC profile. \n" +
				"3. Remove user by participant. \n");
		
		String groupName = "IOS automation server group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		if(groupThreadObj == null) {
			Assert.assertTrue(false, "Server side group was not created/reflected in the UI. Failing test");
		}
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		List<WebElement> allUsers = groupProfileObj.getElementsOfAllUsers();
		longPressByElement(allUsers.get(0));
		
		Assert.assertFalse(isElementPresent(groupProfileObj.getRemoveFromGroup()), "Remove user from group option came for participant.");
	}

}
