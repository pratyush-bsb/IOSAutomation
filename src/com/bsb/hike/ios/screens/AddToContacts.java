package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class AddToContacts extends HikeLibrary {

	public AddToContacts() {
		waitForAddToContactsToLoad();
	}

	private void waitForAddToContactsToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 20) {
			try {
				driver.findElement(header);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	protected By backButton = MobileBy.name("Back");
	protected By stopButton = MobileBy.name("Stop");
	protected By header = MobileBy.name("Add to Contacts");

	//public getters
	public By getBackButton() {
		return backButton;
	}
	public By getStopButton() {
		return stopButton;
	}
	public By getHeader() {
		return header;
	}

	//public methods
	public boolean findContactNumberElement(String contact) {

		boolean contactFound = false;
		By contactBy = MobileBy.name(contact);
		contactFound = isElementPresent(contactBy);
		return contactFound;
	}
	
	public void cancelAdding() {
		
		clickOnElement(stopButton);
	}
}
