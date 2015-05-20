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
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class LeaveGroupAndDeleteChatThread extends HikeLibrary {
	
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
	public void test001_LeaveGroupCancelValidate() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create a group chat. \n" +
				"2. Go to GC profile. \n" +
				"3. Tap on leave group and validate. \n");
		
		String groupName = "IOS automation group";
		
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
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_2);
			contactSelectionScreenObj.selectFirstContactInResults();
			
			groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		}
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		Assert.assertTrue(isElementPresent(groupProfileObj.getLeaveGroupButton()), "The 'leave group' button is not available");
		
		groupProfileObj.clickOnLeaveButton();
		
		Assert.assertTrue(isElementPresent(groupProfileObj.getLeaveGroupButton()), "The 'leave group' button is not available.");
		Assert.assertTrue(isElementPresent(groupProfileObj.getCancelButton()), "The cancel button did not come after clicking on leave button");
		groupProfileObj.clickOnCancelButton();
		
	}
	
	@Test
	public void test002_GCParticipantLeaveGroup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to GC created in above chat. \n" +
				"2. Go to GC profile. \n" +
				"3. Make user leave group and validate. \n");
		
		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.removeUser(HIKE_CONTACT_NAME_1);
		groupThreadObj = groupProfileObj.goBackToThread();
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("left the group chat!"), "The other members were not notified about user leaving GC.");
		
	}
	
	@Test
	public void test003_GCOwnerLeaveGroup() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create group from server side. \n" +
				"2. Make owner leave group. \n" +
				"3. Validate for actions. \n");
		
		String groupname = "IOS server side group to remove owner";
		goToHome();
		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_2);
		String groupId = groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_1, users, groupname);
		groupChatServerObj.groupCreatorLeavingGroup(HIKE_NUMBER_1, groupId);
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupname, true);
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.equalsIgnoreCase(HIKE_CONTACT_NAME+" left the group chat! "), "Notification for owner leaving was not sent to members.");
	}

}
