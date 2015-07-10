package com.bsb.hike.ios.screens;

import java.util.Set;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class FAQScreen extends HikeLibrary {
	
	public FAQScreen() {
		waitForFAQPageToLoad();
	}

	private void waitForFAQPageToLoad() {
		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 10) {
			try {
				Set<String> currentContexts = driver.getContextHandles();
				for(String context : currentContexts) {
					if(context.contains("WEBVIEW")) {
						pageLoaded = true;
						break;
					}
				}
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	
	//protected identifiers
	protected By faqScreenHeader = MobileBy.name("FAQ");
	protected By backButton = MobileBy.name("Back");
	
	//public getters
	public By getFaqScreenHeader() {
		return faqScreenHeader;
	}

	public By getBackButton() {
		return backButton;
	}
	
	public SettingsScreen goBack() {
		clickOnElement(backButton);
		return new SettingsScreen();
	}
	
}
