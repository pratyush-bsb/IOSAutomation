package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;

public class ConfirmYourNumberPopUpScreen extends AppiumLibrary {

	public static By Confirm_Your_Number_Lbl= By.name("Confirm your number");
	// public static By MSISDN_Lbl=By.name("+919540752593");   MSISDN should be passes in a variable here
	public static By cancelButton=By.name("Cancel");
	public static By confirmButton=By.name("Confirm");
	
	public static By getConfirmButton() {
		return confirmButton;
	}
	
	public static By getCancelButton() {
		return cancelButton;
	}

	public static PinEnteringScreen clickOnConfirmButton(){
		clickOnElement(confirmButton);
		return new PinEnteringScreen();
	}
	
	public static LoginPhoneNumberScreen clickOnCancelButton() {
		
		clickOnElement(cancelButton);
		return new LoginPhoneNumberScreen();
	}
}


