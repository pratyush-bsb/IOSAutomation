package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class ConfirmYourNumberPopUpScreen extends AppiumLibrary {

	public static By Confirm_Your_Number_Lbl= By.name("Confirm your number");
	// public static By MSISDN_Lbl=By.name("+919540752593");   MSISDN should be passes in a variable here
	public static By Cancel_Btn=By.name("Cancel");
	public static By Confirm_Btn=By.name("Confirm");

	public static void clickOnConfirmBtn(){
		clickOnElement(Confirm_Btn);
	}
}


