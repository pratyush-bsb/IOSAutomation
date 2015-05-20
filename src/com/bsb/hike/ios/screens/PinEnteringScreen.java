package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class PinEnteringScreen extends HikeLibrary {

	public PinEnteringScreen() {
		waitForPinEnteringScreenToLoad();
	}

	private void waitForPinEnteringScreenToLoad() {

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(Pin_Screen_Title);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By Back_Icon = MobileBy.name("Back");
	protected By Pin_Screen_Title=MobileBy.name("Verify");
	protected By Did_You_Get_SMS_Text=MobileBy.name("Did you get an SMS PIN? Enter it below.");
	protected By Pin_EditText= MobileBy.IosUIAutomation(".textFields()[0]");
	protected By Did_not_get_pin_Text=MobileBy.name("Didn't get the PIN? We'll call you.");
	protected By Call_Me_Btn=MobileBy.name("Call Me (02:58)");    
	protected By Cross_Btn = MobileBy.name("Clear text");

	//MobileElement cell=((Object) driver.findElementByIosUIAutomation("tableViews()[0].cells().firstWithPredicate(\"staticTexts[0].label== 'Page Control''\";

	public void clickOnBackIcon()
	{
		clickOnElement(Back_Icon);
	}

	public String getText_PinScreenTitle()
	{
		return(getTextByName(Pin_Screen_Title));
	}

	public String getText_DidYouGetSMSText()
	{
		return(getTextByName(Did_You_Get_SMS_Text));
	}

	public String getText_PinEditField()
	{
		return(getTextByName(Pin_EditText));
	}

	public void clickOnCrossBtn()
	{
		clickOnElement(Cross_Btn);
	}

	public void clickOnPinTextField(){
		clickOnElement(Pin_EditText);
	}

	public LoginAboutYouScreen setPin(String value)
	{
		enterText(Pin_EditText, value);  //add 1111 in a variable
		return new LoginAboutYouScreen();
	}


	//To write Send keys method for pin entering

}
