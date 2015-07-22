package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class EnterPasscodeForStealth extends HikeLibrary {

	public EnterPasscodeForStealth() {
		waitForEnterPasswordScreenToLoad();
	}

	private void waitForEnterPasswordScreenToLoad() {

		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 20) {
			try {
				driver.findElement(deletePasscodeButton);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch (Exception eSleep) {}
			}
		}
	}

	//protected identifiers

	protected By header = MobileBy.name("Create Passcode");
	protected By stop = MobileBy.name("Stop");
	protected By deletePasscodeButton = MobileBy.name("ic delete passcode");
	protected By allNumpadButtons = MobileBy.IosUIAutomation(".buttons()");

	//public getters
	public By getHeader() {
		return header;
	}

	public By getStop() {
		return stop;
	}

	public By getDeletePasscodeButton() {
		return deletePasscodeButton;
	}

	public By getAllNumpadButtons() {
		return allNumpadButtons;
	}

	//public methods
	public void enterPasscode () {

		try {
			List<WebElement> numpadKeys = driver.findElements(allNumpadButtons);
			for(int i = 0; i < 4; i++){
				numpadKeys.get(i).click();
			}
		} catch(Exception e) {}
	}

	public void enterIncompletePasscode () {

		try {
			List<WebElement> numpadKeys = driver.findElements(allNumpadButtons);
			for(int i = 0; i < 3; i++){
				numpadKeys.get(i).click();
			}
		} catch(Exception e) {}
	}
	
	public void enterWrongPasscode() {
		
		try {
			List<WebElement> numpadKeys = driver.findElements(allNumpadButtons);
			for(int i = 0; i < 4; i++){
				numpadKeys.get(0).click();
			}
		} catch(Exception e) {}
	}
	
	public HomeScreenMenu cancelEnteringPasscode() {
		clickOnElement(stop);
		return new HomeScreenMenu();
	}

}
