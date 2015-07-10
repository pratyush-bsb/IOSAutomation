package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class AccessPhotosInCamera extends HikeLibrary {
	
	protected By popupHeader = MobileBy.name("\"Hike\" Would Like to Access Your Photos");
	protected By OKButton = MobileBy.name("OK");
	protected By dontAllowButton = MobileBy.name("Don’t Allow");
	
	//public getters
	public By getPopupHeader() {
		return popupHeader;
	}
	public By getOKButton() {
		return OKButton;
	}
	public By getDontAllowButton() {
		return dontAllowButton;
	}
	
	//public methods
	public void allowAlert() {
		clickOnElement(OKButton);
	}

}
