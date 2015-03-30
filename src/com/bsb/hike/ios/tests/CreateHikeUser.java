package com.bsb.hike.ios.tests;

import com.bsb.appium.Library.AppiumLibrary;
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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateHikeUser extends AppiumLibrary{
	AppiumCapabilities appium = new AppiumCapabilities();

	@BeforeTest
	public void setUp() throws Exception{
		appium.setUp();
	}
	
	@Test
	public void test001() throws Exception{
		Thread.sleep(1000*10);
		HikeLibrary.setDEFAULT_MSISDN();
		HikeLibrary.setPin();
		//doubleTapWithTwoFingers(driver.findElement(By.name("signupLogo")));
		WelcomeScreen.clickOnGetStartedBTN();		
		LoginPhoneNumberScreen.clickOnPhoneNumberField();
		LoginPhoneNumberScreen.setText_PhoneNumberField(HikeLibrary.getDEFAULT_MSISDN_Create());
		LoginPhoneNumberScreen.clickOnNextBtn();
		ConfirmYourNumberPopUpScreen.clickOnConfirmBtn();
		Thread.sleep(6000);
		PinEnteringScreen.clickOnPinTextField();
		PinEnteringScreen.setPin(HikeLibrary.DEFAULT_PIN);
		Thread.sleep(5000);
		LoginAboutYouScreen.clickOnNameEnteringScreen();
		LoginAboutYouScreen.setName(HikeLibrary.DEFAULT_NAME);
		LoginAboutYouScreen.clickOnNextBtn();
		Thread.sleep(2000);
		ChooseYourProfilePicturePopUp_NameEnteringScreen.clickOnNoBtn();
		Thread.sleep(5000);
		PushNotificationsScreen.clickOnContinue_Btn();
		Thread.sleep(5000);
	}
	
	@Test
	public void test002() throws InterruptedException{
		String name="HikeIosUserName";
		HomeScreenMenu.clickOnOverflow();
		HomeScreenMenu.clickOnProfile_Lbl();
		Thread.sleep(3000);
		Assert.assertTrue(MyProfileScreen.checkPresent_MyProfileTitle_LBL(),"Profile screen label not get displayed");
		//System.out.println(MyProfileScreen.getText_MyProfileTitle().contains("My Profile"));
		MyProfileScreen.clickOnEdit_BTN();
		Assert.assertTrue(EditProfileScreen.checkPresent_EditProfileScreenTitle_LBL(),"Edit profile screen label not get displayed");
		Assert.assertFalse(EditProfileScreen.getState_Done_BTN(),"Done button is enabled by default");
		EditProfileScreen.setName(name);
		EditProfileScreen.clickOnDone_BTN();
		Thread.sleep(5000);
		Assert.assertFalse(EditProfileScreen.getState_Done_BTN(),"Done button not get disabled");
	}

	@AfterClass
	public void tearDown() throws Exception{
		//	 driver.quit();

	}
}
