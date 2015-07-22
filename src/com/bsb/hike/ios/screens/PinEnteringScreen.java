package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class PinEnteringScreen extends HikeLibrary {

	public PinEnteringScreen() {
		waitForPinEnteringScreenToLoad();
	}

	private void waitForPinEnteringScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(pinScreenTitle);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By backIcon = MobileBy.name("Back");
	protected By pinScreenTitle = MobileBy.name("Verify");
	protected By didYouGetSmsText = MobileBy.name("Did you get an SMS PIN? Enter it below.");
	protected By pinEditText = MobileBy.IosUIAutomation(".textFields()[0]");
	protected By didNotGetPinText = MobileBy.name("Didn't get the PIN? We'll call you.");
	protected By callMeButton = MobileBy.IosUIAutomation(".buttons()[0]");    
	protected By crossButton = MobileBy.name("Clear text");


	public By getBackIcon() {
		return backIcon;
	}

	public By getPinScreenTitle() {
		return pinScreenTitle;
	}

	public By getDidYouGetSmsText() {
		return didYouGetSmsText;
	}

	public By getPinEditText() {
		return pinEditText;
	}

	public By getDidNotGetPinText() {
		return didNotGetPinText;
	}

	public By getCallMeButton() {
		return callMeButton;
	}

	public By getCrossButton() {
		return crossButton;
	}

	//public methods
	public LoginPhoneNumberScreen clickOnBackIcon()
	{
		clickOnElement(backIcon);
		return new LoginPhoneNumberScreen();
	}

	public String getText_PinScreenTitle()
	{
		return(getTextByName(pinScreenTitle));
	}

	public String getText_DidYouGetSMSText()
	{
		return(getTextByName(didYouGetSmsText));
	}

	public String getText_PinEditField()
	{
		return(getTextByName(pinEditText));
	}

	public void clickOnCrossBtn()
	{
		clickOnElement(crossButton);
	}

	public void clickOnPinTextField(){
		clickOnElement(pinEditText);
	}

	public LoginAboutYouScreen setPin(String value)
	{
		enterText(pinEditText, value);  //add 1111 in a variable
		return new LoginAboutYouScreen();
	}
	
	public void clearEnteredPin() {
		try {
			WebElement clearText = driver.findElement(crossButton);
			new TouchAction(driver).press(clearText).perform();
		} catch(Exception e) {}
	}


	//To write Send keys method for pin entering

}
