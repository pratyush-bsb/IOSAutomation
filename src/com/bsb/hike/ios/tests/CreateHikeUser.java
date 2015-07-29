package com.bsb.hike.ios.tests;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ChooseYourProfilePicturePopUp_NameEnteringScreen;
import com.bsb.hike.ios.popups.ConfirmYourNumberPopUpScreen;
import com.bsb.hike.ios.screens.EditProfileScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.MyProfileScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.WelcomeScreen;

public class CreateHikeUser extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenObj = new HomeScreenMenu();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
		//driver.launchApp();
	}

	@Test
	public void test001() throws Exception{
		//Thread.sleep(1000*10);
		setDEFAULT_MSISDN();
		setPin();
		WelcomeScreen welcomeScreenObj = new WelcomeScreen();
		singleTapWithTwoFingers(welcomeScreenObj.getSignUpLogo());
		welcomeScreenObj.selectStagingEnvironment();
		LoginPhoneNumberScreen loginPhoneNumberObj = welcomeScreenObj.clickOnGetStartedBTN();
		
		loginPhoneNumberObj.clickOnPhoneNumberField();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		PinEnteringScreen pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		
		pinEnteringScreenObj.clickOnPinTextField();
		LoginAboutYouScreen aboutYouScreenObj = pinEnteringScreenObj.setPin(DEFAULT_PIN);
		
		aboutYouScreenObj.clickOnNameEnteringScreen();
		aboutYouScreenObj.setName(DEFAULT_NAME);
		aboutYouScreenObj.clickOnNextBtn();
		
		PushNotificationsScreen pushNotificationScreenObj = ChooseYourProfilePicturePopUp_NameEnteringScreen.clickOnNoBtn();
		
		pushNotificationScreenObj.clickOnContinue_Btn();
		try {
			driver.switchTo().alert().accept();
			pushNotificationScreenObj.clickOnContinue_Btn();
		} catch(Exception e) {
			Reporter.log("Sync address book button did not come");
		}
		
	}
	
	@Test
	public void test002() throws InterruptedException{
		String name="HikeIosUserName";
		homeScreenObj.clickOnOverflow();
		homeScreenObj.clickOnProfile_Lbl();
		
		Assert.assertEquals(MyProfileScreen.getText_MyProfileTitle(), MyProfileScreen.MyProfileTitle_LBL_Txt);
		MyProfileScreen.clickOnEdit_BTN();
		
		Assert.assertEquals(EditProfileScreen.getText_EditProfileScreenTitle_LBL(), EditProfileScreen.EditProfileScreenTitle_LBL_Txt);
		Assert.assertEquals(EditProfileScreen.getText_Back_BTN(), EditProfileScreen.Back_BTN_Txt);
		
		EditProfileScreen.setName(name);
		EditProfileScreen.clickOnDone_BTN();
		
		//wait for keyboard to hide
		while(isKeyboardVisible()) {
			Thread.sleep(100);
		}
		
		Assert.assertFalse(EditProfileScreen.getState_Done_BTN(), "The done button is enable even after clicking on 'Done'");
		//go to home
		goToHome();
	}

	@AfterClass
	public void tearDown() throws Exception{
			 driver.quit();
			 
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {

		if(result.getStatus() == ITestResult.FAILURE) {

			//it is a failure. create screenshot and push according to file name and test name

			try {
				final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
				System.setProperty(ESCAPE_PROPERTY, "false");
				String className = 	result.getTestClass().getName();
				String testName = result.getMethod().getMethodName();
				String failureScreenshotName = className+"_"+testName+".png";
				File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
				FileUtils.copyFile(file, new File("Screenshot/"+failureScreenshotName));
				Reporter.log("<a href=\"Screenshot/" + failureScreenshotName + " > Test123 </a>");
				Reporter.setCurrentTestResult(null); 
			} catch(Exception e) {
				Reporter.log("Not able to store screenshot");
			}

		}
	}

}
