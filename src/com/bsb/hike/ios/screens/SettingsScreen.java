package com.bsb.hike.ios.screens;

import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.hike.ios.library.HikeLibrary;

public class SettingsScreen extends HikeLibrary{
	
	public SettingsScreen() {
		waitForSettingsPageToLoad();
	}

	private void waitForSettingsPageToLoad() {
		int counter = 5;
		boolean pageLoaded = false;
		
		while(!pageLoaded && counter < 5) {
			try {
				driver.findElement(SettingsTitle_LBL);
				pageLoaded = true;
			} catch (Exception e) {
				counter++;
				try {
					Thread.sleep(1000);
				} catch(Exception eSleep) {}
			}
		}
	}

	protected By Back_BTN = MobileBy.name("Back");
	protected By SettingsTitle_LBL = MobileBy.name("Settings"); //get text by name
	protected String headerLabel = "Settings";
	protected By Notifications_LBL = MobileBy.name("Notifications");
	protected By BlockedList_LBL = MobileBy.name("Blocked List");
	protected By Account_LBL = MobileBy.name("Account");
	protected String accountLabelString = "Account";
	protected By FreeSMS_LBL = MobileBy.name("Free SMS");
	protected By SystemHealth_LBL = MobileBy.name("System Health");
	protected By Media_LBL = MobileBy.name("Media");
	protected By EnterKeyIsSend_LBL = MobileBy.name("Enter Key Is Send");
	//protected By EnterKeyIsSend_BTN = MobileBy.name("settings enter return cell ico");
	protected By FAQ_LBL = MobileBy.name("FAQs");
	protected By Contact_LBL = MobileBy.name("Contact");
	protected By stickerShop = MobileBy.name("Sticker Shop");

	//getter methods

	public By getBack_BTN() {
		return Back_BTN;
	}
	
	public String getAccountLabelText() {
		return accountLabelString;
	}
	
	public String getHeaderValue() {
		return headerLabel;
	}

	public By getSettingsTitle_LBL() {
		return SettingsTitle_LBL;
	}

	public By getNotifications_LBL() {
		return Notifications_LBL;
	}

	public By getBlockedList_LBL() {
		return BlockedList_LBL;
	}

	public By getAccount_LBL() {
		return Account_LBL;
	}

	public By getFreeSMS_LBL() {
		return FreeSMS_LBL;
	}

	public By getSystemHealth_LBL() {
		return SystemHealth_LBL;
	}

	public By getMedia_LBL() {
		return Media_LBL;
	}

	public By getEnterKeyIsSend_LBL() {
		return EnterKeyIsSend_LBL;
	}

	public By getFAQ_LBL() {
		return FAQ_LBL;
	}

	public By getContact_LBL() {
		return Contact_LBL;
	}

	public By getStickerShop() {
		return stickerShop;
	}

	//public methods

	public void clickOnBackIcon()
	{
		clickOnElement(Back_BTN);
	}

	public String getTextSettingsTitle()
	{
		return(getTextByName(SettingsTitle_LBL));
	}

	public NotificationsScreen clickOnNotifications()
	{
		clickOnElement(Notifications_LBL);
		return new NotificationsScreen();
	}

	public BlockedList clickOnBlockList()
	{
		clickOnElement(BlockedList_LBL);
		return new BlockedList();
	}

	public AccountScreen clickOnAccount()
	{
		clickOnElement(Account_LBL);
		return new AccountScreen();
	}

	public void clickOnFreeSMS()
	{
		clickOnElement(FreeSMS_LBL);
	}

	public void clickSystemHealth()
	{
		clickOnElement(SystemHealth_LBL);
	}

	public void clickOnEnterKeyIsSend()
	{
		clickOnElement(EnterKeyIsSend_LBL);
	}


	public void clickOnFAQ()
	{
		clickOnElement(FAQ_LBL);
	}

	public FeedbackMail clickOnContact()
	{
		clickOnElement(Contact_LBL);
		return new FeedbackMail();
	}


	public int getFreeSMSCount() {
		
		String count = "";
		try {
			WebElement freeSmsCell = driver.findElements(FreeSMS_LBL).get(0);
			By freeSmsCount = MobileBy.IosUIAutomation(".staticTexts()[1]");
			WebElement freeSmsText = freeSmsCell.findElement(freeSmsCount);
			count = freeSmsText.getAttribute("name");
		} catch(Exception e) {
			Reporter.log("Not able to fetch sms count");
		}
		return Integer.parseInt(count.split(":")[1].trim());
	}

}

