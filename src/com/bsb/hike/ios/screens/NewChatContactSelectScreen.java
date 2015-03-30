package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class NewChatContactSelectScreen extends AppiumLibrary{

	public static By Select_a_Contact_Txt=By.name("Select a Contact");	
	public static By PEOPLE_ON_HIKE_Txt=By.name("PEOPLE ON HIKE");
	public static By Search_Bar=By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIASearchBar[1]");
	public static By Search_Text_Bar = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIASearchBar[1]");
	public static By Clear_text_Btn=By.name("Clear text");
	public static By Tap_to_chat_Txt=By.name("Tap to chat");
	public static By Cancel_Btn=By.name("Cancel");
	
	public static boolean Select_a_Contact_Txt_IsExist() throws InterruptedException{
		return isElementPresent(Select_a_Contact_Txt);
	}
	public static boolean PEOPLE_ON_HIKE_Txt_IsExist() throws InterruptedException{
		return isElementPresent(PEOPLE_ON_HIKE_Txt);
	}
	public static boolean Search_Bar_IsExist() throws InterruptedException{
		return isElementPresent(Search_Bar);
	}
	public static boolean Clear_text_Btn_IsExist() throws InterruptedException{
		return isElementPresent(Clear_text_Btn);
	}
	public static boolean Tap_to_chat_Txt_IsExist() throws InterruptedException{
		return isElementPresent(Tap_to_chat_Txt);
	}
	public static boolean checkPresent_Cancel_Btn_IsExist() throws InterruptedException{
		return isElementPresent(Cancel_Btn);
	}
	public static void clickOnSearch_Bar()
	{
		clickOnElement(Search_Bar);
	}
	public static void setSelfChat(String value)
	{
		enterText(Search_Text_Bar, value);
	}
	public static void clickOnClear_text_Btn()
	{
		clickOnElement(Clear_text_Btn);
	}
	public static void clickOnTap_to_chat_Txt()
	{
		clickOnElement(Tap_to_chat_Txt);
	}
	public static void clickOnCancel_Btn()
	{
		clickOnElement(Cancel_Btn);
	}
}
