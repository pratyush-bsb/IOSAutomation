package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ChatScreenInterface;
import com.bsb.hike.ios.screens.interfaces.ContactSelectionInterface;

public class ForwardScreen extends HikeLibrary implements ContactSelectionInterface {

	public ForwardScreen() {
		waitForForwardScreenToLoad();
	}

	private void waitForForwardScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(forwardScreenHeader);
				pageLoaded = true;
			} catch(Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) {}
			}
		}
	}

	//protected identifiers

	protected By forwardScreenHeader = MobileBy.name("Forward");
	protected By cancelTyping = MobileBy.name("Cancel");
	protected By noResultsBy = MobileBy.name("No Results");
	protected By backButton = MobileBy.name("Back");
	protected By searchOrEnterNumber = MobileBy.IosUIAutomation(".searchBars()[0]");
	protected By contactsTab = MobileBy.name("Contacts");
	protected By chatsTab = MobileBy.name("Chats");
	protected By groupsTab = MobileBy.name("GROUPS");
	protected By recentsTab = MobileBy.name("RECENTS");
	protected By forwardActionBarHeader = MobileBy.IosUIAutomation("UIATarget.localTarget().frontMostApp().actionSheet().scrollViews()[0].staticTexts()[1]");
	protected By cancelEditingMessageButton = MobileBy.name("cancel edit message");
	protected By emojiIconInEditScreen = MobileBy.name("edit message emoji icon");
	protected By forwardLinkActionBarHeader = MobileBy.IosUIAutomation("Forward Link");

	public By getForwardScreenHeader() {
		return forwardScreenHeader;
	}
	
	public By getForwardLinkActionBarHeader() {
		return forwardLinkActionBarHeader;
	}
	
	public By getEmojiIconInEditScreen() {
		return emojiIconInEditScreen;
	}
	
	public By getCancelEditingMessageButton() {
		return cancelEditingMessageButton;
	}
	
	public By getForwardActionBarHeader() {
		return forwardActionBarHeader;
	}
	
	public By getChatsTab() {
		return chatsTab;
	}
	
	public String getAllContactsPrefix() {
		return allContactsPrefix;
	}

	public String getContactNameSuffix() {
		return contactNameSuffix;
	}
	
	public By getNoResultsBy() {
		return noResultsBy;
	}

	public By getCancelTyping() {
		return cancelTyping;
	}

	public By getBackButton() {
		return backButton;
	}

	public By getSearchOrEnterNumber() {
		return searchOrEnterNumber;
	}

	public By getContactsTab() {
		return contactsTab;
	}

	public By getPeopleOnHikeTab() {
		return peopleOnHikeTab;
	}

	public By getSmsContactsTab() {
		return smsContactsTab;
	}
	
	public By getForwardButton() {
		return forwardButton;
	}
	
	public By getGroupsTab() {
		return groupsTab;
	}
	
	public By getRecentsTab() {
		return recentsTab;
	}
	
	public By getEditAndForwardButton() {
		return editAndForwardButton;
	}


	//public methods

	public void clickOnSearchTab() {
		clickOnElement(searchOrEnterNumber);
	}

	public By getEditForwardingMessageWindow() {
		return editForwardingMessageWindow;
	}

	public void clickOnForwardMessage() {
		clickOnElement(forwardButton);
	}
	
	public ChatScreenInterface clickOnForwardButtonInActionBar(String user, boolean group) {
		
		ChatScreenInterface chatScreenObj = null;
		
		clickOnElement(forwardButton);
		if(group) {
			chatScreenObj = new GroupThreadScreen(user);
		} else {
			chatScreenObj = new ChatThreadScreen(user);
		}
		
		return chatScreenObj;
		
	}

	
	//gets counts of contacts under different tabs and logs them in reports.
	public void getCountOfContacts() {

		int numberOfTabsAvailable = driver.findElements(allTabs).size();
		int counter = 0;
		if(numberOfTabsAvailable > 2) {
			Reporter.log("Favorites tab available.");
			By favoritesCount = MobileBy.IosUIAutomation(contactsCountPrefix + counter + contactsCountSuffix);
			counter++;
			Reporter.log("Count of Favorites : " + getTextByName(favoritesCount));
		} else {
			Reporter.log("Favorites tab not available.");
		}

		By hikeContacts = MobileBy.IosUIAutomation(contactsCountPrefix + counter + contactsCountSuffix);
		counter++;
		Reporter.log("Count of hike contacts : " + getTextByName(hikeContacts));

		By nonHikeContacts = MobileBy.IosUIAutomation(contactsCountPrefix + counter + nonHikeContactsCountSuffix);
		counter++;
		Reporter.log("Count of non hike contacts : " + getTextByName(nonHikeContacts));

	}

	
	//returns count of contacts under the recents tab
	public int getCountOfRecentChats() {

		int countOfRecents = 0;
		//check if groups tab is also there
		try {
			List<WebElement> allTabsElements = driver.findElements(allTabs);
			if(allTabsElements.size() > 1) {
				//groups tab is there. get recent tabs count
				WebElement countField = allTabsElements.get(1).findElement(MobileBy.IosUIAutomation(contactNumberSuffix));
				countOfRecents = Integer.parseInt(countField.getAttribute("name"));
			}
		} catch(Exception e) {}
		return countOfRecents;
	}
	
	//clicks on 'cancel' in the action bar which comes as popup
	public void cancelForwardingActionMenuBar() {
		
		try {
			WebElement cancelElement = driver.findElement(cancelTyping);
			new TouchAction(driver).press(cancelElement).perform();
		} catch(Exception e){}
	}

	
	//returns the most recent contact in forward screen under 'recent' tab
	public String getMostRecentContactInForwardScreen() {

		String mostRecentChat = "";

		try {
			int countOfAllChats = driver.findElementsByIosUIAutomation(allContactsPrefix).size();
			int countOfRecentChats = getCountOfRecentChats();
			mostRecentChat = getTextByName(MobileBy.IosUIAutomation(allContactsPrefix + "[" + (countOfAllChats - countOfRecentChats) + "]"));

		} catch (Exception e) {}
		return mostRecentChat;
	}
	
	
	//returns the first group listed under 'groups' tab in forward screen
	public String getFirstGroupnameInForwardScreen() {

		String firstGroupName = "";

		try {
			firstGroupName = getTextByName(MobileBy.IosUIAutomation(allContactsPrefix + "[0]"));
		} catch(Exception e) {}

		return firstGroupName;
	}

	
	//checks if all headers are present in forward screen
	public void headersWhenForwardingMessage() {

		try {
			//clickOnElement(contactsTab);
			//if group chats available
			boolean groupChats = isElementPresent(groupsTab);
			List<WebElement> allTabs = driver.findElements(MobileBy.IosUIAutomation(allChildOfTableView));
			List<WebElement> allContacts = driver.findElements(MobileBy.IosUIAutomation(allContactsPrefix));
			int contactsSize = allContacts.size();
			int countOfRecentsListed = 0;
			if(groupChats) {
				
				int countOfGroups = getCountOfGroups();
				//list all groups and their numbers
				boolean groupContinued = true;
				int counter = 0;
				int countOfGroupsInList = 0;
				while (groupContinued && counter < allTabs.size()) {
					try {
						String validGroup = allTabs.get(counter).getAttribute("name");
						if (validGroup.contains("GROUPS")) {
							//this is the start of the group
							groupContinued = true;
							counter++;
							continue;
						} else if(!validGroup.equalsIgnoreCase("RECENTS")) {
							groupContinued = true;
							counter++;
							countOfGroupsInList++;
						} else if (validGroup.equalsIgnoreCase("RECENTS")) {
							groupContinued = false;
						}
					} catch (Exception e) { groupContinued = false; }
				}
				countOfRecentsListed = contactsSize - countOfGroupsInList;
				Assert.assertTrue((countOfGroups == countOfGroupsInList), "The count mentioned in 'GROUPS' header is not the same as number of groups listed");
			}
			
			int countOfRecentsInTab = getCountOfRecentChats();
			Assert.assertTrue((countOfRecentsInTab == countOfRecentsListed), "The count of recents listed is not equal to count of recents mentioned in tab");
			
			clickOnElement(contactsTab);
			if(isElementPresent(favoritesTab)) {
				WebElement favoriteCounts = driver.findElements(favoritesTab).get(0).findElement(MobileBy.IosUIAutomation(contactNumberSuffix));
				Reporter.log("Count of Favorites : " + favoriteCounts.getAttribute("name"));
			}

			WebElement peopleOnHikeCounts = driver.findElements(peopleOnHikeTab).get(0).findElement(MobileBy.IosUIAutomation(contactNumberSuffix));
			Reporter.log("Count of People on Hike : " + peopleOnHikeCounts.getAttribute("name"));

			//TODO fix this. 'SMS CONTACTS' takes the text instead of the tableGroup.
			WebElement peopleOnSMSCounts = driver.findElements(smsContactsTab).get(0).findElement(MobileBy.IosUIAutomation(contactNumberSuffix));
			Reporter.log("Count of People on SMS : " + peopleOnSMSCounts.getAttribute("name"));
		} catch (Exception e) {}
	}
	
	public int getCountOfGroups() {
		
		int countOfGroups = 0;
		//check if groups tab is also there
		try {
			List<WebElement> allTabsElements = driver.findElements(allTabs);
			if(allTabsElements.get(0).getAttribute("name").equalsIgnoreCase("GROUPS")) {
				//groups tab is there. get recent tabs count
				WebElement countField = allTabsElements.get(0).findElement(MobileBy.IosUIAutomation(contactNumberSuffix));
				countOfGroups = Integer.parseInt(countField.getAttribute("name"));
			}
		} catch(Exception e) {}
		return countOfGroups;
	}

	
	//taps on contact to forward message to. returns the object of the selected contact's chat thread
	public ChatThreadScreen selectContactToForwardMessage() {
		clickOnElement(contactsTab);
		clickOnSearchTab();
		enterText(searchOrEnterNumber, HIKE_CONTACT_NAME_3);
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(HIKE_CONTACT_NAME_3)) {
					new TouchAction(driver).press(eachContact).perform();
					clickOnElement(forwardButton);
				}
			}
		} catch (Exception e) {

		}

		return new ChatThreadScreen(HIKE_CONTACT_NAME_3);
	}

	
	//selects the 'edit and forward' message option. Should open the editing box
	public void selectContactToEditandForwardMessage() {
		clickOnElement(contactsTab);
		clickOnSearchTab();
		enterTextWithClear(searchOrEnterNumber, HIKE_CONTACT_NAME_3);
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(HIKE_CONTACT_NAME_3)) {
					new TouchAction(driver).press(eachContact).perform();
					clickOnElement(editAndForwardButton);
					//delete some text and check
					By deleteBy = MobileBy.name("Delete");
					WebElement deleteButton = driver.findElement(deleteBy);
					for(int i=0; i < 7; i++) {
						deleteButton.click();
					}
				}
			}
		} catch (Exception e) {

		}

	}
	
	//deletes full text in edit screen
	public void deleteFullText() {
		
		String populatedText = getTextByValue(editForwardingMessageWindow);
		try {
			By deleteBy = MobileBy.name("Delete");
			WebElement deleteButton = driver.findElement(deleteBy);
			for(int i = 0; i < populatedText.length(); i++) {
				deleteButton.click();
			}
		} catch(Exception e) {}
	}

	//types in the edit box of forward message screen
	public void typeInMessageBox(String textToType) {

		enterText(editForwardingMessageWindow, textToType);
	}
	
	
	//performs a touch action 'tap' on the cancel button.
	public void cancelTyping() {

		try {
			WebElement cancelElement = driver.findElement(cancelTyping);
			new TouchAction(driver).press(cancelElement).perform();
		} catch(Exception e) {}
	}
	
	public WebElement getcurrentScreenElement() {
		
		WebElement element = null;
		try {
			element = driver.findElement(contactsOnFront);
		} catch(Exception e) {}
		
		return element;
	}
	
	//cancels forwarding of message. 
	public void cancelForwarding() {
		
		try {
			WebElement cancelElement = driver.findElement(cancelTyping);
			new TouchAction(driver).press(cancelElement).perform();
			new TouchAction(driver).press(cancelElement).perform();
		} catch(Exception e) {}
	}
	
	public ChatScreenInterface forwardMessageToContact(String user, boolean group) {
		
		ChatScreenInterface chatScreenObj = null;
		clickOnElement(searchOrEnterNumber);
		enterText(searchOrEnterNumber, user);
		try {
			List<WebElement> allContactsFound = driver.findElements(MobileBy.IosUIAutomation(allContactsPrefix));
			for (WebElement eachContact : allContactsFound) {
				String userCaptured = eachContact.getAttribute("name");
				if(userCaptured.equalsIgnoreCase(user)) {
					new TouchAction(driver).press(eachContact).perform();
					break;
				}
			}
			chatScreenObj = clickOnForwardButtonInActionBar(user, group);
		} catch(Exception e) {}
	
		return chatScreenObj;
	}

}
