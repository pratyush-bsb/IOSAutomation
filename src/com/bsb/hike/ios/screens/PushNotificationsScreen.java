package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class PushNotificationsScreen extends AppiumLibrary{

	public static By PushNotifications_Lbl=By.name("Push Notifications");
	public static By GetNotified_Lbl=By.name("Get notified when your friends message you!");
	public static By Continue_Btn=By.name("Continue");
	
	public String getText_PushNotification_Lbl()
		{
		return(getText(PushNotifications_Lbl));
		}
	
	public String getText_GetNotified_Lbl()
		{
		return(getText(GetNotified_Lbl));
		}
	
	public static void clickOnContinue_Btn()
		{
		clickOnElement(Continue_Btn);
		}
}
