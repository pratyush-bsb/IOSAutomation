package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class CameraScreen extends HikeLibrary {
	
	public CameraScreen() {
		waitForCameraScreenToLoad();
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
	
	//public getters
	public By getCapturePhotoButton() {
		return capturePhotoButton;
	}
	
	public By getUsePhotoButton() {
		return usePhotoButton;
	}

	public GroupProfileScreen clickPhoto(String groupName) {
		
		clickOnElement(capturePhotoButton);
		clickOnElement(usePhotoButton);
		return new GroupProfileScreen(groupName);
	}

}
