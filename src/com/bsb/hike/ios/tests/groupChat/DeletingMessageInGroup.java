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

public class DeletingMessageInGroup extends HikeLibrary {
	
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
	
	/*@Test
	public void test001_DeleteAllExistingChats() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Iterate over all chats. \n" +
				"2. Delete all chats. \n" +
				"3. Ensure 'please make my day' text is visible.\n");
		
		homeScreenMenuObj.deleteAllExistingChats();
		
	}*/
	
	@Test(priority=1)
	public void test002_ClearChatFromInsideChat() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a group. \n" +
				"2. Send some messages. \n" +
				"3. Clear messages from options.\n" +
				"4. Ensure that the chat thread is empty.");
		
		String textToEnter = "This is a random text to clear.";
		String groupName = "IOS automation group";
		
		//create a new group
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();
		
		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);
		groupThreadObj.sendMessage(textToEnter);
		groupThreadObj.sendMessage(textToEnter);
		groupThreadObj.sendMessage(textToEnter);
		
		//cancel clear chat and assert
		groupThreadObj.clearChatCancel();
		Assert.assertEquals(groupThreadObj.getLastMessage(), textToEnter, "All messages were cleared when they should not have.");

		//clear chat
		groupThreadObj.clearChat();
		Assert.assertTrue(groupThreadObj.getLastMessage().startsWith("Group chat with"), "All messages were not cleared.");
	}
	
	@Test(priority=2)
	public void test003_ValidateOptions() {
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing group. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToForward = "This is a random string to delete";
		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.sendMessage(textToForward);
		groupThreadObj.longPressOnLastMessage();

		//assert presence of copy, delete, forward buttons
		Assert.assertTrue(isElementPresent(groupThreadObj.getCopyMessage()), "Copy message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(groupThreadObj.getDeleteMessage()), "Delete message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(groupThreadObj.getForwardMessage()), "Forward message did not appear after long press on element");
	}
	
	@Test(priority=3)
	public void test004_DeletingSingleMessageAndValidate() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a group. \n" +
				"2. Send some messages. \n" +
				"3. Clear single message.\n" +
				"4. Go back and verify that last message deleted.");
		
		int counter = 1;
		String textToEnter = "Send message";
		String groupName = "IOS Automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		
		groupThreadObj.sendMessage(textToEnter + counter++);
		groupThreadObj.sendMessage(textToEnter + counter++);
		groupThreadObj.sendMessage(textToEnter + counter);
		
		goToHome();
		String currentLastMessage = homeScreenMenuObj.getLastMessageInView(groupThreadObj.getGroupName());
		Assert.assertEquals(currentLastMessage, "You: " + textToEnter + counter, "Last message in the chat thread does not match the one in home screen preview");
		
		//go to chat and delete last message
		groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupThreadObj.getGroupName(), true);
		groupThreadObj.deleteLastMessage();
		currentLastMessage = groupThreadObj.getLastMessage();
		goToHome();
		
		//fetch user's last message sent
		String updatedLastMessage = homeScreenMenuObj.getLastMessageInView(groupThreadObj.getGroupName());
		Assert.assertEquals(updatedLastMessage, "You: " + currentLastMessage, "Last message in the chat thread does not match the one in home screen preview");
	}
	
	@Test(priority=4)
	public void test005_ValidateStickerAsLastMessage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Go to a chat. \n" +
				"2. Send a sticker. \n" +
				"3. Verify 'Via' share button.\n" +
				"4. Delete and verify tha message preview is changed on home screen.");
		
		String groupName = "IOS Automation group";
		
		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.sendSticker();
		groupThreadObj.longPressOnLastMessage();
		Assert.assertTrue(isElementPresent(groupThreadObj.getShareViaMessage()), "Share via option did not appear after long press on sticker");
		groupThreadObj.deleteLastMessage();
		String lastMessageInThread = groupThreadObj.getLastMessage();
		goToHome();
		
		String messageInHomeView = homeScreenMenuObj.getLastMessageInView(groupThreadObj.getGroupName());
		//TODO will fail if sticker is still a last message
		Assert.assertEquals(messageInHomeView, "You: " + lastMessageInThread, "Message in home view did not get updated after deleting sticker");
	}

}
