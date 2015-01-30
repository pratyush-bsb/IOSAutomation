package com.bsb.hike.ios.screens;

import org.openqa.selenium.By;

import com.bsb.appium.Library.AppiumLibrary;

public class ConversationListEmptyState extends AppiumLibrary{

	public static By MakeMyDay_Lbl=By.name("Make my day... plzzz");
	public static By StickersHaveFeelings_Lbl=By.name("Stickers have feelings too, they like to be shared.");
	public static By Tap_Lbl=By.name("Tap");
	public static By ToSendASticker_Lbl=By.name("to send a sticker");
	public static By Hi_Btn=By.name("conversation hi");
	public static By Compose_Btn=By.name("conversation compose");
	public static By Timeline_Btn=By.name("conversation timeline");
	public static By OverflowConversation_Btn=By.name("conversation overflow");
	
	
	
	public String getText_MakeMyDayLbl()
	{
	return(getText(MakeMyDay_Lbl));
	}
	
	public String getText_StickersHaveFeeling_Lbl()
	{
	return(getText(StickersHaveFeelings_Lbl));
	}
	
	public String getText_Tap_Lbl()
	{
	return(getText(Tap_Lbl));
	}
	
	public String getText_ToSendASticker_Lbl()
	{
	return(getText(ToSendASticker_Lbl));
	}
	
	public void clickOnHi_Btn()
	{
		clickOnElement(Hi_Btn);
	}
	
	public void clickOnCompose_Btn()
	{
		clickOnElement(Compose_Btn);
	}
	
	public void clickOnTimeline_Btn()
	{
		clickOnElement(Timeline_Btn);
	}
	
	public void clickOnOverFlowMenuConversation_Btn()
	{
		clickOnElement(OverflowConversation_Btn);
	}
}
