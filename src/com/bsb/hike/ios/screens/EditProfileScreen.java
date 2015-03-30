package com.bsb.hike.ios.screens;

import net.sourceforge.htmlunit.corejs.javascript.ast.Name;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.appium.Library.AppiumLibrary;

public class EditProfileScreen extends AppiumLibrary {

	public static By Back_BTN=By.name("My Profile");
	public static By EditProfileScreenTitle_LBL=By.name("Edit Profile");
	public static By Done_BTN=By.name("Done");	
	public static By Name_LBL=By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIATextField[1]");
	public static By Mobile_LBL=By.name("Mobile");
	public static By Email_LBL=By.name("E-mail");
	public static By EditPicture_BTN=By.name("Edit Picture");
	public static By Male_BTN=By.name("Male");
	public static By Female_BTN=By.name("Female");


	public static void setName(String value)	
	{
		WebElement m = driver.findElement(Name_LBL);
		//		m.getAttribute("value").length();
		m.clear();
		enterText(Name_LBL, value);
	}
	public static void clickOnDone_BTN()
	{
		clickOnElement(Done_BTN);
	}
	public static boolean getState_Done_BTN(){
		return isEnabled(Done_BTN);
	}
	public static boolean checkPresent_EditProfileScreenTitle_LBL() throws InterruptedException{
		return isElementPresent(EditProfileScreenTitle_LBL);
	}

	
	
	
	public static String EditProfileScreenTitle_LBL()
	{
	return(getText(EditProfileScreenTitle_LBL));
	}
	public void clickOnBack_BTN()
	{
		clickOnElement(Back_BTN);
	}
	public static String getText_EditProfileScreenTitle_LBL()
	{
		return(getText(EditProfileScreenTitle_LBL));				
	}
	public static String getText_Back_BTN()
	{
		return(getText(Back_BTN));				
	}


	public String getTextName_LBL()
	{
		return(getText(Name_LBL));
	}

	public String getTextMobile_LBL()
	{
		return(getText(Mobile_LBL));
	}

	public String getTextEmail_LBL()
	{
		return(getText(Email_LBL));
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
