package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class HomeScreenMenu extends AppiumLibrary{

	public static By Overflow_Option=By.name("conversation overflow");
	public static By NewGroup_Lbl=By.name("public static By");
	public static By Favorites_Lbl=By.name("Favorites");
	public static By Rewards_Lbl=By.name("Rewards");
	public static By InviteFriends_Lbl=By.name("Invite Friends");
	public static By Profile_Lbl=By.name("Profile");
	public static By Settings_Lbl=By.name("Settings");
	public static By Status_Lbl=By.name("Status");
	
	
	public static void clickOnOverflow(){
		clickOnElement(Overflow_Option);
	}
	
	public void clickOnNewGroup_Lbl()
		{
		clickOnElement(NewGroup_Lbl);
		}
	
	public void clickOnFavorites_Lbl()
		{
	clickOnElement(Favorites_Lbl);
		}
	
	public void clickOnRewards_Lbl()
		{
	clickOnElement(Rewards_Lbl);
		}
	
	public void clickOnInviteFriends_Lbl()
		{
	clickOnElement(InviteFriends_Lbl);
		}
	
	public static void clickOnProfile_Lbl()
		{
	clickOnElement(Profile_Lbl);
		}
	
	public void clickOnSettings_Lbl()
		{
	clickOnElement(Settings_Lbl);
		}
	
	public void clickOnStatus_Lbl()
		{
	clickOnElement(Status_Lbl);
		}
}
