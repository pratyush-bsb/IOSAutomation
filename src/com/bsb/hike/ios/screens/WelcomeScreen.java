package com.bsb.hike.ios.screens;


import com.bsb.appium.Library.AppiumLibrary;
import org.openqa.selenium.By;

public class WelcomeScreen extends AppiumLibrary{

	public static By Get_Started_BTN = By.name("Get Started");
	public static By Terms_and_Privacy_Link= By.name("Terms and Privacy");
	public static By Made_With_Love_In_India_Txt = By.name("Made with love in India");

	public static void clickOnGetStartedBTN()
		{
		clickOnElement(Get_Started_BTN);
		}
	
	public String getText_Get_Started_BTN()
		{
		 return(getText(Get_Started_BTN));
		}
	
	
	
	public void clickOnTermsAndPrivacyLink()
		{
		clickOnElement(Terms_and_Privacy_Link);
		}
	public String getText_TermsAndPrivacyLink()
		{
		return(getText(Terms_and_Privacy_Link));
		}
	
	
	
	public String getText_MadeWithLoveInIndiaText()
		{
		return(getText( Made_With_Love_In_India_Txt));
		}
	
	
	
	
}
