package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.screens.interfaces.ChatScreenInterface;

public class ChatThreadScreen extends AppiumLibrary implements ChatScreenInterface {

	public ChatThreadScreen(String userName) {
		this.userName = userName;
		waitForChatThreadToLoad();
	}

	private void waitForChatThreadToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				String chatLoaded = getTextByName(chatThreadHeader);
				pageLoaded = chatLoaded.equalsIgnoreCase(userName);
			} catch(Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) {}
			}
		}
	}

	protected String initialTypeBoxMessageString = "Hike message...";
	protected String freeSmsTypeBoxString = "Free SMS...";

	//overlay options
	protected By profileButton = MobileBy.name("Profile");
	protected By callButton = MobileBy.name("Call");
	protected By inviteToHike = MobileBy.name("Invite to hike");
	protected By addToContacts = MobileBy.name("Add to Contacts");

	//more options
	protected By blockButton = MobileBy.name("Block");
	protected String userName;

	protected By messageCount = MobileBy.IosUIAutomation(".staticTexts()[1]");
	
	//Getter methods

	public By getCameraButton() {
		return cameraButton;
	}
	
	public By getAddToContacts() {
		return addToContacts;
	}
	
	public By getShareViaMessage() {
		return shareViaMessage;
	}

	public By getCopyMessage() {
		return copyMessage;
	}

	public By getDeleteMessage() {
		return deleteMessage;
	}

	public By getForwardMessage() {
		return forwardMessage;
	}

	public By getMessageCount() {
		return messageCount;
	}

	public By getInviteToHike() {
		return inviteToHike;
	}

	public By getPhotosButton() {
		return photosButton;
	}

	public By getVideoButton() {
		return videoButton;
	}

	public By getAudioButton() {
		return audioButton;
	}

	public By getLocationButton() {
		return locationButton;
	}

	public By getContactButton() {
		return contactButton;
	}

	public By getChatThreadScreenBackButton() {
		return chatThreadScreenBackButton;
	}

	public By getSendMessageButton() {
		return sendMessageButton;
	}

	//return name of the user
	public By getChatThreadHeader() {
		return chatThreadHeader;
	}

	public By getChatThreadOverflowButton() {
		return chatThreadOverflowButton;
	}

	public By getChatbodyChatWithHeader() {
		return chatbodyChatWithHeader;
	}

	public By getStickerButton() {
		return stickerButton;
	}

	public By getChatMessageTypeBox() {
		return chatMessageTypeBox;
	}

	public By getInitialTypeBoxMessage() {
		return initialTypeBoxMessage;
	}

	public By getFreeSmsTypeBoxMessage() {
		return freeSmsTypeBoxMessage;
	}

	public By getEmojiButton() {
		return emojiButton;
	}

	public By getAttachmentButton() {
		return attachmentButton;
	}

	public By getProfileButton() {
		return profileButton;
	}

	public By getCallButton() {
		return callButton;
	}

	public By getThemeButton() {
		return themeButton;
	}

	public By getMoreButton() {
		return moreButton;
	}

	public By getBlockButton() {
		return blockButton;
	}

	public By getEmailButton() {
		return emailButton;
	}

	public By getClearChatButton() {
		return clearChatButton;
	}

	public By getCancelButton() {
		return cancelButton;
	}

	public String getUserName() {
		return userName;
	}

	public String getAllChatMessages() {
		return allChatMessages;
	}

	public String getChatMessageText() {
		return chatMessageText;
	}

	public String getChatMessageTime() {
		return chatMessageTime;
	}

	public String getInitialTypeBoxMessageString() {
		return initialTypeBoxMessageString;
	}

	public String getFreeSmsTypeBoxString() {
		return freeSmsTypeBoxString;
	}

	//Public methods

	public void clickOverlaybutton() {
		clickOnElement(chatThreadOverflowButton);
	}

	public void clickOnStickers() {
		clickOnElement(stickerButton);
	}

	public void clickOnEmoji() {
		clickOnElement(emojiButton);
	}

	public void clickOnAttachment() {
		clickOnElement(attachmentButton);
	}

	public UserProfileScreen clickOnProfile() {
		clickOnElement(chatThreadOverflowButton);
		clickOnElement(profileButton);
		return new UserProfileScreen(this.userName);
	}

	public void clickOnCall() {
		clickOnElement(callButton);
	}

	public void clickOnTheme() {
		clickOnElement(themeButton);
	}

	public void clickOnMoreOption() {
		clickOnElement(moreButton);
	}

	public void clickOnEmailChatButton() {
		clickOnElement(emailButton);
	}

	public void clickOnClearChatButton() {
		clickOnElement(clearChatButton);
	}

	public void clickOnSendMessage() {
		clickOnElement(sendMessageButton);
	}

	public void clickOnCancelButton() {
		clickOnElement(cancelButton);
	}

	public void clickOnBlockButton() {
		clickOnElement(blockButton);
	}

	public HomeScreenMenu goBack() {
		clickOnElement(chatThreadScreenBackButton);
		return new HomeScreenMenu();
	}

	public void clickOnOverflowButton() {
		clickOnElement(chatThreadOverflowButton);
	}

	public UserProfileScreen clickOnUserName() {
		clickOnElement(chatThreadHeader);
		return new UserProfileScreen(userName);
	}

	public void typeInMessageBox (String textToType) {
		enterTextWithClear(chatMessageTypeBox, textToType);
	}

	public List<WebElement> allChatMessagesCells() {
		return driver.findElementsByIosUIAutomation(allChatMessages);
	}

	public void sendMessage(String textToSend) {
		typeInMessageBox(textToSend);
		clickOnSendMessage();
	}

	public String getLastMessage() {

		int numberOfMessages = allChatMessagesCells().size();
		By getLastMessage = MobileBy.IosUIAutomation(getAllChatMessages() + "[" + String.valueOf(numberOfMessages-1) + "].staticTexts()[0]");
		String textCapturedLater = getTextByName(getLastMessage);
		return textCapturedLater;
	}

	public void assertInviteToHikeButton() {

		clickOnElement(chatThreadOverflowButton);
		Assert.assertTrue(isElementPresent(inviteToHike), "Invite to Hike button not present for sms user.");
	}

	public int countOfMessagesTyped() {

		int count = 0;
		String totalText = getTextByName(messageCount);
		count = Integer.parseInt(totalText.split("#")[1].trim());
		return count;
	}

	public void longPressOnLastMessage() {

		try {
			List<WebElement> allChatMessagesList = driver.findElementsByIosUIAutomation(allChatMessages);
			MobileElement lastMessage = (MobileElement) allChatMessagesList.get(allChatMessagesList.size() - 1);
			new TouchAction(driver).longPress(lastMessage).perform();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StartANewChatScreen clickOnForwardButton() {
		if(!isElementPresent(forwardMessage)) {
			longPressOnLastMessage();
		}
		clickOnElement(forwardMessage);
		return new StartANewChatScreen();
	}
	
	public void clickOnCopyButton() {
		clickOnElement(copyMessage);
	}

	public void clickOnDeleteButton() {
		clickOnElement(deleteMessage);
	}

	public void clearChat() {

		clickOnOverflowButton();
		clickOnMoreOption();
		clickOnClearChatButton();
		clickOnClearChatButton();
	}


	public void deleteLastMessage() {

		longPressOnLastMessage();
		clickOnDeleteButton();
		
	}

	//this method sends a fixed sticker
	public void sendSticker() {
		
		clickOnStickers();
		
		try {
			WebElement firstSticker = driver.findElements(allStickersInCurrentTab).get(0);
			driver.tap(1, firstSticker, 1);
		} catch (Exception e) {
			Reporter.log("unable to fetch stickers on current tab");
		}
		
	}
	
	public void sendEmoji() {
		
		clickOnEmoji();
		
		try {
			WebElement firstEmoji = driver.findElements(allEmojisInCurrentTab).get(0);
			driver.tap(1, firstEmoji, 1);
		} catch(Exception e) {
			Reporter.log("Unable to fetch emojis in current tab");
		}
	}

	@Override
	public String getCurrentTextInTypeBox() {
		String text = getTextByValue(chatMessageTypeBox);
		return text;
	}
	
	public void blockUser() {
		clickOnOverflowButton();
		if(isElementPresent(moreButton)) {
			clickOnMoreOption();
		}
		clickOnBlockButton();
	}

}
