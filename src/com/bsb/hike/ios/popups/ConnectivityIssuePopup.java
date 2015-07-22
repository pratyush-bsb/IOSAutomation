package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class ConnectivityIssuePopup extends HikeLibrary {
	
	public By connectivityIssuePopup = MobileBy.IosUIAutomation(".elements()[0].scrollViews()[0].staticTexts()[0]");
	public By acceptAlertButton = MobileBy.IosUIAutomation(".elements()[0].collectionViews()[0].cells()[0].buttons()[0]");
	
	//public getters
	public By getConnectivityIssuePopup() {
		return connectivityIssuePopup;
	}
	
	
	public void acceptAlert() {
		clickOnElement(acceptAlertButton);
	}

}
