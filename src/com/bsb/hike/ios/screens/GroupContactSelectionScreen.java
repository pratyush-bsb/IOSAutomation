package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.interfaces.ContactSelectionInterface;

public class GroupContactSelectionScreen extends HikeLibrary implements ContactSelectionInterface {

	public GroupContactSelectionScreen() {
		waitForContactSelectionScreenToLoad();
	}

	private void waitForContactSelectionScreenToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(getSearchBar());
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	//protected identifiers
	protected By backButton = MobileBy.name("New Group");
	protected By screenHeader = MobileBy.name("Select Contacts");
	protected String screenHeaderString = "Select Contacts";
	protected By doneButton = MobileBy.name("Done");
	protected By searchBar = MobileBy.IosUIAutomation(".scrollViews()[0].textFields()[0]"); //get text by value

	

	//public getters

	public By getBackButton() {
		return backButton;
	}

	public By getScreenHeader() {
		return screenHeader;
	}

	public String getScreenHeaderString() {
		return screenHeaderString;
	}

	public By getDoneButton() {
		return doneButton;
	}

	public By getSearchBar() {
		return searchBar;
	}

	public String getSearchOrEnterNumberString() {
		return searchOrEnterNumberString;
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

	public String getContactsCountPrefix() {
		return contactsCountPrefix;
	}

	public String getContactsCountSuffix() {
		return contactsCountSuffix;
	}

	public String getNonHikeContactsCountSuffix() {
		return nonHikeContactsCountSuffix;
	}

	//public methods
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

	public void searchForAContact(String contactName) {

		clickOnElement(searchBar);
		enterTextWithClear(searchBar, contactName);

	}
	
	public void searchForAContactWithoutClear(String contactName) {
		clickOnElement(searchBar);
		enterText(searchBar, contactName);
	}

	public int getTotalCountOfContacts() {

		int totalContacts = 0;

		try {
			List<WebElement> allContacts = driver.findElements(MobileBy.IosUIAutomation(allContactsPrefix));
			totalContacts = allContacts.size();
		} catch (Exception e) {
			totalContacts = 0;
		}
		return totalContacts;
	}

	public void selectFirstContactInResults() {
		
		try {
			WebElement contact = driver.findElement(MobileBy.IosUIAutomation(firstContact));
			new TouchAction(driver).press(contact).perform();
		} catch (Exception e) {
			Reporter.log("No contact found after searching");
		}
	}

	public GroupThreadScreen clickOnDoneButton(String groupName) {
		clickOnElement(doneButton);
		return new GroupThreadScreen(groupName);
	}
	
	public GroupProfileScreen addNewMember(String user, String groupName) {

		searchForAContact(user);
		try {
			List<WebElement> allResults = driver.findElementsByIosUIAutomation(allContactsPrefix);

			//iterate over all results and pick the one matching the search criteria
			for(WebElement eachContact : allResults) {
				String contactName = eachContact.findElement(MobileBy.IosUIAutomation(contactNameSuffix)).getAttribute("name");
				if(contactName.equalsIgnoreCase(user)) {
					new TouchAction(driver).press(eachContact).perform();
					break;
				}
			}
		} catch (Exception e) {

		}
		clickOnElement(doneButton);
		return new GroupProfileScreen(groupName);
	}
}
