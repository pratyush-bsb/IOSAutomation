package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class MyProfileScreen extends AppiumLibrary{

	public static By Back_BTN=By.name("Back");
	public static By MyProfileTitle_LBL=By.name("My Profile");
	public static By Edit_BTN=By.name("Edit");
	public static By Camera_BTN=By.name("largeCamera");
	public static By ProfileName_LBL=By.name("tgg"); // ** To be added in a variable
	public static By PostStatus_BTN=By.name("Post a Status");
	
	public void clickOnBack_BTN()
		{
		clickOnElement(Back_BTN);
		}
	
	public static String getText_MyProfileTitle()
		{
		return(getText(MyProfileTitle_LBL));
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
		 return(getText(ProfileName_LBL));
		}
	
	public void clickOnPostStatus_BTN()
		{
		clickOnElement(PostStatus_BTN);
		}
	
}
