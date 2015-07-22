package com.bsb.hike.ios.screens;


import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class WelcomeScreen extends HikeLibrary{

	public WelcomeScreen() {
		waitForWelcomeScreenToLoad();
	}

	private void waitForWelcomeScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(getStartedButton);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By getStartedButton = MobileBy.name("Get Started");
	protected By termsAndConditions = By.name("Terms and Conditions");
	protected By madeWithLoveBy = By.name("Made with love in India");
	protected String HikeMessengerNameIdentifier = "signupLogo";
	protected By signUpLogo = MobileBy.name("signupLogo");
	protected By stagingButton = MobileBy.name("Staging");

	public LoginPhoneNumberScreen clickOnGetStartedBTN()
	{
		clickOnElement(getStartedButton);
		return new LoginPhoneNumberScreen();
	}
	
	public By getMadeWithLoveBy() {
		return madeWithLoveBy;
	}
	
	public By getTermsAndConditions() {
		return termsAndConditions;
	}
	
	public By getSignUpLogo() {
		return signUpLogo;
	}
	
	public By getGetStartedButton() {
		return getStartedButton;
	}

	public String getText_Get_Started_BTN()
	{
		return(getTextByName(getStartedButton));
	}



	public void clickOnTermsAndPrivacyLink()
	{
		clickOnElement(termsAndConditions);
	}
	public String getText_TermsAndPrivacyLink()
	{
		return(getTextByName(termsAndConditions));
	}



	public String getText_MadeWithLoveInIndiaText()
	{
		return(getTextByName( madeWithLoveBy));
	}

	public String getHikeMessengerNameIdentifier() {
		return HikeMessengerNameIdentifier;
	}

	public void selectStagingEnvironment() {
		
		try {
			clickOnElement(stagingButton);
		} catch(Exception e) {}
	}
	
	public TermsAndConditions clickOnTermsAndConditions() {
		
		clickOnElement(getTermsAndConditions());
		return new TermsAndConditions();
	}

}
