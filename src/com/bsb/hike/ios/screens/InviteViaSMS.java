package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class InviteViaSMS extends HikeLibrary {
	
	public InviteViaSMS() {
		waitForInviteViaSMSPageToLoad();
	}

	private void waitForInviteViaSMSPageToLoad() {
		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(inviteViaSMSScreenHeader);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	
	//protected getters :
	
	protected By inviteViaSMSScreenHeader = MobileBy.name("Invite via SMS");
	protected By backButton = MobileBy.name("Back");
	protected By stopButton = MobileBy.name("Stop");
	protected By inviteButton = MobileBy.name("Invite");
	
	protected By searchForANumber = MobileBy.IosUIAutomation(".searchBars()[0]");
	protected By allContacts = MobileBy.IosUIAutomation(".tableViews()[0].cells()");
	
	protected By contactName = MobileBy.IosUIAutomation(".staticTexts()[0]");
	protected By contactNumber = MobileBy.IosUIAutomation(".staticTexts()[1]");
	
	
	//public getters
	public By getInviteViaSMSScreenHeader() {
		return inviteViaSMSScreenHeader;
	}

	public By getBackButton() {
		return backButton;
	}

	public By getStopButton() {
		return stopButton;
	}

	public By getInviteButton() {
		return inviteButton;
	}

	public By getSearchForANumber() {
		return searchForANumber;
	}

	public By getAllContacts() {
		return allContacts;
	}

	public By getContactName() {
		return contactName;
	}

	public By getContactNumber() {
		return contactNumber;
	}
	
	//public methods
	public int getCountOfContacts() {
		
		int contacts = 0;
		
		try {
			List<WebElement> allContactsList = driver.findElements(allContacts);
			contacts = allContactsList.size();
		} catch(Exception e) {}
		
		return contacts;
	}
	
	public FreeSMS cancelInvite() {
		
		clickOnElement(stopButton);
		return new FreeSMS();
	}
	
}
