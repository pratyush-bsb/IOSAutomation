package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class EditProfileScreen extends AppiumLibrary {

	public static By Back_BTN = MobileBy.name("Edit Profile");
	public static String Back_BTN_Txt = "Edit Profile";
	public static By EditProfileScreenTitle_LBL = MobileBy.name("Edit Profile");
	public static String EditProfileScreenTitle_LBL_Txt = "Edit Profile";
	public static By Done_BTN = MobileBy.name("Done");
	public static By Name_LBL = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].textFields()");
	public static By Mobile_LBL = MobileBy.name("Mobile");
	public static By Email_LBL = MobileBy.name("E-mail");
	public static By EditPicture_BTN = MobileBy.name("Edit Picture");
	public static By Male_BTN = MobileBy.name("Male");
	public static By Female_BTN = MobileBy.name("Female");


	public static void setName(String value)	
	{
		
		enterTextWithClear(Name_LBL, value);
		//WebElement m = driver.findElement(Name_LBL);
		//		m.getAttribute("value").length();
		//m.clear();
		//enterText(Name_LBL, value);
	}

	public static void clickOnDone_BTN()
	{
		clickOnElement(Done_BTN);
	}
	public static boolean getState_Done_BTN(){
		return isEnabled(Done_BTN);
	}

	
	public void clickOnBack_BTN()
	{
		clickOnElement(Back_BTN);
	}

	public static String getText_EditProfileScreenTitle_LBL()
	{
		return(getTextByName(EditProfileScreenTitle_LBL));				
	}
	public static String getText_Back_BTN()
	{
		return(getTextByName(Back_BTN));				
	}


	public String getTextName_LBL()
	{
		return(getTextByName(Name_LBL));
	}

	public String getTextMobile_LBL()
	{
		return(getTextByName(Mobile_LBL));
	}

	public String getTextEmail_LBL()
	{
		return(getTextByName(Email_LBL));
	}

	public void clickOnEditPicture_BTN()
	{
		clickOnElement(EditPicture_BTN);
	}

	public void clickOnMale_BTN()
	{
		clickOnElement(Male_BTN);
	}

	public void clickOnFemale_BTN()
	{	
		clickOnElement(Female_BTN);	
	}
}
