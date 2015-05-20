package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.screens.PushNotificationsScreen;

public class ChooseYourProfilePicturePopUp_NameEnteringScreen extends AppiumLibrary{

	public static By ChooseYourProfilePic_Lbl = MobileBy.name("Choose a Profile Picture");
	public static By ItHelpsYourFriends_Lbl = MobileBy.name("It helps your friends find you  on hike.");
	public static By YesIWantTo_Btn = MobileBy.name("Yes, I want to");
	public static By NoThanks_Btn = MobileBy.name("No, Thanks");

	public String getTextByName_ChooseYourProfilePic_Lbl()
	{
		return(getTextByName(ChooseYourProfilePic_Lbl));
	}

	public String getTextByName_ItHelpsYourFriends_Lbl()
	{
		return(getTextByName(ItHelpsYourFriends_Lbl));
	}

	public void clickOnYesBtn()
	{
		clickOnElement(YesIWantTo_Btn);
	}

	public static PushNotificationsScreen clickOnNoBtn()
	{
		clickOnElement(NoThanks_Btn);
		return new PushNotificationsScreen();
	}

}

