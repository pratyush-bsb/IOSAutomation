package com.bsb.hike.ios.popups;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.HomeScreenMenu;

public class ResetHiddenMode extends HikeLibrary {
	
	public ResetHiddenMode() {
		waitForResetHiddenModeToLoad();
	}

	private void waitForResetHiddenModeToLoad() {
		
		boolean pageLoaded = false;
		int counter = 0; 

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(resetHiddenModeHeader);
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
	
	protected By resetHiddenModeHeader = MobileBy.name("Reset Hidden Mode");
	protected By confirmReset = MobileBy.name("OK");
	protected By cancelReset = MobileBy.name("Cancel");
	protected By resetHiddenModeTagline = MobileBy.name("Hidden mode will be reset in two minutes. All hidden chats will be deleted. Continue?");
	
	//public getters
	public By getResetHiddenModeHeader() {
		return resetHiddenModeHeader;
	}

	public By getConfirmReset() {
		return confirmReset;
	}

	public By getCancelReset() {
		return cancelReset;
	}

	public By getResetHiddenModeTagline() {
		return resetHiddenModeTagline;
	}
	
	public HomeScreenMenu confirmResetHiddenMode() {
		
		clickOnElement(confirmReset);
		return new HomeScreenMenu();
	}

}
