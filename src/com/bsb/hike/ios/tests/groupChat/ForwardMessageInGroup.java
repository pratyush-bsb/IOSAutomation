package com.bsb.hike.ios.tests.groupChat;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.ForwardScreen;
import com.bsb.hike.ios.screens.GroupContactSelectionScreen;
import com.bsb.hike.ios.screens.GroupThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.NewGroupScreen;
import com.bsb.hike.ios.screens.RecordAudio;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class ForwardMessageInGroup extends HikeLibrary {

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
	public void test001_ForwardingMessageValidate() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing group. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToForward = "This is a random string to forward";
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

	@Test(priority=2)
	public void test002_ForwardScreenSanity() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing group. \n" +
				"2. Long press on any message and tap on forward. \n" +
				"3. Validate the forward screen elements and flow. \n");

		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.longPressOnLastMessage();

		ForwardScreen forwardScreenObj = groupThreadObj.clickOnForwardButton();

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardScreenHeader()), "Forward screen header is not 'Forward'");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getChatsTab()), "Chats tab is not available at bottom of forward screen");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getContactsTab()), "Contacts tab is not available at bottom of forward screen");

		//search for any 1-1 contact
		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME_1);

		Assert.assertFalse(isElementPresent(forwardScreenObj.getGroupsTab()), "Groups tab appeared when only recents tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getRecentsTab()), "Recents tab did not appear when recents tab should have come");

		forwardScreenObj.cancelTyping();

		//search for any group name
		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), groupName);

		Assert.assertTrue(isElementPresent(forwardScreenObj.getGroupsTab()), "Groups tab did not appear when groups tab should have come");
		Assert.assertFalse(isElementPresent(forwardScreenObj.getRecentsTab()), "Recents tab appeared when recents tab should not have come");

		forwardScreenObj.cancelTyping();

		//check the same for contacts tab
		clickOnElement(forwardScreenObj.getContactsTab());
		Assert.assertTrue(isElementPresent(forwardScreenObj.getPeopleOnHikeTab()), "People on hike tab did not come under contacts category");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getSmsContactsTab()), "SMS contacts tab did not come under contacts category");

		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME_1);

		Assert.assertFalse(isElementPresent(forwardScreenObj.getSmsContactsTab()), "SMS tab appeared when only people on hike tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getPeopleOnHikeTab()), "People on hike tab did not appear when it should have come");

		forwardScreenObj.cancelTyping();

		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_SMS_CONTACT_NAME_1);

		Assert.assertFalse(isElementPresent(forwardScreenObj.getPeopleOnHikeTab()), "People on hike tab appeared when only SMS contacts tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getSmsContactsTab()), "SMS contacts tab did not appear when only it should have come");

	}

	@Test(priority=3)
	public void test003_ForwardAudioAndValidate() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to a chat and make a recording. \n" +
				"2. Forward the recording to some GC. \n" +
				"3. Validate the elements in forward action bar. \n");

		String groupName = "IOS Automation group";

		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME_1);
		RecordAudio recordAudioScreen = chatScreenObj.clickOnAudioButton();
		recordAudioScreen.recordAudio();

		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();

		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), groupName);
		try {
			WebElement contactFound = driver.findElementByIosUIAutomation(forwardScreenObj.getAllContactsPrefix());
			contactFound.click();
		} catch(Exception e) {}

		Assert.assertFalse(isElementPresent(forwardScreenObj.getEditAndForwardButton()), "Edit and Forward button in action bar came while forwarding audio");

		String textInActionBar = getTextByName(forwardScreenObj.getForwardActionBarHeader());
		Assert.assertTrue(textInActionBar.contains(groupName), "The header does not contain the selected contact name");

		forwardScreenObj.clickOnForwardMessage();

		goToHome();
		String topChat = getTextByName(homeScreenMenuObj.getFirstExistingChatName());

		Assert.assertTrue((topChat.equalsIgnoreCase(groupName)), "The contact to which message was forwarded did not come at top in home screen");

	}

	@Test(priority=4)
	public void test004_RecentChatOnTopInForwardScreen() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Record the most recent chat in home screen. \n" +
				"2. Go to any chat thread and forward some message. \n" +
				"3. Ensure that the chat recorded comes first in forward screen. \n");

		String textToForward = "This is a random string to forward";

		goToHome();
		String mostRecentChat = getTextByName(homeScreenMenuObj.getFirstExistingChatName());

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME_1);
		chatScreenObj.sendMessage(textToForward);

		chatScreenObj.longPressOnLastMessage();

		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		String mostRecentgroupInForwardScreen = forwardScreenObj.getFirstGroupnameInForwardScreen();

		Assert.assertTrue(mostRecentChat.equalsIgnoreCase(mostRecentgroupInForwardScreen), "The most recent group chat in home screen did not come as most recent group in forward screen");
		forwardScreenObj.cancelTyping();

	}

	@Test(priority=5)
	public void test005_ForwardMessageActionBarSanityCheck() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Select any contact to forward message to. \n" +
				"2. Action bar pops up from bottom. \n" +
				"3. Validate the elements of the action bar. \n");

		String groupName = "IOS Automation group";

		goToHome();

		//validate headers with chat
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		try {
			WebElement contactFound = driver.findElementByIosUIAutomation(forwardScreenObj.getAllContactsPrefix());
			contactFound.click();
		} catch(Exception e) {}

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getEditAndForwardButton()), "Edit and Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardActionBarHeader()), "Forward action bar did not come");

		String textInActionBar = getTextByName(forwardScreenObj.getForwardActionBarHeader());
		Assert.assertTrue(textInActionBar.contains(HIKE_CONTACT_NAME), "The header does not contain the selected contact name");

		forwardScreenObj.cancelForwarding();

		//validate headers with GC

		chatScreenObj.longPressOnLastMessage();
		forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), groupName);
		try {
			WebElement contactFound = driver.findElementByIosUIAutomation(forwardScreenObj.getAllContactsPrefix());
			contactFound.click();
		} catch(Exception e) {}

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getEditAndForwardButton()), "Edit and Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardActionBarHeader()), "Forward action bar did not come");

		textInActionBar = getTextByName(forwardScreenObj.getForwardActionBarHeader());
		Assert.assertTrue(textInActionBar.contains(groupName), "The header does not contain the selected contact name");

		forwardScreenObj.cancelForwarding();

		//try validating from chat to GC

		goToHome();
		chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false);
		chatScreenObj.longPressOnLastMessage();
		forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), groupName);
		try {
			WebElement contactFound = driver.findElementByIosUIAutomation(forwardScreenObj.getAllContactsPrefix());
			contactFound.click();
		} catch(Exception e) {}

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getEditAndForwardButton()), "Edit and Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardActionBarHeader()), "Forward action bar did not come");

		textInActionBar = getTextByName(forwardScreenObj.getForwardActionBarHeader());
		Assert.assertTrue(textInActionBar.contains(groupName), "The header does not contain the selected contact name");

		forwardScreenObj.cancelForwarding();

	}

	@Test(priority=6)
	public void test006_ForwardMessageToContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Select a contact to forward message to. \n" +
				"3. Ensure that message is pre populated in the text field. \n");

		String textToForward = "This is a random string to";
		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.sendMessage(textToForward);
		groupThreadObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = groupThreadObj.clickOnForwardButton();

		Assert.assertTrue(isElementPresent(forwardScreenObj.getSearchOrEnterNumber()), "Text field to search number did not appear on clicking on 'Forward' button.");
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible when it should not be");
		forwardScreenObj.headersWhenForwardingMessage();
		ChatThreadScreen chatScreenObj = forwardScreenObj.selectContactToForwardMessage();

		Assert.assertEquals(getTextByName(chatScreenObj.getChatThreadHeader()), chatScreenObj.getUserName(), "Message not forwarded to the user selected to forward to.");

		Assert.assertEquals(chatScreenObj.getLastMessage(), textToForward, "Forwarded message is not pre populated.");

	}

	@Test(priority=7)
	public void test007_ForwardAndEditMessageToContact() {


		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Add and remove text from the pre populated field. \n" +
				"2. Send button should be activated. \n" +
				"3. Tap on send button should send the message. \n");

		String textToForward = "This is a random string to forward";
		String groupName = "IOS Automation group";

		goToHome();
		GroupThreadScreen groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(groupName, true);
		groupThreadObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = groupThreadObj.clickOnForwardButton();
		forwardScreenObj.selectContactToEditandForwardMessage();

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Forward button did not come in the 'Edit and Forward' screen");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getCancelEditingMessageButton()), "Cancel editing button did not come in the 'Edit and Forward' screen");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getEmojiIconInEditScreen()), "Emoji icon did not come in the 'Edit and Forward' screen");

		Assert.assertEquals(getTextByValue(forwardScreenObj.getEditForwardingMessageWindow()), textToForward.substring(0, textToForward.length()-7), "The edited texts do not match");

		//delete full text and check if forward button disabled
		forwardScreenObj.deleteFullText();
		Assert.assertFalse(isElementEnabled(forwardScreenObj.getForwardButton()), "Forward button was not disabled after removing all text in edit field");

		//cancel forwarding. edit window to close
		clickOnElement(forwardScreenObj.getCancelEditingMessageButton());
		Assert.assertFalse(isElementPresent(forwardScreenObj.getEditForwardingMessageWindow()), "Editing window did not disappear after cancelling forward");

		//add some text and check
		forwardScreenObj.selectContactToEditandForwardMessage();
		forwardScreenObj.typeInMessageBox("forward");
		Assert.assertEquals(getTextByValue(forwardScreenObj.getEditForwardingMessageWindow()), textToForward, "The edited texts do not match");

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Send button is not available");
		forwardScreenObj.clickOnForwardMessage();

	}

	@Test(priority=8)
	public void test008_CreateNewGCToCheckInForwardScreen() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Create new GC. \n" +
				"2. Try to forward some message. \n" +
				"3. The newly created GC should be there in forward screen. \n");

		String groupName = "IOS automation group forward";
		String existingGroupname = "IOS Automation group";

		goToHome();
		NewGroupScreen newGroupObj = homeScreenMenuObj.clickOnNewGroup_Lbl();
		newGroupObj.typeGroupName(groupName);
		GroupContactSelectionScreen contactSelectionScreenObj = newGroupObj.clickOnNextButton();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME);
		contactSelectionScreenObj.selectFirstContactInResults();
		contactSelectionScreenObj.searchForAContactWithoutClear(HIKE_CONTACT_NAME_1);
		contactSelectionScreenObj.selectFirstContactInResults();

		GroupThreadScreen groupThreadObj = contactSelectionScreenObj.clickOnDoneButton(groupName);

		goToHome();
		groupThreadObj = (GroupThreadScreen) homeScreenMenuObj.goToSpecificUserThread(existingGroupname, true);
		groupThreadObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = groupThreadObj.clickOnForwardButton();

		String mostRecentgroupInForwardScreen = forwardScreenObj.getFirstGroupnameInForwardScreen();

		Assert.assertTrue(groupName.equalsIgnoreCase(mostRecentgroupInForwardScreen), "The most recent group chat in home screen did not come as most recent group in forward screen");

	}

}
