package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bsb.hike.ios.library.HikeLibrary;

public class LoginAboutYouScreen extends HikeLibrary {
	
	public LoginAboutYouScreen() {
		waitForLoginAboutYouScreenToLoad();
	}

	private void waitForLoginAboutYouScreenToLoad() {
		
		int counter = 0;
		boolean pageLoaded = false;

		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(aboutYouTitle);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By aboutYouTitle = MobileBy.name("About You");
	protected By nextButton = MobileBy.name("Next");
	protected By addPhotoButton = MobileBy.IosUIAutomation(".buttons()[0]");
	protected By editNameField = MobileBy.IosUIAutomation(".textFields()[0]");
	protected By feelingLazyLabel = MobileBy.name("Feeling lazy? We'll do it for you");
	protected By connectWithFacebookButton = MobileBy.name("Connect with Facebook");
	protected By clearTextButton = MobileBy.name("Clear text");
	
	protected By changePhotoActionBar = MobileBy.name("Change Photo?");
	protected By takePhotoButton = MobileBy.name("Take Photo");
	protected By chooseExistingButton = MobileBy.name("Choose Existing");
	protected By cancelPhotoButton = MobileBy.name("Cancel");

	//public getters
	public By getChangePhotoActionBar() {
		return changePhotoActionBar;
	}

	public By getTakePhotoButton() {
		return takePhotoButton;
	}

	public By getChooseExistingButton() {
		return chooseExistingButton;
	}

	public By getCancelPhotoButton() {
		return cancelPhotoButton;
	}

	public By getAboutYouTitle() {
		return aboutYouTitle;
	}

	public By getNextButton() {
		return nextButton;
	}

	public By getAddPhotoButton() {
		return addPhotoButton;
	}

	public By getEditNameField() {
		return editNameField;
	}

	public By getFeelingLazyLabel() {
		return feelingLazyLabel;
	}

	public By getConnectWithFacebookButton() {
		return connectWithFacebookButton;
	}

	public By getClearTextButton() {
		return clearTextButton;
	}

	public String getTextByName_AboutYouTitle()
	{
		return(getTextByName(aboutYouTitle));
	}

	public boolean iSNextButtonEnabled()
	{
		return(isEnabled(nextButton));
	}

	public void clickOnNextBtn()
	{
		clickOnElement(nextButton);
	}

	public boolean isAddPhotoBtnEnabled()
	{
		return(isEnabled(addPhotoButton));
	}

	public PhotosScreen clickOnAddPhotoBtn()
	{
		clickOnElement(addPhotoButton);
		return new PhotosScreen();
	}

	public String getTextByName_NameEnteringEditText()
	{
		return(getTextByName(editNameField));
	}
	public void clickOnNameEnteringScreen()
	{
		clickOnElement(editNameField);
	}
	public void setName(String value)
	{
		enterText(editNameField, value); //name value to be put in a variable
	}

	public String getTextByName_FeelingLazyLbl()
	{
		return(getTextByName(feelingLazyLabel));
	}

	public void clickOnConnectWithFacebookBtn()
	{
		clickOnElement(connectWithFacebookButton);
	}

	public boolean isConnectWithFacebookBtnEnabled()
	{
		return(isEnabled(connectWithFacebookButton));
	}

	public void clickOnClearText_Btn()
	{
		clickOnElement(clearTextButton);
	}
	
	public CameraScreen clickOnTakePhoto() {
		clickOnElement(takePhotoButton);
		return new CameraScreen();
	}
	
	public PhotosScreen clickOnChooseExisting() {
		return new PhotosScreen();
	}
	
	public void clearTypedUsername() {
		try {
			WebElement clearText = driver.findElement(clearTextButton);
			new TouchAction(driver).press(clearText).perform();
		} catch(Exception e) {}
	}
}
