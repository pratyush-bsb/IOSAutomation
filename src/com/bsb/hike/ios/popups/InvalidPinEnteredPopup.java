package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;

public class InvalidPinEnteredPopup extends HikeLibrary {
	
	protected By goBackButton = MobileBy.name("Go Back");
	protected By cancelButton = MobileBy.name("Cancel");
	protected By popupHeader = MobileBy.name("Invalid PIN Entered");
	protected By popupText = MobileBy.name("Please enter the correct PIN.");
	
	//public getters
	public By getGoBackButton() {
		return goBackButton;
	}
	public By getCancelButton() {
		return cancelButton;
	}
	public By getPopupHeader() {
		return popupHeader;
	}
	public By getPopupText() {
		return popupText;
	}
	
	public void clickOnCancel() {
		clickOnElement(cancelButton);
	}
	
	public LoginPhoneNumberScreen clickOnGoBack() {
		clickOnElement(goBackButton);
		return new LoginPhoneNumberScreen();
	}

}
