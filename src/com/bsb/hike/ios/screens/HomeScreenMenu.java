package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.HiddenModeFTUEPopUp;
import com.bsb.hike.ios.screens.interfaces.ChatScreenInterface;

public class HomeScreenMenu extends HikeLibrary {
	
	public HomeScreenMenu() {
		waitForHomeScreenToLoad();
	}

	private void waitForHomeScreenToLoad() {
		
		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 20) {
			try {
				driver.findElement(conversationHide);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	protected By overflow_Option = MobileBy.name("conversation overflow");
	protected By newGroup_Lbl = MobileBy.name("New Group");
	protected By favorites_Lbl = MobileBy.name("Favorites");
	protected By rewards_Lbl = MobileBy.name("Rewards");
	protected By inviteFriends_Lbl = MobileBy.name("Invite Friends");
	protected By profile_Lbl = MobileBy.name("Profile");
	protected By settings_Lbl = MobileBy.name("Settings");
	protected By status_Lbl = MobileBy.name("Status");
	protected By conversationTimeline = MobileBy.name("conversation timeline");
	protected By conversationCompose = MobileBy.name("conversation compose");
	protected By conversationHide = MobileBy.name("conversation hi");
	protected By allExistingChats = MobileBy.IosUIAutomation(".tableViews()[0].cells()");
	protected String allExistingChatsString = ".tableViews()[0].cells()";
	protected By firstExistingChatName = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].staticTexts()[1]");

	protected String deleteChatButton = "Delete";
	protected String hideButton = "Hide";
	protected String unhideButton = "Unhide";
	protected By moreChatButton = MobileBy.name("More");
	protected By moreProfileButton = MobileBy.name("Profile");
	protected By moreEmailChatButton = MobileBy.name("Email Chat");
	protected By moreClearChatButton = MobileBy.name("Clear Chat");
	protected By moreCancelButton = MobileBy.name("Cancel");
	protected By deleteChatConfirmButton = MobileBy.name("Delete Chat");
	
	protected String lastMessageInView = ".staticTexts()[0]";
	protected String userName = ".staticTexts()[1]";
	protected String timestamp = ".staticTexts()[2]";
	protected String unreadMessageCounter = ".staticTexts()[3]";
	protected By removeHiddenChatHintButton = MobileBy.name("ic cross tip hiddenmode");
	
	protected By leaveGroupButton = MobileBy.name("Leave Group");
	protected By deleteChat = MobileBy.name("Delete");
	protected By moreButton = MobileBy.name("More");
	
	protected By hiddenModeSetupConfirmation = MobileBy.name("Hidden mode setup is complete. Swipe chats left to hide.");
	protected By popupHomeScreenText = MobileBy.IosUIAutomation(".staticTexts()[0]");

	//getters
	public By getOverflow_Option() {
		return overflow_Option;
	}
	
	public By getPopupHomeScreenText() {
		return popupHomeScreenText;
	}
	
	public By getHiddenModeSetupConfirmation() {
		return hiddenModeSetupConfirmation;
	}

	public By getHiddenChatHintButton() {
		return removeHiddenChatHintButton;
	}
	
	public By getDeleteChatConfirmButton() {
		return deleteChatConfirmButton;
	}

	public By getMoreProfileButton() {
		return moreProfileButton;
	}

	public By getMoreEmailChatButton() {
		return moreEmailChatButton;
	}

	public By getMoreClearChatButton() {
		return moreClearChatButton;
	}

	public By getMoreCancelButton() {
		return moreCancelButton;
	}

	public String getDeleteChatButton() {
		return deleteChatButton;
	}

	public By getMoreChatButton() {
		return moreChatButton;
	}

	public By getAllExistingChats() {
		return allExistingChats;
	}

	public By getFirstExistingChatName() {
		return firstExistingChatName;
	}

	public By getNewGroup_Lbl() {
		return newGroup_Lbl;
	}

	public By getFavorites_Lbl() {
		return favorites_Lbl;
	}

	public By getRewards_Lbl() {
		return rewards_Lbl;
	}

	public By getInviteFriends_Lbl() {
		return inviteFriends_Lbl;
	}

	public By getProfile_Lbl() {
		return profile_Lbl;
	}

	public By getSettings_Lbl() {
		return settings_Lbl;
	}

	public By getStatus_Lbl() {
		return status_Lbl;
	}

	public By getConversationTimeline() {
		return conversationTimeline;
	}


	public By getConversationHide() {
		return conversationHide;
	}

	public By getConversationCompose() {
		return conversationCompose;
	}


	//public methods
	public void clickOnOverflow(){
		clickOnElement(overflow_Option);
	}

	public NewGroupScreen clickOnNewGroup_Lbl()
	{
		clickOnOverflow();
		clickOnElement(newGroup_Lbl);
		return new NewGroupScreen();
	}
	
	public HiddenModeFTUEPopUp clickOnHideButton(boolean firstSetup) {
		
		HiddenModeFTUEPopUp hiddenFtueObj = null;
		clickOnElement(conversationHide);
		if(firstSetup) {
			hiddenFtueObj = new HiddenModeFTUEPopUp();
		} else { //TODO add contructor and interface for enter passcode screens
			
			//return new EnterPasscodeForStealth();
		}
		
		return hiddenFtueObj;
	}

	public ChatThreadScreen clickOnFirstChat() {
		String userName = getTextByName(firstExistingChatName);
		clickOnElement(firstExistingChatName);
		return new ChatThreadScreen(userName);
	}

	public FavoritesScreen clickOnFavorites_Lbl()
	{
		clickOnElement(favorites_Lbl);
		return new FavoritesScreen();
	}

	public RewardsScreen clickOnRewards_Lbl()
	{
		clickOnOverflow();
		clickOnElement(rewards_Lbl);
		return new RewardsScreen();
	}

	public void clickOnInviteFriends_Lbl()
	{
		clickOnElement(inviteFriends_Lbl);
	}

	public MyProfileScreen clickOnProfile_Lbl()
	{
		clickOnElement(profile_Lbl);
		return new MyProfileScreen();
	}

	public SettingsScreen clickOnSettings_Lbl()
	{
		clickOnOverflow();
		clickOnElement(settings_Lbl);
		return new SettingsScreen();
	}

	public void clickOnStatus_Lbl()
	{
		clickOnElement(status_Lbl);
	}

	public StartANewChatScreen clickOnComposeConversation() {
		clickOnElement(conversationCompose);
		return new StartANewChatScreen();
	}

	public void deleteAllExistingChats() {

		int counter = 0;

		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);

			//iterate over all chats, swipe left and delete
			for(WebElement eachChat : allChats) {
				MobileElement eachChatCasted = (MobileElement) eachChat;
				eachChatCasted.swipe(SwipeElementDirection.LEFT, 1000);
				clickOnDeleteChatButton(eachChatCasted, counter);
				clickOnElement(deleteChatConfirmButton);
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clickOnDeleteChatButton(MobileElement chatToDelete, int counter) {

		boolean buttonFound = false;

		while(!buttonFound) {
			try {
				By deleteButtonBy = MobileBy.IosUIAutomation(allExistingChatsString + "[0]" + deleteChatButton);
				WebElement deleteButton = chatToDelete.findElement(deleteButtonBy);
				deleteButton.click();
				if(isElementPresent(leaveGroupButton)) {
					clickOnElement(leaveGroupButton);
				} else {
					clickOnElement(deleteChat);
				}
				buttonFound = true;
			} catch(Exception e) {
				buttonFound = false;
				chatToDelete = (MobileElement) driver.findElements(allExistingChats).get(0);
			}
		}
	}

	//return message that is in preview in the chat bar in home screen
	public String getLastMessageInView(String user) {
		
		String lastMessage = "";

		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			int counter = allChats.size();
			
			//iterate over all chats and get the chat with user passed as param
			for(int i = 0; i < counter; i++) {
				String chatUserCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + i + "]" + userName));
				if(chatUserCaptured.equalsIgnoreCase(user)) {
					//fetch last message in view
					lastMessage = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + i + "]" + lastMessageInView));
					break;
				}
			}
		} catch(Exception e) {
		}
		
		return lastMessage;
	}
	
	//navigates to the chat thread of the particular user, if the chat thread exists
	public ChatScreenInterface goToSpecificUserThread(String user, boolean group) {
		
		ChatScreenInterface chatScreenObj = null;
		int counter = 0;
		
		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			
			//iterate over all chats and click on user thread
			for(WebElement eachChat : allChats) {
				String userCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + counter + "]" + userName));
				if(userCaptured.equalsIgnoreCase(user)) {
					//user captured. click on user and return ChatThreadScreen instance
					eachChat.click();
					if(group) {
						chatScreenObj = new GroupThreadScreen(user);
					} else {
						chatScreenObj = new ChatThreadScreen(user);
					}
					break;
				}
				counter++;
			}
		} catch (Exception e) {
			counter++;
		}
		return chatScreenObj;
	}

	public ChatThreadScreen clickOnNatashaBot() {
		
		int counter = 0;
		ChatThreadScreen chatScreenObj = null;
		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			
			//iterate over all chats and click on user thread
			for(WebElement eachChat : allChats) {
				String userCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + counter + "]" + userName));
				if(userCaptured.equalsIgnoreCase(HIKE_BOT_NATASHA)) {
					//user captured. click on user and return ChatThreadScreen instance
					eachChat.click();
					chatScreenObj = new ChatThreadScreen(HIKE_BOT_NATASHA);
					break;
				}
				counter++;
			}
		} catch(Exception e) {
			//Reporter.log("Not able to click on natasha bot");
			counter++;
		}
		return chatScreenObj;
	}
	
	public void removeHiddenChatHintButton() {
		try {
			Thread.sleep(2000);
			if(isElementPresent(removeHiddenChatHintButton)) {
				clickOnElement(removeHiddenChatHintButton);
			}
		} catch(Exception e){}
	}
	
	public WebElement getSpecificChatElement(String user) {
		WebElement userChat = null;
		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			int allChatsSize = allChats.size();
			for(int i = 0; i < allChatsSize; i++) {
				String userCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + i + "]" + userName));
				if(user.equalsIgnoreCase(userCaptured)) {
					userChat = allChats.get(i);
					break;
				}
			}
		} catch(Exception e) {}
		return userChat;
	}
	
	public int getUnreadMessageCounter(String user) {
		int counter = 0;
		
		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			int allChatsSize = allChats.size();
			
			for(int i = 0; i < allChatsSize; i++) {
				By counterBy = MobileBy.IosUIAutomation(unreadMessageCounter);
				String userCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + i + "]" + userName));
				if(user.equalsIgnoreCase(userCaptured)) {
					counter = Integer.parseInt(allChats.get(i).findElement(counterBy).getAttribute("name"));
					break;
				}
			}
		} catch(Exception e){counter = 0;}
		return counter;
	}
	
	public boolean checkTimestampAvailability(String user) {
		
		boolean timestampAvailable = false;
		
		try {
			List<WebElement> allChats = driver.findElements(allExistingChats);
			int allChatsSize = allChats.size();
			
			for(int i = 0; i < allChatsSize; i++) {
				By timestampBy = MobileBy.IosUIAutomation(timestamp);
				String userCaptured = getTextByName(MobileBy.IosUIAutomation(allExistingChatsString + "[" + i + "]" + userName));
				if(user.equalsIgnoreCase(userCaptured)) {
					timestampAvailable = allChats.get(i).findElement(timestampBy).getAttribute("name").length() > 0;
					break;
				}
			}
		} catch(Exception e) {}
		return timestampAvailable;
	}
	
	public WebElement swipeLeftParticularUser(String user) {
		
		WebElement userElement = getSpecificChatElement(user);
		try {
			MobileElement userElementCasted = (MobileElement) userElement;
			
			userElementCasted.swipe(SwipeElementDirection.LEFT, 1000);
		} catch(Exception e) {}
		
		return userElement;
	}
	
	public void toggleHideUnhideChat(String user) {
		
		WebElement userElement = swipeLeftParticularUser(user);
		try {
			MobileElement userElementCasted = (MobileElement) userElement;
			try {
				driver.findElement(MobileBy.name(hideButton));
				if(userElementCasted.findElement(MobileBy.name(hideButton)) != null) {
					WebElement hideButtonElem = userElementCasted.findElement(MobileBy.name(hideButton));
					hideButtonElem.click();
				}
			} catch(Exception e){}
			try {
				if(userElementCasted.findElement(MobileBy.name(unhideButton)) != null) {
					WebElement unhideButtonElem = userElementCasted.findElement(MobileBy.name(unhideButton));
					unhideButtonElem.click();
				}
			} catch(Exception e) {}
			
		} catch(Exception e) {}
	}
	
	public void leaveGroup(String groupName) {
		
		WebElement userElement = swipeLeftParticularUser(groupName);
		WebElement leaveButtonElement = null;
		WebElement moreButtonElement = null;
		
		try {
			leaveButtonElement = userElement.findElement(leaveGroupButton);
		} catch (Exception e) {
			moreButtonElement = userElement.findElement(moreButton);
		}
		
		try {
			if(leaveButtonElement != null) {
				leaveButtonElement.click();
			} else if(moreButtonElement != null) {
				moreButtonElement.click();
				clickOnElement(leaveGroupButton);
			}
		} catch (Exception e) {}
		
		
	}
}
