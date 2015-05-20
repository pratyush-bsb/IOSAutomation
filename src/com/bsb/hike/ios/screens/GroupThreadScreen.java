package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ChatScreenInterface;

public class GroupThreadScreen extends HikeLibrary implements ChatScreenInterface {

	protected String groupName;

	public GroupThreadScreen(String groupName) {
		this.groupName = groupName;
		waitForGroupThreadScreenToLoad();
	}

	private void waitForGroupThreadScreenToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(chatThreadHeader);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}

	}

	//protected variables
	protected String initialTypeBoxMessageString = "Group message...";
	protected By groupInfoButton = MobileBy.name("Group Info");
	protected By muteButton = MobileBy.name("Mute");
	protected By unmuteButton = MobileBy.name("Unmute");
	protected By mutedConversationImage = MobileBy.name("This conversation is muted.");

	public String getGroupName() {
		return groupName;
	}
	
	public By getMutedConversationImage() {
		return mutedConversationImage;
	}
	
	public By getInitialTypeBoxMessage() {
		return initialTypeBoxMessage;
	}
	
	public By getShareViaMessage() {
		return shareViaMessage;
	}

	public String getInitialTypeBoxMessageString() {
		return initialTypeBoxMessageString;
	}

	public By getGroupInfoButton() {
		return groupInfoButton;
	}
	
	public By getChatMessageTypeBox() {
		return chatMessageTypeBox;
	}

	public By getMuteButton() {
		return muteButton;
	}
	
	public By getUnmuteButton() {
		return unmuteButton;
	}

	public By getGroupChatHeader() {
		return chatThreadHeader;
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

	//public methods

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

	public GroupProfileScreen clickOnProfile() {
		clickOnElement(chatThreadOverflowButton);
		clickOnElement(groupInfoButton);
		return new GroupProfileScreen(this.groupName);
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

	public HomeScreenMenu goBack() {
		clickOnElement(chatThreadScreenBackButton);
		return new HomeScreenMenu();
	}

	public void clickOnOverflowButton() {
		clickOnElement(chatThreadOverflowButton);
	}

	public GroupProfileScreen clickOnGroupName() {
		clickOnElement(chatThreadHeader);
		return new GroupProfileScreen(groupName);
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
		By getLastMessage = MobileBy.IosUIAutomation(allChatMessages + "[" + String.valueOf(numberOfMessages-1) + "].staticTexts()[0]");
		String textCapturedLater = getTextByName(getLastMessage);
		return textCapturedLater;
	}
	
	public String getLastChatMessage() {

		int numberOfMessages = allChatMessagesCells().size();
		By getLastMessage = MobileBy.IosUIAutomation(allChatMessages + "[" + String.valueOf(numberOfMessages-1) + "].staticTexts()[1]");
		String textCapturedLater = getTextByName(getLastMessage);
		return textCapturedLater;
	}

	public void longPressOnLastMessage() {

		try {
			List<WebElement> allChatMessagesList = driver.findElementsByIosUIAutomation(allChatMessages);
			MobileElement lastMessage = (MobileElement) allChatMessagesList.get(allChatMessagesList.size() - 1);
			new TouchAction(driver).longPress(lastMessage).perform();

		} catch (Exception e) {

		}
	}

	public StartANewChatScreen clickOnForwardButton() {

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

	@Override
	public void sendEmoji() {

		clickOnEmoji();

		try {
			WebElement firstEmoji = driver.findElements(allEmojisInCurrentTab).get(0);
			driver.tap(1, firstEmoji, 1);
		} catch(Exception e) {
			Reporter.log("Unable to fetch emojis in current tab");
		}
	}
	
	public String getCurrentTextInTypeBox() {
		String text = getTextByValue(chatMessageTypeBox);
		return text;
	}

	public void clearChatCancel() {
		clickOnOverflowButton();
		clickOnMoreOption();
		clickOnClearChatButton();
		clickOnCancelButton();
	}
	
	public void muteGroup() {
		clickOnOverflowButton();
		clickOnMuteButton();
	}

	private void clickOnMuteButton() {
		clickOnElement(muteButton);
	}

	public void unmuteGroup() {
		clickOnElement(unmuteButton);
	}

}
