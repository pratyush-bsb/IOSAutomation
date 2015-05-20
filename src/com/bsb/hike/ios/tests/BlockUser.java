package com.bsb.hike.ios.tests;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.ChatThreadScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.RewardsScreen;

public class BlockUser extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
		//driver.launchApp();
	}

	@AfterClass
	public void tearDown() throws Exception{
		driver.quit();

	}

	@Test
	public void test001_BlockBotUser() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+ " : 1. Search if natasha bot thread is there. \n" +
				"2. If bot thread is not there, trigger from rewards. \n" +
				"3. Block natasha bot and verify from blocked list.");
		
		ChatThreadScreen chatScreenObj = homeScreenMenuObj.clickOnNatashaBot();
		
		if(chatScreenObj == null) {
			//natasha bot not in messages. try to activate from settings
			RewardsScreen rewardsScreenObj = homeScreenMenuObj.clickOnRewards_Lbl();
			rewardsScreenObj.activateNatashaBot();
		}
		
	}
	
}
