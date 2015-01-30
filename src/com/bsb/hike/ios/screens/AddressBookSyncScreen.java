package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class AddressBookSyncScreen extends AppiumLibrary{

	public static By AddressBookSync_Lbl=By.name("Address Book Sync");
	public static By SyncYourAddressbook_Lbl=By.name("Sync your Address Book to find  your friends on hike.");
	public static By Continue_Btn=By.name("Continue");
	
	public String getTextAddressbookSyncLbl()
		{
		return(getText(AddressBookSync_Lbl));
		}
	
	public String getTextSyncYourAddressbookLbl()
		{
		return(getText(SyncYourAddressbook_Lbl));
		}
	
	public void clickOnContinue()
		{
		clickOnElement(Continue_Btn);
		}
	
}
