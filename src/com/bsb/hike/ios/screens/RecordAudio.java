package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class RecordAudio extends HikeLibrary {
	
	public RecordAudio() {
		waitForRecordAudioToLoad();
	}

	private void waitForRecordAudioToLoad() {

		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(recordButton);
			} catch(Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) {}
			}
		}
	}
	
	protected By recordButton = MobileBy.name("recordButton");
	protected By sendButton = MobileBy.name("Send");
	
	public void recordAudio() {
		clickOnElement(recordButton);
		clickOnElement(recordButton);
		if(isElementPresent(sendButton)) {
			clickOnElement(sendButton);
		} else {
			try {
				Thread.sleep(2000);
				clickOnElement(sendButton);
			} catch(Exception e) {}
		}
	}

}
