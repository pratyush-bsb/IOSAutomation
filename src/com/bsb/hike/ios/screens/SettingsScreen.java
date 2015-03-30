package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class SettingsScreen extends AppiumLibrary{

	public static By Back_BTN = By.name("Back");
	public static By SettingsTitle_LBL=By.name("Settings");
	public static By Notifications_LBL=By.name("Notifications");
	public static By Notifications_BTN=By.name("settings notifications cell ic");
	public static By BlockedList_LBL=By.name("Blocked List");
	public static By BlockedList_BTN=By.name("settings block cell icon");
	public static By Account_LBL=By.name("Account");
	public static By Accountt_BTN=By.name("settings account cell icon");
	public static By FreeSMS_LBL=By.name("Free SMS");
	public static By FreeSMS_BTN=By.name("settings sms cell icon");
//  public static By FreeSMSCount_LBL=By.name("Free SMS: 0");   This ID needs to be static
	public static By SystemHealth_LBL=By.name("System Health");
	public static By SystemHealth_BTN=By.name("settings system health cell ic");
	public static By AutoSaveMedia_LBL=By.name("Auto Save Media");
	public static By AutoSaveMedia_BTN=By.name("settings autodownload cell ico");
//	public static By AutoSaveMediaSwitch_BTN=By.name("Auto Save Media");  Need to get its ID change as it is same as LBL/ Value of this element is 1 when enabled 0 otherwise
	public static By EnterKeyIsSend_LBL=By.name("Enter Key Is Send");
	public static By EnterKeyIsSend_BTN=By.name("ettings enter return cell ico");
//	public static By EnterKeyIsSendSwitch_BTN=By.name("Enter Key Is Send"); Need to get its ID change as it is same as LBL/ Value of this element is 1 when enabled 0 otherwise
	public static By FAQ_LBL=By.name("FAQs");
	public static By FAQ_BTN=By.name("settings faq cell icon");
	public static By Contact_LBL=By.name("Contact");
	public static By Contact_BTN=By.name("settings contact us cell icon");
	
	
		public void clickOnBackIcon()
		{
		clickOnElement(Back_BTN);
		}
	
		public String getTextSettingsTitle()
		{
		return(getText(SettingsTitle_LBL));
		}
	
		public void clickOnNotifications()
		{
		clickOnElement(Notifications_LBL);
		}
	
		public boolean isNotificationButtonPresent() throws InterruptedException
		{
		return(isElementPresent(Notifications_BTN));
		}
	
		public void clickOnBlockList()
		{
		 clickOnElement(BlockedList_LBL);
		}
	
		public boolean isBlockListButtonPresent() throws InterruptedException
		{
		 return(isElementPresent(BlockedList_BTN));
		}
	
		public void clickOnAccount()
		{
		 clickOnElement(Account_LBL);
		}

		public boolean isAccountButtonPresent() throws InterruptedException
		{
		return(isElementPresent(Accountt_BTN));
		}
	
		public void clickOnFreeSMS()
		{
		 clickOnElement(FreeSMS_LBL);
		}

		public boolean isFreeSMSButtonPresent() throws InterruptedException
		{
		return(isElementPresent(FreeSMS_BTN));
		}
	
		public void clickSystemHealth()
		{
			clickOnElement(SystemHealth_LBL);
		}

		public boolean isSystemHealthButtonPresent() throws InterruptedException
		{
		 return(isElementPresent(SystemHealth_BTN));
		}
	
		public void clickOnAutoSaveMedia()
		{
		 clickOnElement(AutoSaveMedia_LBL);
		}
	
		public boolean isAutoSaveMediaButtonPresent() throws InterruptedException
		{
		 return(isElementPresent(AutoSaveMedia_BTN));
		}
		
		public void clickOnEnterKeyIsSend()
		{
		 clickOnElement(EnterKeyIsSend_LBL);
		}
	
		public boolean isEnterKeyIsSendButtonPresent() throws InterruptedException
		{
		 return(isElementPresent(EnterKeyIsSend_BTN));
		}
		
		public void clickOnFAQ()
		{
		 clickOnElement(FAQ_LBL);
		}
	
		public boolean isFAQButtonPresent() throws InterruptedException
		{
		 return(isElementPresent(FAQ_BTN));
		}
	
		public void clickOnContact()
		{
		clickOnElement(Contact_LBL);
		}

		public boolean isContactButtonPresent() throws InterruptedException
		{
		return(isElementPresent(Contact_BTN));
		}
		
}

