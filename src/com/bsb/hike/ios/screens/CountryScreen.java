package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;

public class CountryScreen extends HikeLibrary {
	
	public CountryScreen() {
		waitForCountryScreenToLoad();
	}

	private void waitForCountryScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(searchField);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By countryScreenHeader = MobileBy.name("Country");
	protected By cancelButton = MobileBy.name("Cancel");
	protected By searchField = MobileBy.IosUIAutomation(".searchBars()[0]");
	
	public By getCountryScreenHeader() {
		return countryScreenHeader;
	}
	
	public By getSearchField() {
		return searchField;
	}
	
	public By getCancelButton() {
		return cancelButton;
	}
	
	public LoginPhoneNumberScreen clickOnCancelButton() {
		
		try {
			WebElement cancelElement = driver.findElement(cancelButton);
			new TouchAction(driver).press(cancelElement).perform();
		} catch(Exception e) {
			Reporter.log("Not able to cancel selection of country codes");
		}
		
		return new LoginPhoneNumberScreen();
	}

}
