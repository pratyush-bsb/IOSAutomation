package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.screens.EnterPasscodeForStealth;

public class HiddenModeFTUEPopUp extends AppiumLibrary {
	
	public HiddenModeFTUEPopUp() {
		waitForHiddenModePopupToLoad();
	}

	private void waitForHiddenModePopupToLoad() {
		
		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 10) {
			try {
				driver.findElement(introducingHiddenModeTitle_LBL);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	//protected identifiers
	protected By introducingHiddenModeTitle_LBL=By.name("Introducing Hidden Mode");
	protected By hiddenModeSubTitle_LBL=By.name("Now you can hide your private chats from the world and read them only with a password. Try it now!");
	protected By quickSetup_BTN=By.name("Quick Setup");
	
	//public getters
	public By getIntroducingHiddenModeTitle_LBL() {
		return introducingHiddenModeTitle_LBL;
	}
	
	public By getHiddenModeSubTitle_LBL() {
		return hiddenModeSubTitle_LBL;
	}
	
	public By getQuickSetup_BTN() {
		return quickSetup_BTN;
	}
	
	
	public String getText_HiddenModePopupTitle()
		{
		return(getTextByName(introducingHiddenModeTitle_LBL));
		}
	
	public String getTextHiddenModePopupSubtitle()
		{
		 return(getTextByName(hiddenModeSubTitle_LBL));
		}

	public void clickOnQuickSetup_BTN()
		{
		 clickOnElement(quickSetup_BTN);
		}
	
	public EnterPasscodeForStealth clickOnQuickSetup() {
		clickOnElement(quickSetup_BTN);
		return new EnterPasscodeForStealth();
	}

}