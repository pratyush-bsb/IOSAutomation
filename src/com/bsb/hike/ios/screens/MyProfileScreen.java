package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class MyProfileScreen extends AppiumLibrary{

	protected By backButton = MobileBy.name("Back");
	protected By myProfileTitle = By.name("My Profile");
	protected String myProfileTitleString = "My Profile";
	protected By statusButton = MobileBy.name("Status");
	protected By photoButton = MobileBy.name("Photo");
	protected By profileOverflowButton = MobileBy.name("ic post three dot");
	protected By editProfileButton = MobileBy.name("Edit Profile");
	
	
	public By getProfileOverflowButton() {
		return profileOverflowButton;
	}
	public By getEditProfileButton() {
		return editProfileButton;
	}
	public By getBackButton() {
		return backButton;
	}
	public By getMyProfileTitle() {
		return myProfileTitle;
	}
	public String getMyProfileTitleString() {
		return myProfileTitleString;
	}
	public By getStatusButton() {
		return statusButton;
	}
	
	public By getPhotoButton() {
		return photoButton;
	}
	
	public EditProfileScreen goToEditProfile() {
		try {
			if(!isElementPresent(editProfileButton)) {
				clickOnElement(profileOverflowButton);
			}
			clickOnElement(editProfileButton);
		} catch(Exception e) {}
		return new EditProfileScreen();
	}


}
