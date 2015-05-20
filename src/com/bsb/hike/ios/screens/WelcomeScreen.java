package com.bsb.hike.ios.screens;


import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class WelcomeScreen extends HikeLibrary{

	public WelcomeScreen() {
		waitForWelcomeScreenToLoad();
	}

	private void waitForWelcomeScreenToLoad() {

		int counter = 5;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(Get_Started_BTN);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By Get_Started_BTN = MobileBy.name("Get Started");
	protected By Terms_and_Privacy_Link= By.name("Terms and Privacy");
	protected By Made_With_Love_In_India_Txt = By.name("Made with love in India");
	protected String HikeMessengerNameIdentifier = "signupLogo";

	public LoginPhoneNumberScreen clickOnGetStartedBTN()
	{
		clickOnElement(Get_Started_BTN);
		return new LoginPhoneNumberScreen();
	}

	public String getText_Get_Started_BTN()
	{
		return(getTextByName(Get_Started_BTN));
	}



	public void clickOnTermsAndPrivacyLink()
	{
		clickOnElement(Terms_and_Privacy_Link);
	}
	public String getText_TermsAndPrivacyLink()
	{
		return(getTextByName(Terms_and_Privacy_Link));
	}



	public String getText_MadeWithLoveInIndiaText()
	{
		return(getTextByName( Made_With_Love_In_India_Txt));
	}

	public String getHikeMessengerNameIdentifier() {
		return HikeMessengerNameIdentifier;
	}




}
