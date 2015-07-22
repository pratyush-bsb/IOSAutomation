package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ContactSelectionInterface;

public class StartANewChatScreen extends HikeLibrary implements ContactSelectionInterface {

	//protected variables
	protected By startAChatBackButton = MobileBy.name("All Conversations");
	protected By startAChatHeader = MobileBy.name("Start a Chat");
	protected String startAChatHeaderString = "Start a Chat";
	protected By searchOrEnterNumber = MobileBy.IosUIAutomation(".searchBars()[0]");

	/*protected By favoriteContactsCount = MobileBy.IosUIAutomation(".tableViews()[0].groups()[0].staticTexts()[1]");
	protected By peopleOnHikeContactsCount = MobileBy.IosUIAutomation(".tableViews()[0].groups()[1].staticTexts()[1]");
	protected By smsContactsCount = MobileBy.IosUIAutomation(".tableViews()[0].groups()[2].staticTexts()[1]");
	 */

	protected By cancelTyping = MobileBy.name("Cancel");
	protected By noResultsBy = MobileBy.name("No Results");
	protected String noResultsString = "No Results";

	//getters
	public String getStartAChatHeaderString() {
		return startAChatHeaderString;
	}

	public By getStartAChatBackButton() {
		return startAChatBackButton;
	}

	public By getStartAChatHeader() {
		return startAChatHeader;
	}

	public By getSearchOrEnterNumber() {
		return searchOrEnterNumber;
	}

	public By getFavoritesTab() {
		return favoritesTab;
	}

	public By getPeopleOnHikeTab() {
		return peopleOnHikeTab;
	}

	public By getSmsContactsTab() {
		return smsContactsTab;
	}

	public By getAllTabs() {
		return allTabs;
	}

	public By getCancelTyping() {
		return cancelTyping;
	}

	public By getForwardButton() {
		return forwardButton;
	}

	public String getSearchOrEnterNumberString() {
		return searchOrEnterNumberString;
	}

	public String getContactsCountPrefix() {
		return contactsCountPrefix;
	}

	public String getContactsCountSuffix() {
		return contactsCountSuffix;
	}

	public String getAllContactsPrefix() {
		return allContactsPrefix;
	}

	public String getFirstContact() {
		return firstContact;
	}

	public String getContactNameSuffix() {
		return contactNameSuffix;
	}

	public String getContactNumberSuffix() {
		return contactNumberSuffix;
	}

	public String getNoResultsString() {
		return noResultsString;
	}

	public By getNoResultsBy() {
		return noResultsBy;
	}

	//public methods
	public void clickOnSearchTab() {
		clickOnElement(searchOrEnterNumber);
	}

	public void clickOnBackButton() {
		clickOnElement(startAChatBackButton);
	}

	public void cancelTypingForContact() {
		//clickOnElement(cancelTyping);
		driver.hideKeyboard();
	}

	public void cancelTyping() {

		try {
			WebElement cancelElement = driver.findElement(cancelTyping);
			new TouchAction(driver).press(cancelElement).perform();
		} catch(Exception e) {}
	}

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

	public ChatThreadScreen clickOnFirstContact() {

		String userName = "";
		By userNameBy = MobileBy.IosUIAutomation(firstContact + contactNameSuffix);
		userName = getTextByName(userNameBy);
		clickOnElement(MobileBy.IosUIAutomation(firstContact));
		return new ChatThreadScreen(userName);

	}

	//taps on the number found after typing.
	public ChatThreadScreen selectTypedUser(String userNumber) {

		ChatThreadScreen chatThreadObj = null;
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(userNumber)) {
					new TouchAction(driver).press(eachContact).perform();
					chatThreadObj = new ChatThreadScreen(userNumber);
				}
			}
		} catch (Exception e) {

		}

		if(chatThreadObj == null) {
			Assert.fail("Name/number did not match with the one searched with");
		}

		//Assert.assertEquals(userNumber.toLowerCase(), getTextByName(MobileBy.IosUIAutomation(firstContact + contactNameSuffix)).toLowerCase(), "Name/number did not match with the one searched with");
		//ChatThreadScreen chatThreadObj = clickOnFirstContact();
		return chatThreadObj;
	}

	//searches and taps on contact and check if apt chat thread screen came up
	public void assertSelectedUserValidity(String user, String typeToAssert) {
		String toAssert = "";


		clickOnSearchTab();
		enterText(getSearchOrEnterNumber(), user);
		ChatThreadScreen chatThreadScreenObj = selectTypedUser(user);
		if(typeToAssert.equalsIgnoreCase("free")) {
			toAssert = chatThreadScreenObj.getFreeSmsTypeBoxString();
		} else {
			toAssert = chatThreadScreenObj.getInitialTypeBoxMessageString();
		}
		Assert.assertEquals(chatThreadScreenObj.getUserName(), getTextByName(chatThreadScreenObj.getChatThreadHeader()), "Chat did not start with the selected user");
		Assert.assertEquals(getTextByName(chatThreadScreenObj.getFreeSmsTypeBoxMessage()), toAssert, "Initial message does not match with 'Free SMS...'");

	}

	public ChatThreadScreen searchContactByNameAndStartChat(String user) {
		clickOnSearchTab();
		enterText(getSearchOrEnterNumber(), user);
		return clickOnFirstContact();
	}

	//starts a new chat with sms contact
	//return ChatThreadScreen object
	public ChatThreadScreen startNewSmsChat() {

		clickOnSearchTab();
		enterText(getSearchOrEnterNumber(), HIKE_SMS_CONTACT_NAME_1);
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(HIKE_SMS_CONTACT_NAME_1)) {
					new TouchAction(driver).press(eachContact).perform();
				}
			}
		} catch (Exception e) {

		}

		return new ChatThreadScreen(HIKE_SMS_CONTACT_NAME_1);
	}

	public ChatThreadScreen startNewHikeChat(String user) {

		clickOnSearchTab();
		enterText(getSearchOrEnterNumber(), user);
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(user)) {
					new TouchAction(driver).press(eachContact).perform();
				}
			}
		} catch (Exception e) {

		}

		return new ChatThreadScreen(user);
	}

}
