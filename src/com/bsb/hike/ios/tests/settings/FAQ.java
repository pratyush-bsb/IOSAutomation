package com.bsb.hike.ios.tests.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.screens.AccountScreen;
import com.bsb.hike.ios.screens.FAQScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;

public class FAQ extends HikeLibrary {

	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	SettingsScreen settingScreenObj = null;
	AccountScreen accountScreenObj = null;
	FAQScreen faqScreenObj = null;

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
	public void test001_ValidateFAQScreen() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to settings. \n" +
				"2. Validate presence of FAQ." +
				"3. Open FAQ and validate header and back button. \n");

		settingScreenObj = homeScreenMenuObj.clickOnSettings_Lbl();
		faqScreenObj = settingScreenObj.clickOnFAQ();
		Assert.assertEquals(getTextByName(faqScreenObj.getFaqScreenHeader()), "FAQ", "The header does not match the default header");

		Assert.assertTrue(isElementPresent(faqScreenObj.getBackButton()), "Back button is not available in FAQ screen");
	}

	@Test(priority=2)
	public void test002_ScrollToEndOfPage() throws InterruptedException {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go to FAQs page. \n" +
				"2. Check if able to scroll to bottom.\n");

		boolean pageLoaded = false;
		int counter = 0;

		//shift to webview
		Thread.sleep(5000);
		changeToWebView();
		while(!pageLoaded && counter < 10) {
			try {
				driver.getPageSource();
				WebElement allLinks = driver.findElement(By.linkText("back to top"));
				allLinks.click();
				//JavascriptExecutor js = (JavascriptExecutor) driver;
				//js.executeScript("mobile: scrollTo", "element: name('back to top').ref");
				pageLoaded = true;
				Reporter.log("FAQs web view opened.");
			} catch(Exception e) {
				pageLoaded = false;
				try {
					Thread.sleep(1000);
					counter++;
				} catch(Exception es) {}
			}
		}
		changeToNativeView();
	}

	@Test(priority=3)
	public void test003_GoBackToSettings() {

		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Go back to settings. \n" +
				"2. Validate presence of settings page. \n");

		settingScreenObj = faqScreenObj.goBack();

		Assert.assertTrue(isElementPresent(settingScreenObj.getSettingsTitle_LBL()), "Settings screen did not load on clicking on back button in FAQ");

	}
}
