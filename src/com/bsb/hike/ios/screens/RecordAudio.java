package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

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
				pageLoaded = true;
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
		
		try {
			WebElement recordButtonElement = driver.findElement(recordButton);
			new TouchAction(driver).press(recordButtonElement).perform();
			Thread.sleep(2000);
			new TouchAction(driver).press(recordButtonElement).perform();
			if(isElementPresent(sendButton)) {
				WebElement sendButtonElement = driver.findElement(sendButton);
				new TouchAction(driver).press(sendButtonElement).perform();
			} else {
					Thread.sleep(2000);
					WebElement sendButtonElement = driver.findElement(sendButton);
					new TouchAction(driver).press(sendButtonElement).perform();
			}
		} catch (Exception e) {
			Reporter.log("Not able to record audio");
		}
	}

}
