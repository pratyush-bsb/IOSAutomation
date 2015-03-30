package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;
import com.bsb.appium.Library.AppiumLibrary;

public class HomeScreen extends AppiumLibrary{

	public static By conversation_hi_BTN=By.name("conversation hi");	
	public static By no_threads_sticker_icon_Sticker=By.name("no_threads_sticker_icon");
	public static By conversation_compose_BTN=By.name("conversation compose");
	public static By conversation_timeline_BTN=By.name("conversation timeline");

	public static boolean checkPresent_conversation_compose_BTN() throws InterruptedException{
		return isElementPresent(conversation_compose_BTN);
	}
	
	public static boolean checkPresent_conversation_timeline_BTN() throws InterruptedException{
		return isElementPresent(conversation_timeline_BTN);
	}
	
	public static boolean checkPresent_no_threads_sticker_icon_Sticker() throws InterruptedException{
		return isElementPresent(no_threads_sticker_icon_Sticker);
	}
	
	public static void clickOnconversation_compose_BTN()
	{
		clickOnElement(conversation_compose_BTN);
	}
	
	
}
