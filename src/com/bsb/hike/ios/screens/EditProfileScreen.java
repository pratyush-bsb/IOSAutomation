package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class EditProfileScreen extends AppiumLibrary {

	protected By backButton = MobileBy.name("Back");
	protected By doneButton = MobileBy.name("Done");
	protected By nameLabel = MobileBy.IosUIAutomation(".tableViews()[0].cells()[0].textFields()");
	protected By mobileNumberLabel = MobileBy.name("Mobile");
	protected By emailLabel = MobileBy.name("E-mail");
	protected By editPictureLabel = MobileBy.name("Update Profile Picture");
	protected By maleButton = MobileBy.name("Male");
	protected By femaleButton = MobileBy.name("Female");


	public By getBackButton() {
		return backButton;
	}

	public By getDoneButton() {
		return doneButton;
	}

	public By getNameLabel() {
		return nameLabel;
	}

	public By getMobileNumberLabel() {
		return mobileNumberLabel;
	}

	public By getEmailLabel() {
		return emailLabel;
	}

	public By getEditPictureLabel() {
		return editPictureLabel;
	}

	public By getMaleButton() {
		return maleButton;
	}

	public By getFemaleButton() {
		return femaleButton;
	}

	public  void setName(String value)	
	{

		enterTextWithClear(nameLabel, value);
	}

	public  void clickOnDone_BTN()
	{
		clickOnElement(doneButton);
	}
	public  boolean getState_Done_BTN(){
		return isEnabled(doneButton);
	}


	public void clickOnBack_BTN()
	{
		clickOnElement(backButton);
	}

	public  String getText_Back_BTN()
	{
		return(getTextByName(backButton));				
	}


	public String getTextName_LBL()
	{
		return(getTextByName(nameLabel));
	}

	public String getTextMobile_LBL()
	{
		return(getTextByName(mobileNumberLabel));
	}

	public String getTextEmail_LBL()
	{
		return(getTextByName(emailLabel));
	}

	public void clickOnEditPicture_BTN()
	{
		clickOnElement(editPictureLabel);
	}

	public void clickOnMale_BTN()
	{
		clickOnElement(maleButton);
	}

	public void clickOnFemale_BTN()
	{	
		clickOnElement(femaleButton);	
	}
}
