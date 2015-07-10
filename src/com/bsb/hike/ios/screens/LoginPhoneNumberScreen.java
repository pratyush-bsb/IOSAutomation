package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class LoginPhoneNumberScreen extends HikeLibrary {
	
	public LoginPhoneNumberScreen() {
		waitForPhoneNumberScreenToLoad();
	}

	private void waitForPhoneNumberScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(phoneNumberHeader);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By phoneNumberHeader = MobileBy.name("Phone Number");
	protected By backIcon = MobileBy.name("Back");
	protected By nextButton = MobileBy.name("Next");
	protected By whatsYourNumberLabel = MobileBy.name("Hi! What's your number?");
	protected By countryCodeButton = MobileBy.name("+91 India");
	protected By phoneNumberEdit = MobileBy.IosUIAutomation(".textFields()[0]");
	protected By clearTextButton = MobileBy.name("Clear text");


	public By getPhoneNumberHeader() {
		return phoneNumberHeader;
	}
	
	public By getClearTextButton() {
		return clearTextButton;
	}

	public By getBackIcon() {
		return backIcon;
	}

	public By getNextButton() {
		return nextButton;
	}

	public By getWhatsYourNumberLabel() {
		return whatsYourNumberLabel;
	}

	public By getCountryCodeButton() {
		return countryCodeButton;
	}

	public By getPhoneNumberEdit() {
		return phoneNumberEdit;
	}

	public String getText_PhoneNumberScreenTitle()
	{
		return(getTextByName(phoneNumberHeader));
	}	
	public void clickOnBackIcon()
	{
		clickOnElement(backIcon);
	}	
	public boolean isNextBtnEnabled()
	{
		return(isEnabled(nextButton));
	}
	public void clickOnNextBtn()
	{
		clickOnElement(nextButton);
	}
	public String getText_WhatsYourNumberLbl()
	{
		return(getTextByName(whatsYourNumberLabel));
	}
	public CountryScreen clickOnCountryCode()
	{
		clickOnElement(countryCodeButton);
		return new CountryScreen();
	}
	public String getText_CountryCodeField()
	{
		return(getTextByName(countryCodeButton));
	}
	public void clickOnPhoneNumberField()
	{
		clickOnElement(phoneNumberEdit);
	}
	public String getText_PhoneNumberField()
	{
		return(getTextByName(phoneNumberEdit));
	}
	public void setTextPhoneNumberField(String value)
	{
		enterText(phoneNumberEdit, value);
	}
	
	public void removeEnteredText() {
		
		try {
			WebElement clearTextElement = driver.findElement(clearTextButton);
			new TouchAction(driver).press(clearTextElement).perform();
		} catch(Exception e) {}
	}


}
