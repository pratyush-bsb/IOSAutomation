package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ResetHiddenMode;

public class AccountScreen extends HikeLibrary {

	public AccountScreen() {
		waitForAccountsPageToLoad();
	}

	private void waitForAccountsPageToLoad() {
		int counter = 0;
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
	protected By confirmResetButton = MobileBy.IosUIAutomation("UIATarget.localTarget().frontMostApp().actionSheet().collectionViews()[0]");
	protected By cancel = MobileBy.name("Cancel");
	protected By confirmDeleteAccountButton = MobileBy.name("Continue");
	protected By confirmPopup = MobileBy.name("Yes");
	
	protected String accountTabHeader = ".staticTexts()[0]";
	protected String accountTabTagline = ".staticTexts()[1]";

	protected String deleteAccountTaglineString = "Permanently delete your hike account";
	protected By deleteAccountTagline = MobileBy.name("Permanently delete your hike account");

	protected String resetAccountTaglineString = "Clear your messages and profile from this device.";
	protected By resetAccountTagline = MobileBy.name("Clear your messages and profile from this device.");
	
	//stealth settings
	protected By resetHiddenMode = MobileBy.name("Reset Hidden Mode");
	protected By changePasscode = MobileBy.name("Change Passcode");
	protected By hideChatsOnAppExitToggle = MobileBy.name("Hide Chats on App Exit, Hide chats everytime you exit hike");
	
	

	public By getAccountScreenHeader() {
		return accountScreenHeader;
	}
	
	public By getResetHiddenMode() {
		return resetHiddenMode;
	}
	
	public By getChangePasscode() {
		return changePasscode;
	}
	
	public By getHideChatsOnAppExitToggle() {
		return hideChatsOnAppExitToggle;
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
		try {
			WebElement resetAccount = driver.findElement(resetAccountButton);
			new TouchAction(driver).press(resetAccount).perform();

			WebElement confirmResetAccount = driver.findElement(confirmResetButton);
			new TouchAction(driver).press(confirmResetAccount).perform();

		} catch(Exception e) {
			Reporter.log("Reset account button click did not work conventionally!");
		}
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
	
	public void toggleFreeSMS() {
		clickOnElement(freeSMSToIndiaSwitch);
	}
	
	public void toggle128bitSSL() {
		clickOnElement(bitSSLSwitch);
	}
	
	public EnterPasscodeForStealth clickOnChangePasscode() {
		clickOnElement(changePasscode);
		return new EnterPasscodeForStealth();
	}

	public void changeStealthPasscode() {
		
		try {
			EnterPasscodeForStealth enterStealthPasscodeObj = clickOnChangePasscode();
			enterStealthPasscodeObj.enterPasscode();
			enterStealthPasscodeObj.enterPasscode();
			enterStealthPasscodeObj.enterPasscode();
		} catch(Exception e) {}
		
	}
	
	public ResetHiddenMode clickOnResetHiddenMode() {
		
		clickOnElement(resetHiddenMode);
		return new ResetHiddenMode();
	}
	
	public HomeScreenMenu resetHiddenMode() {
		
		ResetHiddenMode resetHiddenModePopup = clickOnResetHiddenMode();
		resetHiddenModePopup.confirmResetHiddenMode();
		return new HomeScreenMenu();
	}
	
}
