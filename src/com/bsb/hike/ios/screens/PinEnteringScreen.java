package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileElement;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.SendKeysAction;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.common.Locators;

public class PinEnteringScreen extends AppiumLibrary{

	public static By Back_Icon = By.name("Back");
	public static By Pin_Screen_Title=By.name("Verify");
	public static By Did_You_Get_SMS_Text=By.name("Did you get an SMS PIN? Enter it below.");
	public static By Pin_EditText=By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");//("PIN");   //This is value field
	public static By Did_not_get_pin_Text=By.name("Didn't get the PIN? We'll call you.");
	public static By Call_Me_Btn=By.name("Call Me (02:58)");    
	public static By Cross_Btn = By.name("Clear text");
	
	//MobileElement cell=((Object) driver.findElementByIosUIAutomation("tableViews()[0].cells().firstWithPredicate(\"staticTexts[0].label== 'Page Control''\";
	
	public void clickOnBackIcon()
		{
		clickOnElement(Back_Icon);
		}
	
	public static String getText_PinScreenTitle()
		{
		return(getText(Pin_Screen_Title));
		}
	
	public String getText_DidYouGetSMSText()
		{
		return(getText(Did_You_Get_SMS_Text));
		}
	
	public String getText_PinEditField()
		{
		return(getText(Pin_EditText));
		}
	
	public void clickOnCrossBtn()
		{
		clickOnElement(Cross_Btn);
		}
	
	public static void clickOnPinTextField(){
		clickOnElement(Pin_EditText);
	}
	
	public static void setPin(String value)
		{
		enterText(Pin_EditText, value);  //add 1111 in a variable
		}
	
	
	//To write Send keys method for pin entering
	
}
