package com.bsb.hike.ios.tests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.UserBlockedConfirmationToast;
import com.bsb.hike.ios.screens.BlockedList;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.ForwardScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.bsb.hike.ios.screens.StartANewChatScreen;

public class ForwardingMessage extends HikeLibrary {

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
	public void test001_ForwardAMessageValidate() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on any message. \n" +
				"3. Validate if options are populated - copy, delete, forward. \n");

		String textToForward = "This is a random string to forward";

		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToForward);
		chatScreenObj.longPressOnLastMessage();

		Assert.assertTrue(isElementPresent(chatScreenObj.getCopyMessage()), "Copy message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getDeleteMessage()), "Delete message did not appear after long press on element");
		Assert.assertTrue(isElementPresent(chatScreenObj.getForwardMessage()), "Forward message did not appear after long press on element");

	}

	@Test
	public void test002_ForwardScreenSanity() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start chat with any existing chat. \n" +
				"2. Long press on any message and tap on forward. \n" +
				"3. Validate the forward screen elements and flow. \n");

		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardScreenHeader()), "Forward screen header is not 'Forward'");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getChatsTab()), "Chats tab is not available at bottom of forward screen");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getContactsTab()), "Contacts tab is not available at bottom of forward screen");

		//search for any 1-1 contact
		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME_1);
		WebElement currentScreenElement = forwardScreenObj.getcurrentScreenElement();
		Assert.assertFalse(isElementPresentUnderParentElement(forwardScreenObj.getGroupsTab(), currentScreenElement), "Groups tab appeared when only recents tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getRecentsTab()), "Recents tab did not appear when recents tab should have come");

		forwardScreenObj.cancelTyping();

		//check the same for contacts tab
		clickOnElement(forwardScreenObj.getContactsTab());
		Assert.assertTrue(isElementPresent(forwardScreenObj.getPeopleOnHikeTab()), "People on hike tab did not come under contacts category");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getSmsContactsTab()), "SMS contacts tab did not come under contacts category");

		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME_1);

		Assert.assertFalse(isElementPresentUnderParentElement(forwardScreenObj.getSmsContactsTab(), currentScreenElement), "SMS tab appeared when only people on hike tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getPeopleOnHikeTab()), "People on hike tab did not appear when it should have come");

		forwardScreenObj.cancelTyping();

		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_SMS_CONTACT_NAME_1);

		Assert.assertFalse(isElementPresentUnderParentElement(forwardScreenObj.getPeopleOnHikeTab(), currentScreenElement), "People on hike tab appeared when only SMS contacts tab should have come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getSmsContactsTab()), "SMS contacts tab did not appear when only it should have come");
		
		forwardScreenObj.cancelForwarding();
	}

	@Test
	public void test003_RecentChatOnTopInForwardScreen() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Record the most recent chat in home screen. \n" +
				"2. Go to any chat thread and forward some message. \n" +
				"3. Ensure that the chat recorded comes first in forward screen. \n");

		String textToForward = "This is a random string to forward";

		goToHome();
		String mostRecentChat = getTextByName(homeScreenMenuObj.getFirstExistingChatName());

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME_1);
		chatScreenObj.sendMessage(textToForward);

		goToHome();
		newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToForward);
		goToHome();
		chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);

		chatScreenObj.longPressOnLastMessage();

		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		String mostRecentChatInForwardScreen = forwardScreenObj.getMostRecentContactInForwardScreen();

		Assert.assertTrue(mostRecentChat.equalsIgnoreCase(mostRecentChatInForwardScreen), "The most recent chat in home screen did not come as most recent chat in forward screen");
		forwardScreenObj.cancelTyping();

	}

	@Test
	public void test004_ForwardMessageActionBarSanityCheck() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Select any contact to forward message to. \n" +
				"2. Action bar pops up from bottom. \n" +
				"3. Validate the elements of the action bar. \n");

		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getContactsTab());
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

		forwardScreenObj.cancelForwardingActionMenuBar();
		forwardScreenObj.cancelTyping();

		//try forwarding from chats tab
		clickOnElement(forwardScreenObj.getChatsTab());
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		try {
			WebElement contactFound = driver.findElementByIosUIAutomation(forwardScreenObj.getAllContactsPrefix());
			contactFound.click();
		} catch(Exception e) {}

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardButton()), "Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getEditAndForwardButton()), "Edit and Forward button in action bar did not come");
		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardActionBarHeader()), "Forward action bar did not come");

		textInActionBar = getTextByName(forwardScreenObj.getForwardActionBarHeader());
		Assert.assertTrue(textInActionBar.contains(HIKE_CONTACT_NAME), "The header does not contain the selected contact name");

		forwardScreenObj.cancelForwardingActionMenuBar();
		forwardScreenObj.cancelForwarding();

	}

	@Test
	public void test005_ForwardMessageToContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Select a contact to forward message to. \n" +
				"3. Ensure that message is pre populated in the text field. \n");

		String textToForward = "This is a random string to forward";

		goToHome();

		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(HIKE_CONTACT_NAME);
		chatScreenObj.sendMessage(textToForward);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();

		Assert.assertTrue(isElementPresent(forwardScreenObj.getSearchOrEnterNumber()), "Text field to search number did not appear on clicking on 'Forward' button.");
		forwardScreenObj.headersWhenForwardingMessage();
		chatScreenObj = forwardScreenObj.selectContactToForwardMessage();

		Assert.assertEquals(getTextByName(chatScreenObj.getChatThreadHeader()), chatScreenObj.getUserName(), "Message not forwarded to the user selected to forward to.");

		Assert.assertEquals(chatScreenObj.getLastMessage(), textToForward, "Message is not forwarded.");
		goToHome();
		String topChat = getTextByName(homeScreenMenuObj.getFirstExistingChatName());

		Assert.assertTrue((topChat.equalsIgnoreCase(HIKE_CONTACT_NAME_3)), "The contact to which message was forwarded did not come at top in home screen");

	}

	@Test
	public void test006_ForwardMessageToUnsavedContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Type unsaved number to forward message to. \n" +
				"3. Ensure that message is sent to unsaved contact. \n");

		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		chatScreenObj = (ChatThreadScreen) forwardScreenObj.forwardMessageToContact("+911234567890", false);
		Assert.assertTrue((chatScreenObj != null) && (chatScreenObj.getUserName().equalsIgnoreCase("+911234567890")), "The message was not forwarded to unsaved contact");
		goToHome();
	}

	@Test
	public void test007_ForwardMessageToSavedContactViaMSISDN() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Type saved number to forward message to. \n" +
				"3. Ensure that message is sent to saved contact. \n");


		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getContactsTab());
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_NUMBER_1);
		try {
			WebElement contactFound = driver.findElementByName(HIKE_CONTACT_NAME);
			contactFound.click();
		} catch(Exception e) {}

		chatScreenObj = (ChatThreadScreen) forwardScreenObj.clickOnForwardButtonInActionBar(HIKE_CONTACT_NAME, false);
		Assert.assertTrue((chatScreenObj != null) && (chatScreenObj.getUserName().equalsIgnoreCase(HIKE_CONTACT_NAME)), "The message was not forwarded to unsaved contact");
	}

	@Test
	public void test008_ForwardMessageToUnsavedRecent() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Have an unsaved chat in recent. \n" +
				"2. Forward any random message. \n" +
				"3. Unsaved contact should come in recent. \n");

		String randomMessage = "testing123";
		String messageToForward = "Forward123";
		String unsavedContact = "+911234567890";

		goToHome();
		StartANewChatScreen newChatScreenObj = homeScreenMenuObj.clickOnComposeConversation();
		ChatThreadScreen chatScreenObj = newChatScreenObj.startNewHikeChat(unsavedContact);
		chatScreenObj.sendMessage(randomMessage);

		goToHome();
		chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.sendMessage(messageToForward);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		chatScreenObj = (ChatThreadScreen) forwardScreenObj.forwardMessageToContact(unsavedContact, false);

		Assert.assertTrue((chatScreenObj != null) && (chatScreenObj.getUserName().equalsIgnoreCase(unsavedContact)), "The message was not forwarded to unsaved contact");
		goToHome();
		String topChat = getTextByName(homeScreenMenuObj.getFirstExistingChatName());
		Assert.assertTrue(topChat.equalsIgnoreCase(unsavedContact), "The unsaved contact did not come at top in the home screen");
	}

	@Test
	public void test009_ForwardToInternationalNumber() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on forward button. \n" +
				"2. Type saved number to forward message to. \n" +
				"3. Ensure that message is sent to saved contact. \n");


		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		chatScreenObj = (ChatThreadScreen) forwardScreenObj.forwardMessageToContact(INTERNATIONAL_HIKE_USER, false);
		Assert.assertTrue((chatScreenObj != null) && (chatScreenObj.getUserName().equalsIgnoreCase(INTERNATIONAL_HIKE_USER)), "The message was not forwarded to international contact");
	}

	@Test
	public void test0010_ForwardMessageToBlockedContact() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Block a contact. \n" +
				"2. Forward message to blocked contact and validate. \n");

		goToHome();
		SettingsScreen settingsScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		BlockedList blockedListObj = settingsScreenObj.clickOnBlockList();
		blockedListObj.blockContact(HIKE_CONTACT_NAME);

		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getContactsTab());
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		Assert.assertTrue(isElementPresent(forwardScreenObj.getNoResultsBy()), "Blocked contact was shown in 'Contacts' tab of forward screen");

		forwardScreenObj.cancelTyping();
		forwardScreenObj.clickOnSearchTab();
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_NUMBER_1);
		try {
			List<WebElement> allContactsFound = driver.findElements(MobileBy.IosUIAutomation(forwardScreenObj.getAllContactsPrefix()));
			for (WebElement eachContact : allContactsFound) {
				String userCaptured = eachContact.getAttribute("name");
				if(userCaptured.equalsIgnoreCase(HIKE_NUMBER_1)) {
					new TouchAction(driver).press(eachContact).perform();
					break;
				}
			}
		} catch(Exception e) {}
		chatScreenObj = (ChatThreadScreen) forwardScreenObj.clickOnForwardButtonInActionBar(HIKE_CONTACT_NAME, false);
		UserBlockedConfirmationToast blockToastObj = new UserBlockedConfirmationToast();
		Assert.assertTrue(isElementPresent(blockToastObj.getUnblockButton()), "Blocked user did not have 'Unblock' toast in chat thread");
		blockToastObj.unblockUser();
		Assert.assertTrue(isElementPresent(blockToastObj.getUnblockButton()), "Blocked user did not have 'Unblock' toast in chat thread");
	}

	@Test
	public void test011_ForwardURL() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Have a URl in some chat thread. \n" +
				"2. Try to forward the URL and validate the same. \n");

		String url = "www.google.com";

		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.sendMessage(url);
		ForwardScreen forwardScreenObj = new ForwardScreen();
		try {
			List<WebElement> allChats = driver.findElementsByIosUIAutomation(chatScreenObj.getAllChatMessages());
			allChats.get(allChats.size() - 1).click();
			Thread.sleep(5000); //wait for page to load
			//changeToWebView();
			driver.findElement(MobileBy.IosUIAutomation(".toolbars()[0].buttons().withPredicate(\"name MATCHES \'Share\'\")")).click();
			driver.findElement(MobileBy.name("Forward Link")).click();
		} catch (Exception e) {
			Reporter.log("Not able to automate web view share process.");
		}

		Assert.assertTrue(isElementPresent(forwardScreenObj.getForwardScreenHeader()), "Forward page did not come after clicking on share option in webview");
		chatScreenObj = (ChatThreadScreen) forwardScreenObj.forwardMessageToContact(HIKE_CONTACT_NAME, false);

		Assert.assertTrue((chatScreenObj != null) && (chatScreenObj.getUserName().equalsIgnoreCase(HIKE_CONTACT_NAME)), "The URL was not forwarded to contact");
		String lastMessage = chatScreenObj.getLastMessage();
		Assert.assertTrue(lastMessage.equalsIgnoreCase(url), "The forwarded link and the recieved link do not match");
	}

	@Test
	public void test0012_CopyPasteInEditWindow() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start a chat and copy a message. \n" +
				"2. Forward some other message. \n" +
				"3. Paste message in edit window. Ensure sanity. \n");

		String toForward = "To forward text ";
		String toCopy = "copied text";

		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME_1, false);
		chatScreenObj.sendMessage(toCopy);
		chatScreenObj.longPressOnLastMessage();
		chatScreenObj.clickOnCopyButton();

		chatScreenObj.sendMessage(toForward);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
		clickOnElement(forwardScreenObj.getContactsTab());
		clickOnElement(forwardScreenObj.getSearchOrEnterNumber());
		enterText(forwardScreenObj.getSearchOrEnterNumber(), HIKE_CONTACT_NAME);
		try {
			WebElement contactFound = driver.findElementByName(HIKE_CONTACT_NAME);
			contactFound.click();
		} catch(Exception e) {}
		clickOnElement(forwardScreenObj.getEditAndForwardButton());
		longPress(forwardScreenObj.getEditForwardingMessageWindow());
		clickOnElement(pasteButton);

		String newText = getTextByValue(forwardScreenObj.getEditForwardingMessageWindow());

		Assert.assertTrue(newText.equalsIgnoreCase(toForward+toCopy), "The text was not copied in the edit window of the forwarded message");
		clickOnElement(forwardScreenObj.getCancelEditingMessageButton());
		forwardScreenObj.cancelTyping();
	}

	@Test
	public void test0013_EditForwardedMessageSanityCheck() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Add and remove text from the pre populated field. \n" +
				"2. Send button should be activated. \n" +
				"3. Tap on send button should send the message. \n");

		String textToForward = "This is a random string to forward";
		goToHome();
		ChatThreadScreen chatScreenObj = (ChatThreadScreen) homeScreenMenuObj.goToSpecificUserThread(HIKE_CONTACT_NAME, false);
		chatScreenObj.sendMessage(textToForward);
		chatScreenObj.longPressOnLastMessage();
		ForwardScreen forwardScreenObj = chatScreenObj.clickOnForwardButton();
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

	@AfterMethod
	public void tearDown(ITestResult result) {

		if(result.getStatus() == ITestResult.FAILURE) {

			//it is a failure. create screenshot and push according to file name and test name

			try {
				String className = 	result.getTestClass().getName();
				String testName = result.getTestName();
				String failureScreenshotName = className+"_"+testName;
				File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
				FileUtils.copyFile(file, new File(failureScreenshotName));
			} catch(Exception e) {
				Reporter.log("Not able to store screenshot");
			}

		}
	}

}
