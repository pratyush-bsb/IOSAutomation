package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class HiddenMode_FtuePopUp extends AppiumLibrary {

	public static By IntroducingHiddenModeTitle_LBL=By.name("Introducing Hidden Mode");
	public static By HiddenModeSubTitle_LBL=By.name("Now you can hide your private chats from the world and read them only with a password. Try it now!");
	public static By QuickSetup_BTN=By.name("Quick Setup");
	
	public String getText_HiddenModePopupTitle()
		{
		return(getText(IntroducingHiddenModeTitle_LBL));
		}
	
	public String getTextHiddenModePopupSubtitle()
		{
		 return(getText(HiddenModeSubTitle_LBL));
		}

	public void clickOnQuickSetup_BTN()
		{
		 clickOnElement(QuickSetup_BTN);
		}
}