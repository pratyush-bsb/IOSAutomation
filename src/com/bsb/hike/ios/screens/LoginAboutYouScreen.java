package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class LoginAboutYouScreen extends HikeLibrary {
	
	public LoginAboutYouScreen() {
		waitForLoginAboutYouScreenToLoad();
	}

	private void waitForLoginAboutYouScreenToLoad() {
		
		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(AboutYouTitle_Lbl);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By AboutYouTitle_Lbl = MobileBy.name("About You");
	protected By NextBtn = MobileBy.name("Next");
	protected By AddPhoto_Btn = MobileBy.name("add photo");
	protected By Name_EditText = MobileBy.IosUIAutomation(".textFields()[0]");
	protected By FeelingLazy_Lbl = MobileBy.name("Feeling lazy? We'll do it for you");
	protected By ConnectWithFacebook_Btn = MobileBy.name("Connect with Facebook");
	protected By ClearText_Btn = MobileBy.name("Clear text");


	public String getTextByName_AboutYouTitle()
	{
		return(getTextByName(AboutYouTitle_Lbl));
	}

	public boolean iSNextButtonEnabled()
	{
		return(isEnabled(NextBtn));
	}

	public void clickOnNextBtn()
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

	public String getTextByName_NameEnteringEditText()
	{
		return(getTextByName(Name_EditText));
	}
	public void clickOnNameEnteringScreen()
	{
		clickOnElement(Name_EditText);
	}
	public void setName(String value)
	{
		enterText(Name_EditText, value); //name value to be put in a variable
	}






	public String getTextByName_FeelingLazyLbl()
	{
		return(getTextByName(FeelingLazy_Lbl));
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
