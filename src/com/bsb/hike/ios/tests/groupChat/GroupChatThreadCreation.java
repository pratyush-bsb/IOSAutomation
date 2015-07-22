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
import com.bsb.hike.ios.popups.UserBlockedConfirmationToast;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupProfileScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;

public class GroupChatThreadCreation extends HikeLibrary {

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
	public void test001_StartGroupChat() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to options in home - Select New group. \n" +
				"2. New group page should open. \n" +
				"3. Validate presence of elements on the page. \n");

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		Assert.assertTrue(isElementPresent(newGroupObj.getBackButton()), "Back button did not appear");
		Assert.assertEquals(getTextByName(newGroupObj.getScreenHeader()), "New Group", "The header did not come as 'New Group'");
		Assert.assertTrue(isElementPresent(newGroupObj.getAddPhoto()), "Add photo button did not appear");
		Assert.assertTrue(getTextByName(newGroupObj.getNewGroupInfo()).startsWith(newGroupObj.getNewGroupInfoString()), "The group info string did not match with the expected value");
		Assert.assertEquals(getTextByValue(newGroupObj.getTypeGroupName()), newGroupObj.getDefaultGroupText(), "Default text in 'type group name' field did not match");
		Assert.assertFalse(isElementEnabled(newGroupObj.getNextButton()), "The next button is not disabled by default");
		Assert.assertTrue(isKeyboardVisible(), "The keyboard is not visible by default");
		newGroupObj.clickOnAddPhoto();
		Assert.assertTrue(isElementEnabled(newGroupObj.getTakePhotoButton()), "The take photo button is not visible");
		Assert.assertTrue(isElementEnabled(newGroupObj.getChooseExistingButton()), "The choose existing button is not visible");
		newGroupObj.cancelAddingPhoto();
	}

	@Test
	public void test002_ContactSelectionValidation() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Type a group name and click on next. \n" +
				"2. Observe icons on select contacts page. \n" +
				"3. Perform different searches and observe behavior. \n");

		String unsavedContact = "Contact which is not saved";

		String groupName = "IOS Automation group";

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		Assert.assertTrue(isElementPresent(contactSelectionScreenObj.getBackButton()), "Back button not available");
		Assert.assertEquals(getTextByName(contactSelectionScreenObj.getScreenHeader()), contactSelectionScreenObj.getScreenHeaderString(), "The screen header did not match with default value");
		Assert.assertEquals(getTextByValue(contactSelectionScreenObj.getSearchBar()), contactSelectionScreenObj.getSearchOrEnterNumberString(), "The search bar default string did not match");

		contactSelectionScreenObj.getCountOfContacts();

		//search for contact (by name) which does not exist
		contactSelectionScreenObj.searchForAContact(unsavedContact);
		Assert.assertEquals(contactSelectionScreenObj.getTotalCountOfContacts(), 0, "Result came despite searching for unsaved user");

		//search for contact by unsaved MSISDN. Result should come
		contactSelectionScreenObj.searchForAContact(HIKE_NOT_SAVED_USER);
		Assert.assertEquals(contactSelectionScreenObj.getTotalCountOfContacts(), 1, "Only one result should have come.");

		//search for contact that is already saved
		contactSelectionScreenObj.searchForAContact(HIKE_CONTACT_NAME);
		Assert.assertTrue(contactSelectionScreenObj.getTotalCountOfContacts() > 0, "Results should have come for a saved contact.");
	}

	@Test
	public void test003_ContactSelection() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Select two contacts from the list. \n" +
				"2. Start a group. \n" +
				"3. Validate elements. \n");

		//add two contacts
		String groupName = "IOS Automation group";

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();

		contactSelectionScreenObj.clickOnDoneButton(groupName);
	}

	@Test
	public void test004_GroupNameEdit() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Find the test group created in above test. \n" +
				"2. Edit the group name and validate. \n" +
				"3. Edit group name with more than 50 characters and validate. \n");

		String groupName = "IOS Automation group";
		String updatedGroupName = "IOS Automation group updated";
		String longGroupName = "IOS automation group ridiculously long name to check if more than 50 characters are taken or not in the group name";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(updatedGroupName);
		groupThreadObj = groupProfileObj.goBackToThread();

		//validate updated group name
		Assert.assertEquals(getTextByName(groupThreadObj.getGroupChatHeader()), updatedGroupName, "The updtaed group name did not match.");

		//set group name more than 50 characters
		groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(longGroupName);
		groupThreadObj = groupProfileObj.goBackToThread();

		//validate updated group name
		Assert.assertEquals(getTextByName(groupThreadObj.getGroupChatHeader()), longGroupName.substring(0, 50), "The group name is taking more than 50 characters.");

		//set group name back to normal
		groupProfileObj = groupThreadObj.clickOnGroupName();
		groupProfileObj.editGroupName(groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
	}

	@Test
	public void test005_InviteToHike() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Find the test group created in above test. \n" +
				"2. Add SMS user to list. \n" +
				"3. Invite user to hike and validate the occurrence of popup. \n");

		String groupName = "IOS Automation group";

		goToHome();

		//add sms user first
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		GroupContactSelectionScreen groupContactSelectionObj = groupProfileObj.addMember();
		groupProfileObj = groupContactSelectionObj.addNewMember(HIKE_SMS_CONTACT_NAME_1, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();
		groupProfileObj = groupThreadObj.clickOnGroupName();

		//assert validity of 'invite to hike' button
		Assert.assertTrue(isElementPresent(groupProfileObj.getInviteToHikeButton()), "Invite to hike button not visible");

		//click on invite to hike button and assert popup presence
		groupProfileObj.clickOnInviteToHike();

	}

	@Test
	public void test006_CheckFocus() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Find the test group created in above test. \n" +
				"2. Check if keyboard available. \n" +
				"3. Lock device and unlock and check if keyboard available. \n");

		String groupName = "IOS Automation group";

		goToHome();
		homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible when it should not be");

		//lock device
		driver.lockScreen(5);
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible after locking and unlocking device, when it should not be");
	}

	@Test
	public void test007_ComposeMessage() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Find the test group created in above test. \n" +
				"2. Type a message; include emoticons. \n" +
				"3. Send message and assert that same message has been sent. \n");

		String groupName = "IOS Automation group";
		String testMessage = "This is a sample message to send to group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.typeInMessageBox(testMessage);
		groupThreadObj.sendEmoji();
		groupThreadObj.clickOnSendMessage();
		String lastMessage = groupThreadObj.getLastMessage();
		Assert.assertTrue(lastMessage.trim().contains(testMessage), "The same message was not sent that was typed with emoji.");
	}

	@Test
	public void test008_MembersName() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Find the test group created in above test. \n" +
				"2. Go to group info and check if members name are visible. \n" +
				"3. Assert the presence of options - add member, leave group. \n");

		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnProfile();

		//assert presence of options
		Assert.assertTrue(isElementPresent(groupProfileObj.getAddMemberButton()), "Add member button is not present");
		Assert.assertTrue(isElementPresent(groupProfileObj.getLeaveGroupButton()), "Leave group button is not present");

		//get group members and print their names
		List<String> groupUsers = groupProfileObj.getListOfAllUsers();
		for(String eachUser : groupUsers) {
			Reporter.log("User in group : " + eachUser);
		}
	}

	@Test
	public void test009_AddNonAddressBookParticipant() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create group with two non-saved numbers. \n" +
				"2. Send message in group. \n" +
				"3. Add same number again. \n" +
				"4. Ensure that new notification is not sent for same user added");

		String groupName = "Automation group non saved numbers";
		String nonSavedNumber1 = "1234123412";
		String nonSavedNumber2 = "5678567878";
		String messageToSend = "This is a random text to send to group";

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(nonSavedNumber1);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(nonSavedNumber2);
		contactSelectionScreenObj.selectFirstContactInResults();
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		groupThreadObj.sendMessage(messageToSend);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		contactSelectionScreenObj = groupProfileObj.addMember();
		groupProfileObj = contactSelectionScreenObj.addNewMember(nonSavedNumber2, groupName);
		groupThreadObj = groupProfileObj.goBackToThread();

		//assert that no new notification is sent
		Assert.assertEquals(groupThreadObj.getLastMessage(), messageToSend, "New notification was sent for the already added member");
	}

	@Test
	public void test010_AddToContactsFromGCInfo() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create group with saved and unsaved contacts. \n" +
				"2. Go to group info. \n" +
				"3. Long press on each member and check the presence of buttons. \n" +
				"4. Add to contacts button should be there for unsaved number.");

		String groupName = "Automation group mixed contacts";
		String designation = "";

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_NOT_SAVED_SMS_USER);
		contactSelectionScreenObj.selectFirstContactInResults();
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		GroupProfileScreen groupProfileObj = groupThreadObj.clickOnGroupName();
		List<WebElement> allUsersElements = groupProfileObj.getElementsOfAllUsers();

		//iterate over each element and check if popup options are right
		for(WebElement eachUser : allUsersElements) {
			designation = "";
			try {
				WebElement eachUserDesignation = eachUser.findElement(groupProfileObj.getEachUserDesignationSuffix());
				designation = eachUserDesignation.getAttribute("name");
			} catch(Exception e){}
			longPressByElement(eachUser);
			if("owner".equalsIgnoreCase(designation)) {
				//this is owner. assert that no popup has come.
				Assert.assertFalse(isElementPresent(groupProfileObj.getCancelButton()), "Popup came for owner when it should not have come");
			} else if(designation.isEmpty()) {
				//this is saved contact. should have only remove button and cancel button
				Assert.assertTrue(getTextByName(groupProfileObj.getRemoveFromGroup()).contains("Remove"), "Remove contact button did not come for saved user.");
				Assert.assertFalse(getTextByName(groupProfileObj.getBlockContact()).contains("Block"), "Bloc contact button came for saved user when it should not have.");
				Assert.assertTrue(isElementPresent(groupProfileObj.getCancelButton()), "Cancel button did not come for saved user");
				clickOnElement(groupProfileObj.getCancelButton());
			} else if("on sms".equalsIgnoreCase(designation)) {
				//this is unsaved contact. Remove, add to contacts and block button should come
				Assert.assertTrue(getTextByName(groupProfileObj.getRemoveFromGroup()).contains("Remove"), "Remove contact button did not come for saved user.");
				Assert.assertTrue(isElementPresent(groupProfileObj.getAddToContacts()), "Add to contacts button did not come for saved user.");
				Assert.assertTrue(getTextByName(groupProfileObj.getBlockContact()).contains("Block"), "Block contact button did not come for saved user when it should not have.");
				Assert.assertTrue(isElementPresent(groupProfileObj.getCancelButton()), "Cancel button did not come for saved user");
				clickOnElement(groupProfileObj.getCancelButton());
			}
		}
	}

	@Test
	public void test11_HikeUserBlocksGroupOwner() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create group from server side. \n" +
				"2. Block owner of group. \n" +
				"3. Go to group and validate blocked popup. \n" +
				"4. Unblock and validate with sending a message.");

		String groupName = "IOS server side group";

		goToHome();

		GroupChatMessageSupport groupChatServerObj = new GroupChatMessageSupport();
		List<String> users = new ArrayList<String>();
		users.add(getDEFAULT_MSISDN());
		users.add(HIKE_NUMBER_2);
		groupChatServerObj.createGroupAndSendMessage(HIKE_NUMBER_1, users, groupName);

		//block user
		SettingsScreen settingsObj = homeScreenMenuObj.clickOnSettings_Lbl();
		BlockedList blockedListObj = settingsObj.clickOnBlockList();
		blockedListObj.blockContact(HIKE_CONTACT_NAME);
		goToHome();

		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);

		UserBlockedConfirmationToast blockedToast = new UserBlockedConfirmationToast();

		//assert presence of block toast
		Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "The unblock button did not come.");

		//tap on type box and check if keyboard visible.
		clickOnElement(groupThreadObj.getInitialTypeBoxMessage());
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible when it should not be.");

		//unblock user
		blockedToast.unblockUser();

		//Assert.assertTrue(isElementPresent(blockedToast.getBlockedLogo()), "User still blocked after clicking on unblock button.");

		clickOnElement(groupThreadObj.getInitialTypeBoxMessage());
		Assert.assertTrue(isKeyboardVisible(), "keyboard is not coming as popup to type in!");

	}

}
