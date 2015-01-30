package com.bsb.hike.qa.constants;

import org.apache.commons.lang.RandomStringUtils;

public class AutomationConstants {
	
	//Current Working Directory
	public static String WorkingDir = System.getProperty("user.dir");
	
	//URL for config file containing env specific info like host, port, mqtt/mongo details etc.
	public static String ConfigFileUrl="./config/testng-env-awsstaging.properties";
	public static long MAX_WAIT_Time = 15000;
	public static long MIN_WAIT_Time = 1000;
	
	//API URLs
	public static String UploadAddressBookUrl = "/v1/account/addressbook";
	public static String CreateAccountUrl = "/v1/account";
	public static String ValidatePin = "/v1/account/validate";
	public static String DeleteAccountUrl = "/v1/account";
	public static String UnlinkAccountUrl = "/v1/account/unlink";
	public static String SetAccountNameUrl = "/v1/account/name";
	public static String UpdateProfileUrl = "/v1/account/profile";
	public static String UpdateProfilePicUrl = "/v1/account/avatar";
	public static String FileTransferUrl = "/v1/user/ft";
	public static String StatusUpdateUrl = "/v1/user/status";
	public static String ConnectFBUrl = "/v1/account/connect/fb";
	public static String ConnectTwitterUrl = "/v1/account/connect/twitter";
	public static String ListConnectedAccounts = "/v1/account/connect";
	public static String DeleteFBUrl = "/v1/account/connect/fb";
	public static String DeleteTwitterUrl = "/v1/account/connect/twitter";
	public static String SpreadHikeFbTwitter = "/v1/account/spread";
	public static String GetStickerUrl = "/v1/stickers?";
	public static String GetLastSeen = "/v1/user/lastseen";
	public static String PartialFT = "/v1/user/pft/";
	
	//Redis host and port
	public static final String Redis_Staging_Host = "10.0.1.210";
	public static final int Redis_Staging_Port = 6379;
	
	
	public static final String Comviva_Staging_Url = "http://54.251.153.101:8181/comviva/";
	

}
