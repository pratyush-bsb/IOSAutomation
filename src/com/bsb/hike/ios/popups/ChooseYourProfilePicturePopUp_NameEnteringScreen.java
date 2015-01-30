package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class ChooseYourProfilePicturePopUp_NameEnteringScreen extends AppiumLibrary{

	public static By ChooseYourProfilePic_Lbl=By.name("Choose a Profile Picture");
	public static By ItHelpsYourFriends_Lbl=By.name("It helps your friends find you  on hike.");
	public static By YesIWantTo_Btn= By.name("Yes, I want to");
	public static By NoThanks_Btn=By.name("No, Thanks");
	
	public String getText_ChooseYourProfilePic_Lbl()
		{
		return(getText(ChooseYourProfilePic_Lbl));
		}
	
	public String getText_ItHelpsYourFriends_Lbl()
		{
		return(getText(ItHelpsYourFriends_Lbl));
		}
	
	public void clickOnYesBtn()
		{
		clickOnElement(YesIWantTo_Btn);
		}
	
	public static void clickOnNoBtn()
		{
		clickOnElement(NoThanks_Btn);
		}
	
}

