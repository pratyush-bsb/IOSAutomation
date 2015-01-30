package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.common.Locators;

public class TermsScreen extends AppiumLibrary{

	public static By Terms_Header = By.name("Terms");
	public String getText_Terms_Header()
		{
		return(getText(Terms_Header));
		}

    public static By back_icon = By.name("Back");
    public void clickOnBackIcon()
    	{
	     clickOnElement(back_icon);
    	}

}
