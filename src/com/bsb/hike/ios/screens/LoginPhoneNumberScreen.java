package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.base.*;
import com.bsb.hike.ios.common.Locators;

public class LoginPhoneNumberScreen extends AppiumLibrary{

	public static By Phone_Number_Title = By.name("Phone Number");
	public static By Back_Icon=By.name("Back");
	public static By Next_Btn=By.name("Next");
	public static By Hi_Whats_Your_Number_Lbl=By.name("Hi! What's your number?");
	public static By Country_Code_Btn=By.name("+91 India");
	public static By Phone_Number_EditText=By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");//("Phone Number");
	
	
	public String getText_PhoneNumberScreenTitle()
		{
		 return(getText(Phone_Number_Title));
		}	
	public void clickOnBackIcon()
		{
		clickOnElement(Back_Icon);
		}	
	public boolean isNextBtnEnabled()
		{
		return(isEnabled(Next_Btn));
		}
	public static void clickOnNextBtn()
		{
		clickOnElement(Next_Btn);
		}
	public String getText_WhatsYourNumberLbl()
		{
		return(getText(Hi_Whats_Your_Number_Lbl));
		}
	public void clickOnCountryCode()
		{
		clickOnElement(Country_Code_Btn);
		}
	public String getText_CountryCodeField()
		{
		return(getText(Country_Code_Btn));
		}
	public static void clickOnPhoneNumberField()
		{
		clickOnElement(Phone_Number_EditText);
		}
	public String getText_PhoneNumberField()
		{
		return(getText(Phone_Number_EditText));
		}
	public static void setText_PhoneNumberField(String value)
		{
		 enterText(Phone_Number_EditText, value);
		}

	
}
