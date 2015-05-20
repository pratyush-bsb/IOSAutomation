package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class BlockedList extends HikeLibrary {

	public BlockedList() {
		waitForBlockedPageToLoad();
	}

	private void waitForBlockedPageToLoad() {

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(header);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}


	protected By header = MobileBy.name("Blocked List");
	protected String headerString = "Blocked List";
	protected By searchForNumberOrName = MobileBy.IosUIAutomation(".searchBars()[0]");
	protected By backButton = MobileBy.name("Back");
	protected By stopButton = MobileBy.name("Stop");
	protected By doneButton = MobileBy.name("Done");
	protected By allUsersFound = MobileBy.IosUIAutomation(".tableViews()[0].cells()");
	protected By firstContactFound = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0]");
	protected String contactBlockStatus = "buttons()[0]";
	protected By addContactToBlock = MobileBy.name("blockedPlusIcon");
	protected By contactNameSuffix = MobileBy.IosUIAutomation(".staticTexts()[0]");

	protected By someoneTroublingYouDiv = MobileBy.name("Someone troublin' you?  Tap on + to block them");
	
	//public getters
	public By getHeader() {
		return header;
	}
	
	public By getStopButton() {
		return stopButton;
	}
	
	public String getHeaderString() {
		return headerString;
	}

	public By getSearchForNumberOrName() {
		return searchForNumberOrName;
	}

	public By getBackButton() {
		return backButton;
	}

	public By getDoneButton() {
		return doneButton;
	}

	public By getAllUsersFound() {
		return allUsersFound;
	}

	public By getFirstContactFound() {
		return firstContactFound;
	}

	public By getAddContactToBlock() {
		return addContactToBlock;
	}

	public By getContactNameSuffix() {
		return contactNameSuffix;
	}

	public void clickOnAddContactToBlock() {
		clickOnElement(addContactToBlock);

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(doneButton);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	public void clickOnSearchBar() {
		clickOnElement(searchForNumberOrName);
	}

	public void clickOnDoneButton() {
		clickOnElement(doneButton);

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(addContactToBlock);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	public void blockContact(String user) {

		clickOnAddContactToBlock();
		clickOnSearchBar();
		enterTextWithClear(searchForNumberOrName, user);

		try {
			List<WebElement> allUsersFoundList = driver.findElements(allUsersFound);

			//iterate over all users and check for user with exact name
			for(WebElement eachUser : allUsersFoundList) {
				String userName = eachUser.findElement(contactNameSuffix).getAttribute("name");
				if(user.equalsIgnoreCase(userName)) {
					//user found. click on it to block.
					driver.tap(1, eachUser, 1);
				}
			}
		} catch(Exception e) {}

		clickOnDoneButton();
	}

	public void unblockContact(String user) {

		try {
			clickOnSearchBar();
			enterTextWithClear(searchForNumberOrName, user);

			List<WebElement> allUsersList = driver. findElements(allUsersFound);

			//iterate over all users and check for user with exact name
			for(WebElement eachUser : allUsersList) {
				String userName = eachUser.findElement(contactNameSuffix).getAttribute("name");
				if(user.equalsIgnoreCase(userName)) {
					//user found. click on it to block.
					driver.tap(1, eachUser, 1);
				}
			}
		} catch(Exception e) {}
	}
	
	public int countOfBlockedContacts() {
		
		int countOfBlockContacts = 0;
		
		try {
			boolean noBlocksText = isElementPresent(someoneTroublingYouDiv);
			if(noBlocksText) {
				return countOfBlockContacts;
			}
			List<WebElement> allBlockedUsers = driver.findElements(allUsersFound);
			countOfBlockContacts = allBlockedUsers.size();
			
		} catch(Exception e) {
			
		}
		
		return countOfBlockContacts;
	}
	
	public String getBlockStatus(int iterator) {
		
		String status = "";
		try {
			By blockStatusButton = MobileBy.IosUIAutomation(allUsersFound + "[" + iterator + "]" + contactBlockStatus);
			status = getTextByValue(blockStatusButton);
		} catch (Exception e) {
			
		}
		
		return status;
	}

}
