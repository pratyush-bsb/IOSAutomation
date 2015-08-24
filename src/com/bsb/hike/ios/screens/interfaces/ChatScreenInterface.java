package com.bsb.hike.ios.screens.interfaces;

import io.appium.java_client.MobileBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.screens.ForwardScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;


public interface ChatScreenInterface {

	By chatThreadScreenBackButton = MobileBy.name("All Conversations");
	By chatThreadHeader = MobileBy.IosUIAutomation(".navigationBars()[0].staticTexts()[0]"); //fetch text by name/value
	By chatThreadOverflowButton = MobileBy.name("chat navbar overflow menu");
	By chatbodyChatWithHeader = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].staticTexts()[0]"); //fetch text by name/value
	By stickerButton = MobileBy.name("chat bottombar sticker");
	String allChatMessages = ".tableViews()[0].cells()";
	String chatMessageText = ".staticTexts()[0]";
	String chatMessageTime = ".staticTexts()[1]";
	By chatMessageTypeBox = MobileBy.IosUIAutomation(".textViews()[0]"); //fetch by value
	By initialTypeBoxMessage = MobileBy.IosUIAutomation(".staticTexts()[0]"); //fetch by name
	By freeSmsTypeBoxMessage = MobileBy.IosUIAutomation(".staticTexts()[0]");

	By emojiButton = MobileBy.name("chat bottombar emoji");
	By attachmentButton = MobileBy.name("chat bottombar attachment");

	By themeButton = MobileBy.name("Theme");
	By moreButton = MobileBy.name("More");

	By copyMessage = MobileBy.name("Copy");
	By deleteMessage = MobileBy.name("Delete");
	By forwardMessage = MobileBy.name("Forward");
	By shareViaMessage = MobileBy.name("Share via");
	By emailButton = MobileBy.name("Email Chat");
	By clearChatButton = MobileBy.name("Clear Chat");
	By cancelButton = MobileBy.name("Cancel");

	//attachment options
	By cameraButton = MobileBy.name("Camera");
	By photosButton = MobileBy.name("Photos");
	By videoButton = MobileBy.name("Video");
	By audioButton = MobileBy.name("Audio");
	By locationButton = MobileBy.name("Location");
	By contactButton = MobileBy.name("Contact Info");

	By sendMessageButton = MobileBy.name("Send Message");
	By allStickersInCurrentTab = MobileBy.IosUIAutomation(".collectionViews()[0].cells()");
	By allStickersCategories = MobileBy.IosUIAutomation(".collectionViews()[1].cells()");
	
	By allEmojisInCurrentTab = MobileBy.IosUIAutomation(".collectionViews()[0].cells()");
	By allEmojisCategories = MobileBy.IosUIAutomation(".collectionViews()[1].cells()");
	By allowButton = MobileBy.name("Allow");
	By okButton = MobileBy.name("Ok");


	public void clickOverlaybutton();

	public void clickOnStickers();

	public void clickOnEmoji();

	public void clickOnAttachment();

	public ProfileScreenInterface clickOnProfile();

	public void clickOnTheme();

	public void clickOnMoreOption();

	public void clickOnEmailChatButton();

	public void clickOnClearChatButton();

	public void clickOnSendMessage();

	public void clickOnCancelButton();

	public HomeScreenMenu goBack();

	public void clickOnOverflowButton();

	public void typeInMessageBox (String textToType);

	public List<WebElement> allChatMessagesCells();

	public void sendMessage(String textToSend);

	public String getLastMessage();

	public void longPressOnLastMessage();

	public ForwardScreen clickOnForwardButton();

	public void clickOnCopyButton();

	public void clickOnDeleteButton();

	public void clearChat();

	public void deleteLastMessage();
	
	public String getCurrentTextInTypeBox();

	//this method sends a fixed sticker
	public void sendSticker();
	public void sendEmoji();
}