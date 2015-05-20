package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class MyProfileScreen extends AppiumLibrary{

	public static By Back_BTN = MobileBy.name("Back");
	public static By MyProfileTitle_LBL = By.name("My Profile");
	public static String MyProfileTitle_LBL_Txt = "My Profile";
	public static By Edit_BTN = MobileBy.name("Edit");
	public static By Camera_BTN = MobileBy.name("largeCamera");
	public static By ProfileName_LBL = MobileBy.name("tgg"); // ** To be added in a variable
	public static By PostStatus_BTN = MobileBy.name("Post a Status");
	
	public void clickOnBack_BTN()
		{
		clickOnElement(Back_BTN);
		}
	
	public static String getText_MyProfileTitle()
		{
		return(getTextByName(MyProfileTitle_LBL));
		}
	
	public static void clickOnEdit_BTN()
		{
		clickOnElement(Edit_BTN);
		}
	
	public void clickOnCamera_BTN()
		{
		clickOnElement(Camera_BTN);
		}
	
	public String getText_ProfileName()
		{	
		 return(getTextByName(ProfileName_LBL));
		}
	
	public void clickOnPostStatus_BTN()
		{
		clickOnElement(PostStatus_BTN);
		}
	
}
