package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class ChangePhotoPopup extends AppiumLibrary {

	public static By ChangePhoto_Lbl= By.name("Change Photo?");
	public static By TakePhoto_Btn = By.name("Take Photo");
	public static By ChooseExisting_Btn=By.name("Choose Existing");
	public static By Cancel_Btn=By.name("Cancel");
	
	public String getText_ChangePhotoLbl()
		{
		return(getTextByName(ChangePhoto_Lbl));
		}
	
	public void clickOnTakePhotoBtn()
		{
		clickOnElement(TakePhoto_Btn);
		}
	
	public void clickOnChooseExistingBtn()
		{
		clickOnElement(ChooseExisting_Btn);
		}
	
	public void clickOnCancelBtn()
		{
		clickOnElement(Cancel_Btn);
		}
	
}
