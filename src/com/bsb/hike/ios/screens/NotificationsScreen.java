package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class NotificationsScreen extends HikeLibrary {
	
	public NotificationsScreen() {
		waitForNotificationsPageToLoad();
	}

	private void waitForNotificationsPageToLoad() {
		int counter = 0;
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
	
	//protected identifiers
	
	protected By header = MobileBy.name("Notifications");
	protected String headerString = "Notifications";
	protected By backButton = MobileBy.name("Back");
	protected String defaultNotificationToneName = "hike-Tone";
	
	protected By notificationTone = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].staticTexts()[1]");
	protected By messageAlertSwitch = MobileBy.name("Message Alerts, Play sound on a new message"); // get value
	protected By inChatSounds = MobileBy.name("In-chat Sounds, For incoming & outgoing messages");
	protected By vibrationSwitch = MobileBy.name("Vibration, Vibrate on a new message");
	protected By messagePreviewSwitch = MobileBy.name("Message Preview, Show preview in notifications");
	protected By disableNudgeSwitch = MobileBy.name("Disable Nudge, Sending only");
	protected By contactJoiningSwitch = MobileBy.name("Contact Joining Hike, Notify you when a contact joins hike");
	
	//public getters
	public By getHeader() {
		return header;
	}

	public String getHeaderString() {
		return headerString;
	}

	public By getBackButton() {
		return backButton;
	}

	public String getDefaultNotificationToneName() {
		return defaultNotificationToneName;
	}

	public By getNotificationTone() {
		return notificationTone;
	}

	public By getMessageAlertSwitch() {
		return messageAlertSwitch;
	}

	public By getInChatSounds() {
		return inChatSounds;
	}

	public By getVibrationSwitch() {
		return vibrationSwitch;
	}

	public By getMessagePreviewSwitch() {
		return messagePreviewSwitch;
	}

	public By getDisableNudgeSwitch() {
		return disableNudgeSwitch;
	}

	public By getContactJoiningSwitch() {
		return contactJoiningSwitch;
	}
	
	
	public SettingsScreen goBackToSettingsPage() {
		clickOnElement(backButton);
		return new SettingsScreen();
	}

}
