package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class TimelineScreen extends AppiumLibrary {

	public static By Back_BTN=By.name("Back");
	// public static By TimelineTitle_LBL=By.name("All Conversations");  **Have to get the ID changed for this
	public static By Mute_BTN=By.name("unmute timeline");
	public static By PostStatus_BTN=By.name("Post Status");
	public static By ViewFavorites_BTN=By.name("View favorites");
	
	
	public void clickOnBack_BTN()
		{
		clickOnElement(Back_BTN);
		}
	
	public void clickOnMute_BTN()
		{
		clickOnElement(Mute_BTN);
		}
	
	public void clickOnPostStatus_BTN()
		{
		clickOnElement(PostStatus_BTN);
		}
	
	public void clickOnViewFavorites_BTN()
		{
		clickOnElement(ViewFavorites_BTN);
		}
}
