package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class UnableToGetPinError extends HikeLibrary {
	
	protected By errorBody = MobileBy.name("Unable to get PIN. Please try again later.");
	protected By okButton = MobileBy.name("OK");
	
	//public getters
	public By getErrorBody() {
		return errorBody;
	}
	public By getOkButton() {
		return okButton;
	}
	
	public boolean waitForPinErrorAndDismiss() {
		
		int counter = 0;
		boolean error = false;
		
		try {
			while(!error && counter < 90) {
				driver.findElement(getErrorBody());
				error = true;
				WebElement okButton = driver.findElement(getOkButton());
				okButton.click();
			}
		} catch(Exception e) {}
		
		return error;
	}

}
