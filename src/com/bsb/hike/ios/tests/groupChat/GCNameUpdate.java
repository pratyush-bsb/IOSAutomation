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

public class GCNameUpdate extends HikeLibrary {
	
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
	public void test001_UpdateGroupName() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Create new group with some members. \n" +
				"4. Ensure that apt event is shown in the screen.");
		
		String groupName = "IOS automation group";
		String groupNameUpdated = "IOS automation group update";
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_SMS_CONTACT_NAME_2);
		contactSelectionScreenObj.selectFirstContactInResults();
		
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(groupNameUpdated);
		groupThreadObj = groupProfileObj.goBackToThread();
		String lastMessage = groupThreadObj.getLastMessage();
		
		Assert.assertTrue(lastMessage.contains("changed the group name"), "The event for group name change was not fired.");
		
		//change name back for further cases.
		groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(groupName);
		
	}
	
	//TODO add server side test to change group name by participant
	
	/*@Test
	public void test003_UpdateGroupNameInNoNetwork() {
		Dimension d = driver.manage().window().getSize();
		int x = d.getHeight();
		int y = d.getWidth();
		Reporter.log("h : " + x + " w : " + y);
		driver.swipe(320, 481, 320, 200, 2000);
	}*/
}
