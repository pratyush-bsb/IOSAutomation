package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class FreeSMS extends HikeLibrary {
	
	public FreeSMS() {
		waitForFreeSMSPageToLoad();
	}

	private void waitForFreeSMSPageToLoad() {
		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(freeSMSScreenHeader);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}
	
	//protected headers
	
	protected By freeSMSScreenHeader = MobileBy.name("Free SMS");
	protected By backButton = MobileBy.name("Back");
	protected By inviteButton = MobileBy.name("Invite");
	protected By startHikingButton = MobileBy.name("Start Hiking");
	protected By smsCount = MobileBy.IosUIAutomation(".tableViews()[0].cells()[1].staticTexts()[2]"); //get by name
	
	//public getter methods
	public By getFreeSMSScreenHeader() {
		return freeSMSScreenHeader;
	}

	public By getBackButton() {
		return backButton;
	}

	public By getInviteButton() {
		return inviteButton;
	}

	public By getStartHikingButton() {
		return startHikingButton;
	}

	public By getSmsCount() {
		return smsCount;
	}
	
	//public methods
	
	public int getSMSCount() {
		
		int countOfSMS = 0;
		try {
			String smsCountString = getTextByName(smsCount);
			countOfSMS = Integer.parseInt(smsCountString);
		} catch(Exception e) {}
		
		return countOfSMS;
	}
	
	public InviteViaSMS inviteSMSUsers() {
		
		clickOnElement(inviteButton);
		return new InviteViaSMS();
	}
	
	public StartANewChatScreen startHiking() {
		
		clickOnElement(startHikingButton);
		return new StartANewChatScreen();
	}

}
