package com.bsb.hike.ios.tests.groupChat;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AddToContacts;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;

public class AddToContactsFromGCInfo extends HikeLibrary {

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
	public void test001_AddUnsavedContactToGC() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start a new group chat. \n" +
				"2. Add unsaved contact. \n" +
				"3. Validate presence unsaved contact. \n");

		String groupName = "IOS automation group";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		if(groupThreadObj == null) {
			//group not there. Create new
			NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
			newGroupObj.typeGroupName(groupName);
			GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
			contactSelectionScreenObj.selectFirstContactInResults();
			contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
			contactSelectionScreenObj.selectFirstContactInResults();

			groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		}

		//add unsaved contact
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember("1234567890", groupName);
		groupThreadObj = groupProfileObj.goBackToThread();

		String lastMessage = groupThreadObj.getLastMessage();

		Assert.assertTrue(lastMessage.startsWith("Added"), "The event for adding new member to group was not fired");
	}
	
	@Test
	public void test002_AddToContacts() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to group chat created in first test. \n" +
				"2. Go to profile of group. \n" +
				"3. Add unsaved contact to contacts. \n");

		String groupName = "IOS automation group";
		String contactToSave = "+911234567890";
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		AddToContacts addToContactsObj = groupProfileObj.addUnsavedToContacts(contactToSave);
		
		Assert.assertFalse((addToContactsObj == null), "AddToObjects did not load after clicking on add contact");
		
		Assert.assertTrue(addToContactsObj.findContactNumberElement(contactToSave), "Add To Contact page did not load.");
		addToContactsObj.cancelAdding();
		
	}

}
