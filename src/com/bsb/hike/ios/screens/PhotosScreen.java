package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;

public class PhotosScreen extends HikeLibrary {
	
	public PhotosScreen () {
		waitForPhotoScreenToLoad();
	}

	private void waitForPhotoScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(photoScreenHeader);
				pageLoaded = true;
			} catch(Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) {}
			}
		}
	}
	
	//protected identifiers
	
	protected By photoScreenHeader = MobileBy.name("All Photos");
	protected By backButton = MobileBy.name("Back");
	protected By cancelButton = MobileBy.name("Cancel");
	protected By cameraIcon = MobileBy.name("ic_camera_live_camera_grid");
	
	protected By allImagesInGallery = MobileBy.IosUIAutomation(".collectionViews()[0].cells()"); //first one is camera. so fetch data from [1]

	public By getPhotoScreenHeader() {
		return photoScreenHeader;
	}

	public By getBackButton() {
		return backButton;
	}

	public By getCancelButton() {
		return cancelButton;
	}

	public By getCameraIcon() {
		return cameraIcon;
	}

	public By getAllImagesInGallery() {
		return allImagesInGallery;
	}
	
	
	
	//public methods
	public CameraScreen clickOnCameraIconInPhotosScreen() {
		try {
			WebElement cameraIconElement = driver.findElements(allImagesInGallery).get(0);
			new TouchAction(driver).press(cameraIconElement).perform();
			cameraIconElement.click();
		} catch(Exception e) {
			Reporter.log("Not able to click on camera");
		}
		return new CameraScreen();
	}

}
