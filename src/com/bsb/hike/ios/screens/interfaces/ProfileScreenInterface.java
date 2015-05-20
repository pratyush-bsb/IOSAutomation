package com.bsb.hike.ios.screens.interfaces;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

public interface ProfileScreenInterface {

	By backButtonProfileScreen = MobileBy.name("Back");
	String profileScreenHeaderString = "Profile";
	By userNameBy = MobileBy.IosUIAutomation(".tableViews()[0].staticTexts()[0]"); //get text by name
	By onHikeSince = MobileBy.IosUIAutomation(".tableViews()[0].staticTexts()[1]"); //get text by value
	By inviteToHike = MobileBy.name("Invite to hike");
	
}
