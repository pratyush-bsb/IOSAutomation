package com.bsb.hike.ios.library;


import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;

import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.bsb.appium.Library.AppiumLibrary;
import com.bsb.hike.ios.screens.FeedbackMail;
import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.ios.screens.SettingsScreen;
import com.support.bsb.hike.qa.apisupport.FriendsActionSupport;
import com.support.bsb.hike.qa.apisupport.GroupChatMessageSupport;
import com.support.bsb.hike.qa.apisupport.Hike2HikeMessageSupport;
import com.support.bsb.hike.qa.apisupport.UserModificationSupport;
import com.support.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;







/*****************************************************************************************************************************************************************
 * 
 * @author  
 *
 *	This class contains all the common functions related to hike app
 * example : create account, delete account
 * 
 ****************************************************************************************************************************************************************/

public class HikeLibrary extends AppiumLibrary {

	public static FriendsActionSupport friendActionSupport = new FriendsActionSupport();
	public static UserModificationSupport userSupport = new UserModificationSupport();
	public static Hike2HikeMessageSupport hike2HikeMessage = new Hike2HikeMessageSupport();
	public static GroupChatMessageSupport gcSupport = new GroupChatMessageSupport();
	public By conversationHide = MobileBy.name("conversation hi");

	public static int MAX_TIMEOUT = 60000;
	public static int MIN_TIMEOUT = 15000;

	public static int DEFAULT_CHARACTER_COUNT=40;
	public static String DEFAULT_PIN = "1111";
	public static String DEFAULT_NAME = "IOS";

	public static String DEFAULT_GROUP_NAME = "HikeTestGroup";
	public static String DEFAULT_GROUP_NAME1 = "HikeTestGroup1";
	public static String DEFAULT_GROUP_NAME2 = "HikeTestGroup2";
	public static String UPDATED_NAME = "HikeTestUser2";
	public static String STATUS_UPDATE ="Happy";
	public static String TEST_CHAT_MESSAGE ="Test Message";
	public static String TEST_GROUP_CHAT_MESSAGE = "Group Chat Message";
	
	public static String HIKE_CONTACT_NAME ="FirstTestUser";
	public static String HIKE_NUMBER_1 = "+914444440001";

	
	public static String HIKE_CONTACT_NAME_1 ="SecondTestUser";
	public static String HIKE_NUMBER_2 = "+914444440002";
	
	public static String HIKE_CONTACT_NAME_2 ="ThirdTestUser";
	public static String HIKE_NUMBER_3 = "+914444440003";
	
	public static String HIKE_CONTACT_NAME_3 ="FourthTestUser";
	public static String HIKE_NUMBER_4 = "+914444440004";
	
	public static String HIKE_CONTACT_NAME_4 ="FifthTestUser";
	public static String HIKE_NUMBER_5 = "+914444440005";

	
	
	public static String INTERNATIONAL_HIKE_USER ="INTERNATIONALHIKEUSER";
	public static String INTERNATIONAL_HIKE_NUMBER = "+447903524281";
	
	public static String HIKE_SMS_CONTACT_NAME_1 ="HikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_1 ="+911231231232";
	
	public static String HIKE_SMS_CONTACT_NAME_2 ="SecondHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_2 ="+911231234321";
	
	
	
	public static String HIKE_SMS_CONTACT_NAME_3 ="ThirdHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_3 ="+911231265473";
	
	public static String HIKE_SMS_CONTACT_NAME_4 ="FourthHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_4 ="+911231233487";
	
	public static String HIKE_DND_NAME_1 ="HikeDNDUser";
	public static String HIKE_DND_NUMBER_1 ="+919818461120";
	
	public static String NON_HIKE_USER = "+914444445319";
	public static String HIKE_NOT_SAVED_USER = "+914444442486";
	
	public static String HIKE_NOT_SAVED_SMS_USER = "+914444445003";

	public static String HIKE_BOT_NATASHA = "Natasha";

	public static String DEF_DIGIT ="777777";
	public static String DEFAULT_MSISDN = DEF_DIGIT+"1234"; //RandomStringUtils.randomNumeric(4);

	public static String iOSAutomation_DESCRIPTION = "iOSAutomation_DESCRIPTION";


	//	public static void main(String args[]){
	//		getUser();
	//	}
	/*****************************************************************************************************************************************************************
	 * 
	 * @return MSISDN of the current User
	 * @author  
	 * 
	 ****************************************************************************************************************************************************************/
	public String getUser(){
		return System.getProperty("user.dir");

	}
	public String getDEFAULT_MSISDN_Create(){
		return DEFAULT_MSISDN;

	}

	public String getDEFAULT_MSISDN() {
		setDEFAULT_MSISDN();
		return "+91"+DEFAULT_MSISDN;
	}
	public void setDEFAULT_MSISDN() {
		try {
			DEFAULT_MSISDN= getMsisdn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getMsisdn() {
		String msisdn = "";
		try {
			Properties prop = new Properties();

			String propFileName = "local.properties";
			FileReader reader = new FileReader(propFileName);
			prop.load(reader);

			msisdn = prop.getProperty("msisdn");
		} catch(Exception e) {
			Reporter.log("Unable to fetch own MSISDN");
		}
		return msisdn;
	}
	
	public void setPin(){
		Reporter.log("Setting Pin");
		RedisServiceManagerUtil.getInstance().setKey("pincodes-"+getDEFAULT_MSISDN(), DEFAULT_PIN);	
	}

	//waits for element to load. waits for 120 seconds for now
	public void waitForElementToLoad (By pin_EditText, IOSDriver driver) {

		int counter = 0;
		while (counter < 120) {
			Reporter.log("Waiting for element : " + pin_EditText.toString() + " to load. Counter : " + counter);
			try {
				if(isElementPresent(pin_EditText)) {
					//element found. Break out
					break;
				} else {
					//wait for a second and check again
					Thread.sleep(1000);
					counter++;
				}
			} catch (Exception e) {
				counter++;
				continue;
			}
		}
	}

	public void goToHome() {

		//see if hide chat logo available. If not, keep pressing back button
		boolean hideLogo = false;
		//HomeScreenMenu homeScreenMenuObj = new HomeScreenMenu();
		By universalBackButtonIdentifier = MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[0]");

		while(!hideLogo) {
			try {
				if(driver.findElement(conversationHide).isDisplayed()) {
					hideLogo = true;
				} else {
					clickOnElement(universalBackButtonIdentifier);
				}
			} catch (Exception e) {
				hideLogo = false;
				clickOnElement(universalBackButtonIdentifier);
			}
		}
	}

	//ignore the 'love using hike' popup
	public void ignoreLoveUsingHikePopup() {

		By cancelLoveUsingHikePopup = MobileBy.name("Ask me later");
		if(isElementPresent(cancelLoveUsingHikePopup)) {
			clickOnElement(cancelLoveUsingHikePopup);
		}
	}

	/*public static void softAssertTrue(boolean condition) {
    	try {
    		assertTrue(condition);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    	}
    }*/

	public void hideHikeKeyboard() {

		try {
			By cancelBy = MobileBy.name("Cancel");
			WebElement cancel = driver.findElement(cancelBy);
			new TouchAction(driver).press(cancel).perform();
		} catch (Exception e) {
			Reporter.log("Unable to hide keyboard");
		}

	}
	
	public String getAppVersion() {

		String appVersion = "";

		goToHome();
		HomeScreenMenu homeScreenObj = new HomeScreenMenu();
		SettingsScreen settingsObj = homeScreenObj.clickOnSettings_Lbl();
		FeedbackMail feedbackMailObj = settingsObj.clickOnContact();
		
		String fullMessage = feedbackMailObj.getMessageBody();
		appVersion = fullMessage.split(":")[1].split("Build")[0].trim();
		
		return appVersion;
	}

}
