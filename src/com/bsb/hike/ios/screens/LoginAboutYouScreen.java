package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class LoginAboutYouScreen extends AppiumLibrary{

	public static By AboutYouTitle_Lbl=By.name("About You");
	public static By NextBtn=By.name("Next");
	public static By AddPhoto_Btn=By.name("add photo");
	public static By Name_EditText=By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");
	public static By FeelingLazy_Lbl=By.name("Feeling lazy? We'll do it for you");
	public static By ConnectWithFacebook_Btn=By.name("Connect with Facebook");
	public static By ClearText_Btn=By.name("Clear text");


	public String getText_AboutYouTitle()
	{
		return(getText(AboutYouTitle_Lbl));
	}

	public boolean iSNextButtonEnabled()
	{
		return(isEnabled(NextBtn));
	}

	public static void clickOnNextBtn()
	{
		clickOnElement(NextBtn);
	}

	public boolean isAddPhotoBtnEnabled()
	{
		return(isEnabled(AddPhoto_Btn));
	}

	public void clickOnAddPhotoBtn()
	{
		clickOnElement(AddPhoto_Btn);
	}

	public String getText_NameEnteringEditText()
	{
		return(getText(Name_EditText));
	}
	public static void clickOnNameEnteringScreen()
	{
		clickOnElement(Name_EditText);
	}
	public static void setName(String value)
	{
		enterText(Name_EditText, value); //name value to be put in a variable
	}






	public String getText_FeelingLazyLbl()
	{
		return(getText(FeelingLazy_Lbl));
	}

	public void clickOnConnectWithFacebookBtn()
	{
		clickOnElement(ConnectWithFacebook_Btn);
	}

	public boolean isConnectWithFacebookBtnEnabled()
	{
		return(isEnabled(ConnectWithFacebook_Btn));
	}

	public void clickOnClearText_Btn()
	{
		clickOnElement(ClearText_Btn);
	}
}
