package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class LoginPhoneNumberScreen extends HikeLibrary {
	
	public LoginPhoneNumberScreen() {
		waitForPhoneNumberScreenToLoad();
	}

	private void waitForPhoneNumberScreenToLoad() {

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(Phone_Number_Title);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By Phone_Number_Title = MobileBy.name("Phone Number");
	protected By Back_Icon=MobileBy.name("Back");
	protected By Next_Btn=MobileBy.name("Next");
	protected By Hi_Whats_Your_Number_Lbl=MobileBy.name("Hi! What's your number?");
	protected By Country_Code_Btn=MobileBy.name("+91 India");
	protected By Phone_Number_EditText = MobileBy.IosUIAutomation(".textFields()[0]");


	public String getText_PhoneNumberScreenTitle()
	{
		return(getTextByName(Phone_Number_Title));
	}	
	public void clickOnBackIcon()
	{
		clickOnElement(Back_Icon);
	}	
	public boolean isNextBtnEnabled()
	{
		return(isEnabled(Next_Btn));
	}
	public void clickOnNextBtn()
	{
		clickOnElement(Next_Btn);
	}
	public String getText_WhatsYourNumberLbl()
	{
		return(getTextByName(Hi_Whats_Your_Number_Lbl));
	}
	public void clickOnCountryCode()
	{
		clickOnElement(Country_Code_Btn);
	}
	public String getText_CountryCodeField()
	{
		return(getTextByName(Country_Code_Btn));
	}
	public void clickOnPhoneNumberField()
	{
		clickOnElement(Phone_Number_EditText);
	}
	public String getText_PhoneNumberField()
	{
		return(getTextByName(Phone_Number_EditText));
	}
	public void setTextPhoneNumberField(String value)
	{
		enterText(Phone_Number_EditText, value);
	}


}
