package com.bsb.hike.ios.tests.signUpFlow;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bsb.hike.ios.base.AppiumCapabilities;
import com.bsb.hike.ios.library.HikeLibrary;
import com.bsb.hike.ios.popups.ChooseYourProfilePicturePopUp_NameEnteringScreen;
import com.bsb.hike.ios.popups.ConfirmYourNumberPopUpScreen;
import com.bsb.hike.ios.popups.InvalidPinEnteredPopup;
import com.bsb.hike.ios.popups.UnableToGetPinError;
import com.bsb.hike.ios.screens.CameraScreen;
import com.bsb.hike.ios.screens.CountryScreen;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.LoginAboutYouScreen;
import com.bsb.hike.ios.screens.LoginPhoneNumberScreen;
import com.bsb.hike.ios.screens.PhotosScreen;
import com.bsb.hike.ios.screens.PinEnteringScreen;
import com.bsb.hike.ios.screens.PushNotificationsScreen;
import com.bsb.hike.ios.screens.TermsAndConditions;
import com.bsb.hike.ios.screens.WelcomeScreen;

public class SignUpFlow extends HikeLibrary {
	
	AppiumCapabilities appium = new AppiumCapabilities();
	HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
	WelcomeScreen welcomeScreenObj = new WelcomeScreen();
	LoginPhoneNumberScreen loginPhoneNumberObj  = new LoginPhoneNumberScreen();
	PinEnteringScreen pinEnteringScreenObj = new PinEnteringScreen();
	LoginAboutYouScreen aboutYouScreenObj = new LoginAboutYouScreen();

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
	public void test001_uninstallApp() throws MalformedURLException {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Uninstall the app if it's installed. \n" +
				"2. Verify that it is uninstalled properly. \n");
		
		boolean appInstalled = driver.isAppInstalled(bundleId);
		if(appInstalled){
			driver.removeApp(bundleId);
		}
	}
	
	@Test(priority=2)
	public void test002_installApp() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Install the app if it isn't installed. \n" +
				"2. Verify that it is installed properly. \n");
		
		try {
			appium.setUp();
		} catch(Exception e) {
			Reporter.log("Unable to install app. Exiting suite");
		}
		
		Assert.assertTrue(driver.isAppInstalled(bundleId), "App was not installed even after trying to install it.");
	}
	
	@Test(priority=3)
	public void test003_hikeLogoPresence() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Launch the app. \n" +
				"2. Ensure that hike logo is there. \n");
		
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getSignUpLogo()), "Hike logo not visible in welcome screen");
	}
	
	@Test(priority=4)
	public void test004_hikeWelcomePage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Verify different elements on hike welcome page. \n");
		
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getSignUpLogo()), "Hike logo not visible in welcome screen");
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getMadeWithLoveBy()), "'Made with love in India' text not visible in welcome screen");
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getTermsAndConditions()), "'Terms and Conditions' text not visible in welcome screen");
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getGetStartedButton()), "'Get Started' button not visible in welcome screen");
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getMadeWithLoveBy()), "'Made with love in India' text not visible in welcome screen");
		
	}
	
	@Test(priority=5)
	public void test005_termsAndConditions() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Tap on terms and conditions. \n" +
				"2. Ensure the header and back button in terms and conditions page. \n");
		
		TermsAndConditions termsScreenObj = welcomeScreenObj.clickOnTermsAndConditions();
		
		Assert.assertTrue(isElementPresent(termsScreenObj.getBackButton()), "Back button not available in terms and conditions page");
		Assert.assertTrue(isElementPresent(termsScreenObj.getTermsHeader()), "'Terms and Conditions' header not available in terms and conditions page");
		
		clickOnElement(termsScreenObj.getBackButton());
		Assert.assertTrue(isElementPresent(welcomeScreenObj.getSignUpLogo()), "Back button in 'Terms and Conditions' page did not take to welcome screen");
		
	}
	
	@Test(priority=6)
	public void test006_signUpOnWifi() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on 'get started' button on welcome screen. \n" +
				"2. Assert the validity of elements on phone number screen. \n");
		
		singleTapWithTwoFingers(welcomeScreenObj.getSignUpLogo());
		welcomeScreenObj.selectStagingEnvironment();
		loginPhoneNumberObj = welcomeScreenObj.clickOnGetStartedBTN();
		
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getPhoneNumberHeader()), "Phone number screen header did not come as 'Phone Number'");
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getCountryCodeButton()), "Default country code did not come as '+91 India'");
		Assert.assertTrue(isKeyboardVisible(), "Numpad not visible by default");
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getWhatsYourNumberLabel()), "'What's you number?' did not come on the screen");
		
	}
	
	@Test(priority=7)
	public void test007_countrySelection() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on 'country code' button on phone number screen. \n" +
				"2. Assert the presence of keyboard and cancel button. \n");
		
		CountryScreen countryObj = loginPhoneNumberObj.clickOnCountryCode();
		
		Assert.assertTrue(isKeyboardVisible(), "Keyboard is not visible by default");
		
		Assert.assertTrue(isElementPresent(countryObj.getCancelButton()), "The cancel button in country screen did not come");
		Assert.assertTrue(isElementPresent(countryObj.getSearchField()), "The search field in country screen did not come");
		
		hideHikeKeyboard();
		Assert.assertTrue(isElementPresent(countryObj.getCountryScreenHeader()), "The header for country screen did not come as 'Country'");
		loginPhoneNumberObj = countryObj.clickOnCancelButton();
		
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getPhoneNumberHeader()), "Phone number screen did not come after cancelling country code selection");
		
	}
	
	@Test
	public void test008_scrollTheCountryList() {
		
	}
	
	@Test(priority=9)
	public void test009_enterNumber() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Start entering number in phone number screen. \n" +
				"2. Assert validity of buttons enabled. \n");
		
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		
		Assert.assertTrue(isElementEnabled(loginPhoneNumberObj.getNextButton()), "'Next' button did not get enabled after typing number in text field");
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getClearTextButton()), "'Clear Text' button did not get enabled after typing number in text field");
		
	}
	
	@Test(priority=10)
	public void test010_removeEnteredNumber() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Remove the entered number. \n" +
				"2. Assert validity of buttons disabled. \n");
		
		loginPhoneNumberObj.removeEnteredText();
		
		Assert.assertFalse(isElementEnabled(loginPhoneNumberObj.getNextButton()), "'Next' button did not get disabled after removing number in text field");
		Assert.assertFalse(isElementPresent(loginPhoneNumberObj.getClearTextButton()), "'Clear Text' button did not get disabled after removing number in text field");
	}

	@Test(priority=11)
	public void test011_invalidNumberValidation() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter a number less that 7 digits. \n" +
				"2. Confirm the number and check for error popup to come. \n");
		
		loginPhoneNumberObj.setTextPhoneNumberField("12345");
		loginPhoneNumberObj.clickOnNextBtn();
		ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		
		//wait for error popup to come
		
		UnableToGetPinError numberError = new UnableToGetPinError();
		boolean errorPopupOccurred = numberError.waitForPinErrorAndDismiss();
		
		Assert.assertTrue(errorPopupOccurred, "Error popup (on entering invalid number) did not occur even after waiting for 90 seconds ");
		
	}
	
	@Test(priority=12)
	public void test012_MSISDNFieldValidation() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter a correct number. \n" +
				"2. Press next and cancel. \n");
		
		setDEFAULT_MSISDN();
		setPin();
		clickOnElement(loginPhoneNumberObj.getPhoneNumberEdit());
		loginPhoneNumberObj.removeEnteredText();
		clickOnElement(loginPhoneNumberObj.getPhoneNumberEdit());
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		
		Assert.assertTrue(isElementPresent(ConfirmYourNumberPopUpScreen.getCancelButton()), "'Cancel' button did not come in the popup");
		Assert.assertTrue(isElementPresent(ConfirmYourNumberPopUpScreen.getConfirmButton()), "'Confirm' button did not come in the popup");
		
		loginPhoneNumberObj = ConfirmYourNumberPopUpScreen.clickOnCancelButton();
		
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getPhoneNumberHeader()), "Cancelling the popup did not take to 'Phone Number' screen");
		
	}
	
	@Test(priority=13)
	public void test013_ConfirmMSISDN() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter a correct number. \n" +
				"2. Press next and confirm. \n");
		
		clickOnElement(loginPhoneNumberObj.getPhoneNumberEdit());
		loginPhoneNumberObj.removeEnteredText();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		
		pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		
		Assert.assertFalse(isElementPresent(ConfirmYourNumberPopUpScreen.getConfirmButton()), "Confirmation popup did not disappear after clicking on confirm button");
		Assert.assertTrue(isElementPresent(pinEnteringScreenObj.getPinScreenTitle()), "PIN screen title did not come as 'Verify'");
		Assert.assertTrue(isElementPresent(pinEnteringScreenObj.getBackIcon()), "Back button did not appear on Verify page");
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible by default when it should not be");
		Assert.assertTrue(getTextByName(pinEnteringScreenObj.getCallMeButton()).contains("Call Me"), "The 'call me' button did not appear on verify pin page");
		Assert.assertTrue(isElementPresent(pinEnteringScreenObj.getDidNotGetPinText()), "The 'Didn't get the PIN?' text did not come above the call me button");
		
		loginPhoneNumberObj = pinEnteringScreenObj.clickOnBackIcon();
		
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getPhoneNumberHeader()), "The back button on verify page did not take to phone number page");
		
	}
	
	@Test(priority=14)
	public void test014_ReenterMSISDN() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Re-enter a correct number. \n" +
				"2. Press next and confirm. \n");
		
		setPin();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		
		pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		Assert.assertTrue(isElementPresent(pinEnteringScreenObj.getPinScreenTitle()), "PIN screen title did not come as 'Verify'");
	}
	
	@Test(priority=15)
	public void test015_EnterWrongPin() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter incorrect pin. \n" +
				"2. Wait for invalid pin popup and assert the same. \n");
		
		pinEnteringScreenObj.clickOnPinTextField();
		pinEnteringScreenObj.setPin("1234");
		
		InvalidPinEnteredPopup invalidPinPopup = new InvalidPinEnteredPopup();
		
		Assert.assertTrue(isElementPresent(invalidPinPopup.getPopupHeader()), "Popup header did not come as 'Invalid PIN Entered'");
		Assert.assertTrue(isElementPresent(invalidPinPopup.getPopupText()), "Popup text did not come as 'Please enter the correct PIN.'");
		Assert.assertTrue(isElementPresent(invalidPinPopup.getCancelButton()), "Popup text did not come with 'Cancel' button");
		Assert.assertTrue(isElementPresent(invalidPinPopup.getGoBackButton()), "Popup text did not come with 'Go back' button");
		
		invalidPinPopup.clickOnCancel();
		
		Assert.assertTrue(isElementPresent(pinEnteringScreenObj.getPinScreenTitle()), "PIN screen title did not come as 'Verify'");
		
		pinEnteringScreenObj.clickOnPinTextField();
		pinEnteringScreenObj.clearEnteredPin();
		pinEnteringScreenObj.setPin("1234");
		
		loginPhoneNumberObj = invalidPinPopup.clickOnGoBack();
		Assert.assertTrue(isElementPresent(loginPhoneNumberObj.getPhoneNumberHeader()), "Phone number screen did not come after clicking on 'Go Back' of invalid PIN popup");
		
	}
	
	@Test(priority=16)
	public void test016_EnterCorrectPIN() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter correct pin. \n" +
				"2. Wait for name page and verify the elements on that page. \n");
		
		setPin();
		loginPhoneNumberObj.setTextPhoneNumberField(getDEFAULT_MSISDN_Create());
		loginPhoneNumberObj.clickOnNextBtn();
		
		pinEnteringScreenObj = ConfirmYourNumberPopUpScreen.clickOnConfirmButton();
		pinEnteringScreenObj.clickOnPinTextField();
		aboutYouScreenObj = pinEnteringScreenObj.setPin(DEFAULT_PIN);
		
		Assert.assertTrue(isElementPresent(aboutYouScreenObj.getAboutYouTitle()), "The title of screen is not 'About You'");
		Assert.assertFalse(isElementEnabled(aboutYouScreenObj.getNextButton()), "The next button is enabled by default when it should not be");
		Assert.assertFalse(isKeyboardVisible(), "Keyboard is visible by default when it should not be");
		Assert.assertTrue(isElementPresent(aboutYouScreenObj.getAddPhotoButton()), "The 'add photo' button is not visible by default");
		Assert.assertTrue(getTextByValue(aboutYouScreenObj.getEditNameField()).equalsIgnoreCase("Your Name"), "Default placeholder for enter name is not 'Your Name'");
		
		Assert.assertTrue(isElementPresent(aboutYouScreenObj.getConnectWithFacebookButton()), "The connect with facebook button did not appear on page");
		Assert.assertTrue(isElementPresent(aboutYouScreenObj.getFeelingLazyLabel()), "The 'feeling lazy' did not appear on screen");
		
	}
	
	@Test(priority=17)
	public void test017_ProfilePhoto() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on add photo logo. \n" +
				"2. Verify validity by first cancelling and then adding photo. \n");
		
		PhotosScreen photosScreenObj = aboutYouScreenObj.clickOnAddPhotoBtn();
		CameraScreen cameraObj = photosScreenObj.clickOnCameraIconInPhotosScreen();
		
		Assert.assertTrue(isElementPresent(cameraObj.getCancelButton()), "Cancel Button on available on camera screen");
		
		clickOnElement(cameraObj.getCancelButton());
		
		Assert.assertTrue(isElementPresent(photosScreenObj.getCameraIcon()), "'Cancel' button in camera screen did not take to photos screen");
		clickOnElement(photosScreenObj.getBackButton());
		clickOnElement(photosScreenObj.getStopButton());
	}
	
	@Test(priority=18)
	public void test018_ChooseCamera() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on add photo logo. \n" +
				"2. Choose camera image and set as profile picture. \n");
		
		
		PhotosScreen photosScreenObj = aboutYouScreenObj.clickOnAddPhotoBtn();
		CameraScreen cameraObj = photosScreenObj.clickOnCameraIconInPhotosScreen();
		clickOnElement(cameraObj.getCapturePhotoButton());
		
		//wait until photo is clicked
		boolean photoClicked = false;
		int counter = 0;
		while (!photoClicked && counter < 10) {
			try {
				driver.findElement(cameraObj.getRetakeButton());
				photoClicked = true;
			} catch(Exception e) {
				counter++;
			}
		}
		
		Assert.assertTrue(isElementPresent(cameraObj.getRetakeButton()), "Retake button did not come after clicking image");
		Assert.assertTrue(isElementPresent(cameraObj.getUsePhotoButton()), "Use Photo button did not come after clicking image");
		
		cameraObj.retakePhoto();
		
		Assert.assertTrue(isElementPresent(cameraObj.getCapturePhotoButton()), "Retake button did not take back to camera screen");
		Assert.assertTrue(isElementPresent(cameraObj.getCancelButton()), "Cancel button did not come on camera screen");
		
	}
	
	@Test(priority=19)
	public void test019_SetCameraImage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on take photo. \n" +
				"2. Set photo as profile picture. \n");
		
		CameraScreen cameraObj = new CameraScreen();
		clickOnElement(cameraObj.getCapturePhotoButton());
		clickOnElement(cameraObj.getUsePhotoButton());
		clickOnElement(cameraObj.getUpdateButton());
		
		Assert.assertTrue(isElementPresent(aboutYouScreenObj.getAboutYouTitle()), "'Use Photo' button in camera screen did not take to about you screen");
		
	}
	
	/*@Test(priority=20)
	public void test020_SetGalleryImage() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Click on choose existing. \n" +
				"2. Set photo from gallery as profile picture. \n");
		
		clickOnElement(aboutYouScreenObj.getAddPhotoButton());
		clickOnElement(aboutYouScreenObj.getChooseExistingButton());
		
	}*/
	
	@Test(priority=21)
	public void test021_UsernameValidationCases() {
		
		Reporter.log(iOSAutomation_DESCRIPTION+" : 1. Enter different cases of username. \n" +
				"2. Validate and assert the same. \n");
		
		String longUsername = "This username is longer than 20 chars";
		String usernameWithSpecialChars = "Ios97&^%";
		
		Assert.assertFalse(isElementEnabled(aboutYouScreenObj.getNextButton()), "Proceeded without entering a user name when it should not have");
		
		aboutYouScreenObj.setName(longUsername);
		
		Assert.assertFalse(getTextByValue(aboutYouScreenObj.getEditNameField()).length() > 21, "The username field is taking more than 20 characters when it should be.");
		aboutYouScreenObj.clearTypedUsername();
		aboutYouScreenObj.setName(usernameWithSpecialChars);
		Assert.assertTrue(isElementEnabled(aboutYouScreenObj.getNextButton()), "On entering special chars/numbers/alphabets combo, the next button was not activated");
		
		aboutYouScreenObj.clickOnNextBtn();
		PushNotificationsScreen pushNotificationScreenObj = ChooseYourProfilePicturePopUp_NameEnteringScreen.clickOnNoBtn();
		
		pushNotificationScreenObj.clickOnContinue_Btn();
	}
	
}
