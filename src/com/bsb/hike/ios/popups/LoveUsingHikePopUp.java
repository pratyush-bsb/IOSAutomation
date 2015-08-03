package com.bsb.hike.ios.popups;

import org.openqa.selenium.By;

import com.bsb.hike.ios.library.HikeLibrary;

public class LoveUsingHikePopUp extends HikeLibrary {

	public static By LoveUsingHikeTitle_LBL=By.name("Love using Hike?");
	public static By LoveUsingHike_SubTitle_LBL=By.name("Would you like to rate hike? We'd love to know what you think. It won't take long. We promise :)");
	public static By AskMeLater_BTN=By.name("Ask me later");
	public static By RateHike_BTN=By.name("Rate Hike");

	public String getTextLoveUsingHikePopUpTitle()
	{
		return(getTextByName(LoveUsingHikeTitle_LBL));
	}

	public String getTextLoveUsingHikePopUpSubTitle()
	{
		return(getTextByName(LoveUsingHike_SubTitle_LBL));
	}

	public void clickOnAskMeLater_BTN()
	{
		clickOnElement(AskMeLater_BTN);
	}

	public void clickOnRateHike_BTN()
	{
		clickOnElement(RateHike_BTN);
	}
}
