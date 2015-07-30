package com.bsb.hike.ios.tests.groupChat;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;

public class NewUserJoinEvents extends HikeLibrary {
	
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
	public void test001_CreateGroupChatWithNUJ() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Create new group with some SMS members. \n" +
				"4. Ensure that apt event is shown in the screen.");
		
		String groupName = "IOS automation group";
		
		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_SMS_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_SMS_CONTACT_NAME_2);
		contactSelectionScreenObj.selectFirstContactInResults();
		
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		
		Assert.assertEquals(getTextByName(groupThreadObj.getInitialTypeBoxMessage()), groupThreadObj.getInitialTypeBoxMessageString(), "Initial type box text was not 'Group message...'");
		
	}
	
	/*@Test
	public void test002_MakeSMSUserJoinHike() {
		
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group created in the first step. \n" +
				"2. Make an SMS user join hike. \n" +
				"3. Verify if proper push notifications are generated. \n" +
				"4. Ensure that apt event is shown in the screen.");
		
		String groupName = "IOS automation group";
		
	}*/

}
