package com.bsb.hike.ios.library;


import junit.framework.Assert;

import com.bsb.hike.ios.screens.HomeScreenMenu;
import com.bsb.hike.qa.apisupport.FriendsActionSupport;
import com.bsb.hike.qa.apisupport.GroupChatMessageSupport;
import com.bsb.hike.qa.apisupport.Hike2HikeMessageSupport;
import com.bsb.hike.qa.apisupport.UserModificationSupport;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;






/*****************************************************************************************************************************************************************
 * 
 * @author  
 *
 *	This class contains all the comman functions related to hike app
 * example : create account, delete account
 * 
 ****************************************************************************************************************************************************************/

public class HikeLibrary{
	
	public static FriendsActionSupport friendActionSupport = new FriendsActionSupport();
	public static UserModificationSupport userSupport = new UserModificationSupport();
	public static Hike2HikeMessageSupport hike2HikeMessage = new Hike2HikeMessageSupport();
	public static GroupChatMessageSupport gcSupport = new GroupChatMessageSupport();

	public static int MAX_TIMEOUT = 60000;
	public static int MIN_TIMEOUT = 15000;
		
	public static int DEFAULT_CHARACTER_COUNT=40;
	public static String DEFAULT_PIN = "1111";
	public static String DEFAULT_NAME = "IOS";

	public static String HIKE_CONTACT_NAME ="FirstTestUser";

	public static String HIKE_NUMBER_1 = "+911255251795";
	
	public static String HIKE_CONTACT_NAME_1 ="SecondTestUser";
	public static String HIKE_NUMBER_2 = "+911210485892";
	
	public static String HIKE_CONTACT_NAME_2 ="ThirdTestUser";
	public static String HIKE_NUMBER_3 = "+911212263641";
	
	public static String HIKE_CONTACT_NAME_3 ="FourthTestUser";
	public static String HIKE_NUMBER_4 = "+911202778911";
	
	public static String INTERNATIONAL_HIKE_USER ="INTERNATIONALHIKEUSER";
	public static String INTERNATIONAL_HIKE_NUMBER = "+14157794453";
	
	public static String HIKE_SMS_CONTACT_NAME_1 ="HikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_1 ="+911231231232";
	
	public static String HIKE_SMS_CONTACT_NAME_2 ="SecondHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_2 ="+911231234321";
	
	public static String HIKE_SMS_CONTACT_NAME_3 ="ThirdHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_3 ="+911231265473";
	
	public static String HIKE_SMS_CONTACT_NAME_4 ="FourthHikeSMSContact";
	public static String HIKE_SMS_CONTACT_NUMBER_4 ="+911231233487";
	
	public static String HIKE_DND_NAME_1 ="HikeDNDUser";
	public static String HIKE_DND_NUMBER_1 ="+919540752593";
	
	public static String DEF_DIGIT ="777777";
	public static String DEFAULT_MSISDN = DEF_DIGIT+"1234"; //RandomStringUtils.randomNumeric(4);
	
	
//	public static void main(String args[]){
//		getUser();
//	}
/*****************************************************************************************************************************************************************
 * 
 * @return MSISDN of the current User
 * @author  
 * 
 ****************************************************************************************************************************************************************/
	public static String getUser(){
		return System.getProperty("user.dir");
		
	}
	public static String getDEFAULT_MSISDN_Create(){
		return DEFAULT_MSISDN;
		
	}
	
	public static String getDEFAULT_MSISDN() {
		setDEFAULT_MSISDN();
		return "+91"+DEFAULT_MSISDN;
	}
	public static void setDEFAULT_MSISDN(){
		DEFAULT_MSISDN="7777776677";
	}
//	public static  String getMsisdn() throws IOException {
//		 
//        Properties prop = new Properties();
//        
//        String propFileName = "/data/local/tmp/local.properties";
//        FileReader reader = new FileReader(propFileName);
//        prop.load(reader);
//        
//        String msisdn = prop.getProperty("msisdn");
//       
//        return msisdn;
//    }
	public static void setPin(){
		System.out.println("Setting Pin");
		RedisServiceManagerUtil.getInstance().setKey("pincodes-"+getDEFAULT_MSISDN(), DEFAULT_PIN);	
	}
	
}
