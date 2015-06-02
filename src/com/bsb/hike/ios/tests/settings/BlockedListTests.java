package com.bsb.hike.ios.tests.settings;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class BlockedListTests extends HikeLibrary {

	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	BlockedList blockScreenObj = null;
	int smsCount = 0;

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
	public void test001_ValidateBlockedListPageHeader() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Navigate to settings page. \n" +
				"2. Go to block list page and validate the header. \n");

		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		blockScreenObj = settingScreenObj.clickOnBlockList();

		Assert.assertEquals(getTextByName(blockScreenObj.getHeader()), blockScreenObj.getHeaderString(), "Header text does not match!");
	}

	@Test
	public void test002_TapOnBlockList() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Validate elements of block list page. \n");

		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "Plus icon to block contacts is not available");

		int blockedContacts = blockScreenObj.countOfBlockedContacts();
		if(blockedContacts > 0) {
			Assert.assertTrue(isElementPresent(blockScreenObj.getSearchForNumberOrName()));
		}
		Reporter.log("Number of blocked contacts : " + blockedContacts);
	}

	@Test
	public void test003_AddContactToBlockListCancel() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on add button to add contact to block list. \n" +
				"2. validate elements and then cancel blocking users. \n");

		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());

		//verify presence of keyboard
		Assert.assertTrue(isKeyboardVisible());
		hideHikeKeyboard();
		Assert.assertFalse(isKeyboardVisible());
		Assert.assertEquals(getTextByName(blockScreenObj.getHeader()), blockScreenObj.getHeaderString(), "Header text does not match!");

		//click on first contact and check if done gets enabled
		clickOnElement(blockScreenObj.getFirstContactFound());
		Assert.assertEquals(blockScreenObj.getBlockStatus(0), "1", "The red button did not get activated for the user tapped to block.");
		Assert.assertTrue(isElementEnabled(blockScreenObj.getDoneButton()), "Done button did not get enabled after selecting a contact to block");

		//cancel adding and assert
		clickOnElement(blockScreenObj.getStopButton());
		Assert.assertFalse(isElementPresent(blockScreenObj.getDoneButton()), "Done button is coming even after cancelling blocking of users");
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "The plus sign did not come after cancelling user blocking process");

	}

	@Test
	public void test004_ReopenBlockListAndCancel() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on add button to add contact to block list. \n" +
				"2. Cancel and verify that user is not blocked. \n" +
				"3. Try adding again to blocked list. \n" +
				"4. Cancel again and verify that user is still not added to blocked list. \n");

		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());

		clickOnElement(blockScreenObj.getFirstContactFound());
		String user = blockScreenObj.getFirstContactName();
		clickOnElement(blockScreenObj.getStopButton());

		//assert that contact is not blocked actually
		Assert.assertFalse(blockScreenObj.checkIfContactBlocked(user), "The contact was blocked even after cancelling blocking");

		//reopen block list
		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());
		clickOnElement(blockScreenObj.getFirstContactFound());
		clickOnElement(blockScreenObj.getFirstContactFound());
		user = blockScreenObj.getFirstContactName();
		clickOnElement(blockScreenObj.getStopButton());

		//assert that contact is not blocked actually
		Assert.assertFalse(blockScreenObj.checkIfContactBlocked(user), "The contact was blocked even after cancelling blocking");
	}
	
	@Test
	public void test005_BlockAContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on add button to add contact to block list. \n" +
				"2. Add contact to block list. \n" +
				"3. Verify that the contact is blocked. \n");
		
		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());
		blockScreenObj.blockContact(HIKE_CONTACT_NAME);
		
		//verify that focus shifts to privacy page
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "Focus did not shift to privacy page after blocking an account");
		Assert.assertTrue(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME), "The contact was not blocked when it should be");
		//unblock for future cases
		blockScreenObj.unblockContact(HIKE_CONTACT_NAME);
	}
	
	@Test
	public void test006_BlockUnsavedContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with unsaved contact. \n" +
				"2. Go to block list. \n" +
				"3. Block unsaved contact and verify. \n");
		
		goToHome();
		StartANewChatScreen startNewChat = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatThreadObj = startNewChat.startNewHikeChat(HIKE_NOT_SAVED_USER);
		chatThreadObj.sendMessage("to block");
		
		goToHome();
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		blockScreenObj = settingScreenObj.clickOnBlockList();
		
		blockScreenObj.clickOnAddContactToBlock();
		clickOnElement(blockScreenObj.getSearchForNumberOrName());
		blockScreenObj.blockContact(HIKE_NOT_SAVED_USER);
		
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "Focus did not shift to privacy page after blocking an account");
		Assert.assertTrue(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME), "The contact was not blocked when it should be");
		//unblock for future cases
		blockScreenObj.unblockContact(HIKE_NOT_SAVED_USER);
		
	}
	
	@Test
	public void test007_BlockMultipleContacts() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to add contacts to block screen. \n" +
				"2. Block multiple accounts. \n" +
				"3. Assert that all selected accounts are blocked. \n");
		
		List<String> contactsToBlock = new ArrayList<String>();
		contactsToBlock.add(HIKE_CONTACT_NAME);
		contactsToBlock.add(HIKE_CONTACT_NAME_1);
		
		blockScreenObj.clickOnAddContactToBlock();
		blockScreenObj.blockMultipleContacts(contactsToBlock);
		
		Assert.assertTrue(isElementPresent(blockScreenObj.getAddContactToBlock()), "Focus did not shift to privacy page after blocking an account");
		Assert.assertTrue(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME), "The contact was not blocked when it should be");
		Assert.assertTrue(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME_1), "The contact was not blocked when it should be");
		
		
	}
	
	@Test
	public void test008_UnblockContact() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to blocked list screen. \n" +
				"2. Unblock blocked contacts. \n" +
				"3. Verify if contacts are unblocked. \n");
		
		blockScreenObj.unblockContact(HIKE_CONTACT_NAME);
		blockScreenObj.unblockContact(HIKE_CONTACT_NAME_1);
		
		Assert.assertFalse(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME), "The contact was blocked when it should not be");
		Assert.assertFalse(blockScreenObj.checkIfContactBlocked(HIKE_CONTACT_NAME_1), "The contact was blocked when it should not be");
	}
}
