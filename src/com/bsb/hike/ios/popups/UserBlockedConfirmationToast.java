package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class UserBlockedConfirmationToast extends HikeLibrary {
	
	protected By unblockButton = MobileBy.name("Unblock");
	protected By blockedLogo = MobileBy.name("hudIconBlock");
	protected By blockedText = MobileBy.IosUIAutomation(".staticTexts()[0]");
	
	//public getters
	public By getUnblockButton() {
		return unblockButton;
	}
	public By getBlockedLogo() {
		return blockedLogo;
	}
	public By getBlockedText() {
		return blockedText;
	}
	
	//public methods
	public void unblockUser() {
		clickOnElement(unblockButton);
	}

}
