package com.bsb.hike.ios.tests.groupChat;

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

public class AddMembersToGroup extends HikeLibrary {
	
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
	
	@Test(priority=1)
	public void test001_OwnerAddsUser() {
		
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
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_CONTACT_NAME_2, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
		
	}
	
	@Test(priority=2)
	public void test002_OwnerAddsSMSUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to existing group. \n" +
				"2. Add SMS user. \n" +
				"3. Ensure that apt event is shown in the screen.");
		
		String groupName = "IOS automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_SMS_CONTACT_NAME_1, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
	}
	
	//TODO add cases to add user (hike and sms) from participant side (server side test)

}
