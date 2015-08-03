package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.AccessPhotosInCamera;

public class CameraScreen extends HikeLibrary {

	public CameraScreen() {
		waitForCameraScreenToLoad();

		//handle if hike notification pops up
		try {
			AccessPhotosInCamera accessPhotosPopup = new AccessPhotosInCamera();
			if(isElementPresent(accessPhotosPopup.getDontAllowButton())) {
				accessPhotosPopup.allowAlert();
			}
		} catch(Exception e) {}
	}

	private void waitForCameraScreenToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(capturePhotoButton);
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
	protected By capturePhotoButton = MobileBy.name("PhotoCapture");
	protected By usePhotoButton = MobileBy.name("Use Photo");
	protected By cancelButton = MobileBy.name("Cancel");
	protected By retakeButton = MobileBy.name("Retake");
	protected By updateButton = MobileBy.name("Update");

	//public getters
	public By getCapturePhotoButton() {
		return capturePhotoButton;
	}

	public By getRetakeButton() {
		return retakeButton;
	}

	public By getCancelButton() {
		return cancelButton;
	}

	public By getUsePhotoButton() {
		return usePhotoButton;
	}

	public void retakePhoto() {
		clickOnElement(retakeButton);

		//handle if hike notification pops up
		try {
			AccessPhotosInCamera accessPhotosPopup = new AccessPhotosInCamera();
			if(isElementPresent(accessPhotosPopup.getDontAllowButton())) {
				accessPhotosPopup.allowAlert();
			}
		} catch(Exception e) {}
	}

	public GroupProfileScreen clickPhoto(String groupName) {

		clickOnElement(capturePhotoButton);
		boolean photoClicked = false;
		while (!photoClicked) {
			try {
				driver.findElement(usePhotoButton);
				photoClicked = true;
			} catch(Exception e) {}
		}
		clickOnElement(usePhotoButton);
		clickOnElement(updateButton);
		return new GroupProfileScreen(groupName);
	}

}
