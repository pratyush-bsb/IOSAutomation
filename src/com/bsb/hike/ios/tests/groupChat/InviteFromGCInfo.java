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

public class InviteFromGCInfo extends HikeLibrary {

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
	public void test001_InviteFromGCInfoAndValidate() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Create new group with some members. \n" +
				"4. Ensure that apt event is shown in the screen.");
		
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
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_SMS_CONTACT_NAME_1);
			contactSelectionScreenObj.selectFirstContactInResults();
			
			groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		}
		
		//go to group info and check if 'invite to hike' button is available.
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		//see if any sms user is there. If not, add one.
		boolean smsUserInGroup = groupProfileObj.verifyPresenceOfParticularUserType("on sms");
		if(!smsUserInGroup) {
			//add user
			GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
			groupProfileObj = contactSelectionScreenObj.addNewMember(HIKE_SMS_CONTACT_NAME_1, groupName);
			groupThreadObj = groupProfileObj.goBackToThread();
			groupProfileObj = groupThreadObj.clickOnGroupName();
		}
		Assert.assertTrue(isElementPresent(groupProfileObj.getInviteToHikeButton()), "The invite to hike is not present.");
	}
	
	@Test(priority=2)
	public void test002_TapOnInviteButton() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in the previous step. \n" +
				"2. Group should open. \n" +
				"3. Go to profile page of group. \n" +
				"4. Tap on 'invite to button'.");
		
		String groupName = "IOS automation group";
		goToHome();
		
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		
		Assert.assertTrue(isElementPresent(groupProfileObj.getInviteToHikeButton()), "The invite to hike button is not present");
		
		groupProfileObj.clickOnInviteToHike();
		
		Assert.assertTrue(isElementPresent(groupProfileObj.getInviteToHikeButton()), "The invite to hike button is not present");
	}
	
}
