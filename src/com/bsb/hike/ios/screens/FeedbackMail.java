package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class FeedbackMail extends HikeLibrary {
	
	public FeedbackMail() {
		waitForFeedbackMailToLoad();
	}
	
	private void waitForFeedbackMailToLoad() {
		
		int counter = 0;
		boolean pageLoaded = false;
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(header);
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
	
	protected By header = MobileBy.name("Feedback on iOS");
	protected By cancel = MobileBy.name("Cancel");
	protected By send = MobileBy.name("Send");
	protected By messageBody = MobileBy.name("Message body");
	protected By toField = MobileBy.name("toField"); //get text by value
	protected By ccField = MobileBy.name("ccField"); //get text by value
	protected By subjectField = MobileBy.name("subjectField"); //get text by value
	protected String toFieldPrefilled = "support@hike.in";
	protected String subjectFieldPrefilled = "Feedback on iOS";
	
	public By getHeader() {
		return header;
	}
	
	public String getToFieldPrefilled () {
		return toFieldPrefilled;
	}
	
	public String getSubjectFieldPrefilled() {
		return subjectFieldPrefilled;
	}

	public By getCancel() {
		return cancel;
	}

	public By getSend() {
		return send;
	}

	public By getToField() {
		return toField;
	}

	public By getCcField() {
		return ccField;
	}

	public By getSubjectField() {
		return subjectField;
	}

	public String getMessageBody() {
		
		String message = getTextByValue(messageBody);
		return message;
	}
	
	public void cancelMail() {
		
		clickOnElement(cancel);
	}
}
