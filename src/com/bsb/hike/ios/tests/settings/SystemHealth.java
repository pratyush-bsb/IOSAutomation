package com.bsb.hike.ios.tests.settings;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class SystemHealth extends HikeLibrary {

	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	AccountScreen accountScreenObj = null;

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
		//driver.launchApp();
	}

	@AfterClass
	public void tearDown() throws Exception{
		driver.quit();
	}

	@Test(priority=1)
	public void test001_CheckSystemHealth() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings. \n" +
				"2. Validate presence of system health." +
				"3. Open System health and validate elements. \n");

		boolean pageLoaded = false;
		int counter = 0;
		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		
		Assert.assertTrue(isElementPresent(settingScreenObj.getSystemHealth_LBL()), "System Health label is not present in settings tab.");
		clickOnElement(settingScreenObj.getSystemHealth_LBL());
		
		//change to webview
		changeToWebView();
		while(!pageLoaded && counter < 10) {
			try {
				driver.getPageSource();
				driver.findElement(By.linkText("Twitter"));
				//JavascriptExecutor js = (JavascriptExecutor) driver;
				//js.executeScript("mobile: scrollTo", "element: name('Twitter').ref");
				pageLoaded = true;
				Reporter.log("Twitter web view opened");
			} catch(Exception e) {
				try {
					Thread.sleep(1000);
					counter++;
				} catch(Exception ec) {}
				pageLoaded = false;
			}
		}
		changeToNativeView();
		
	}

}
