package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class TermsScreen extends AppiumLibrary{

	public static By Terms_Header = By.name("Terms");
	public String getText_Terms_Header()
		{
		return(getTextByName(Terms_Header));
		}

    public static By back_icon = By.name("Back");
    public void clickOnBackIcon()
    	{
	     clickOnElement(back_icon);
    	}

}
