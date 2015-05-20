package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class PushNotificationsScreen extends HikeLibrary {

	public PushNotificationsScreen() {
		waitForPushNotificationScreenToLoad();
	}

	private void waitForPushNotificationScreenToLoad() {

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(PushNotifications_Lbl);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	public By PushNotifications_Lbl = MobileBy.name("Push Notifications");
	public By GetNotified_Lbl = MobileBy.name("Get notified when your friends message you!");
	public By Continue_Btn= MobileBy.name("Continue");

	public String getTextByName_PushNotification_Lbl()
	{
		return(getTextByName(PushNotifications_Lbl));
	}

	public String getTextByName_GetNotified_Lbl()
	{
		return(getTextByName(GetNotified_Lbl));
	}

	public HomeScreenMenu clickOnContinue_Btn()
	{
		clickOnElement(Continue_Btn);
		return new HomeScreenMenu();
	}
}
