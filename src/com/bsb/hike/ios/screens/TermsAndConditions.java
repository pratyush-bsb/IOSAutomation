package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class TermsAndConditions extends AppiumLibrary{


	public TermsAndConditions() {
		waitForTermsAndConditionsToLoad();
	}

	private void waitForTermsAndConditionsToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(termsHeader);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By termsHeader = By.name("Terms and Conditions");
	protected By backIcon = By.name("Back");
	
	public By getTermsHeader() {
		return termsHeader;
	}
	
	public By getBackButton() {
		return backIcon;
	}
	
	//public methods
	public String getText_Terms_Header()
	{
		return(getTextByName(termsHeader));
	}
	
	public WelcomeScreen clickOnBackIcon()
	{
		clickOnElement(backIcon);
		return new WelcomeScreen();
	}

}
