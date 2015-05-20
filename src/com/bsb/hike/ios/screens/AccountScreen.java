package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class AccountScreen extends HikeLibrary {
	
	public AccountScreen() {
		waitForAccountsPageToLoad();
	}

	private void waitForAccountsPageToLoad() {
		int counter = 5;
		boolean pageLoaded = false;
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(accountScreenHeader);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}
	
	//protected identifiers
	
	protected By accountScreenHeader = MobileBy.name("Account");
	protected String accountScreenHeaderString = "Account";
	protected By backButton = MobileBy.name("Back");
	protected By freeSMSToIndiaSwitch = MobileBy.name("Free SMS to India, Allow sending SMS to India");
	protected By bitSSLSwitch = MobileBy.name("128-bit SSL, Wi-Fi Only");
	protected By profilePicturePrivacySwitch = MobileBy.name("Profile Picture Privacy, Show only to favorites");
	protected By lastSeenSwitch = MobileBy.name("Last Seen, Show last seen");
	protected By allAccountOptions = MobileBy.IosUIAutomation(".tableViews()[0].cells()");
	protected By resetAccountButton = MobileBy.name("Reset Account");
	protected By deleteAccountButton = MobileBy.name("Delete Account");
	protected By confirmResetButton = MobileBy.name("Reset Account");
	protected By cancel = MobileBy.name("Cancel");
	protected By confirmDeleteAccountButton = MobileBy.name("Continue");
	protected By confirmPopup = MobileBy.name("Yes");
	
	protected String deleteAccountTaglineString = "Permanently delete your hike account";
	protected By deleteAccountTagline = MobileBy.name("Permanently delete your hike account");
	
	protected String resetAccountTaglineString = "Clear your messages and profile from this device.";
	protected By resetAccountTagline = MobileBy.IosUIAutomation("Clear your messages and profile from this device.");
	
	public By getAccountScreenHeader() {
		return accountScreenHeader;
	}
	
	public By getDeleteAccountTagline() {
		return deleteAccountTagline;
	}
	
	public String getResetAccountTaglineString() {
		return resetAccountTaglineString;
	}
	
	public By getResetAccountTagline() {
		return resetAccountTagline;
	}
	
	public String getDeleteAccountTaglineString() {
		return deleteAccountTaglineString;
	}
	
	public String getAccountScreenHeaderString() {
		return accountScreenHeaderString;
	}
	
	//public getters

	public By getBackButton() {
		return backButton;
	}

	public By getFreeSMSToIndiaSwitch() {
		return freeSMSToIndiaSwitch;
	}

	public By getBitSSLSwitch() {
		return bitSSLSwitch;
	}

	public By getProfilePicturePrivacySwitch() {
		return profilePicturePrivacySwitch;
	}

	public By getLastSeenSwitch() {
		return lastSeenSwitch;
	}

	public By getAllAccountOptions() {
		return allAccountOptions;
	}

	public By getResetAccountButton() {
		return resetAccountButton;
	}

	public By getDeleteAccountButton() {
		return deleteAccountButton;
	}

	public By getConfirmResetButton() {
		return confirmResetButton;
	}

	public By getCancel() {
		return cancel;
	}

	public By getConfirmDeleteAccountButton() {
		return confirmDeleteAccountButton;
	}
	
	//public methods
	
	public WelcomeScreen resetAccount() {
		clickOnElement(resetAccountButton);
		clickOnElement(confirmResetButton);
		return new WelcomeScreen();
	}
	
	public WelcomeScreen deleteAccount() {
		clickOnElement(deleteAccountButton);
		clickOnElement(confirmDeleteAccountButton);
		clickOnElement(confirmPopup);
		return new WelcomeScreen();
	}
	
	public SettingsScreen goBackToSettingsScreen() {
		
		clickOnElement(backButton);
		return new SettingsScreen();
	}

}
