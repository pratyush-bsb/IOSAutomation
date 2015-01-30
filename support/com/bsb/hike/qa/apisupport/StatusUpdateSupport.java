package com.bsb.hike.qa.apisupport;

import java.util.HashMap;
import java.util.Map;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.http.HTTPService;
import com.bsb.hike.qa.constants.AutomationConstants;
import com.bsb.hike.qa.dbmanager.MongoDBManagerUtil;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;
import com.mongodb.DB;

public class StatusUpdateSupport extends BaseClass{
	private static final String JSONTYPE = "application/json";
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    String uidSender = "";
	String tokenSender = "";
	HTTPService httpService = new HTTPService();
	
	public static void main(String[] args) {
		StatusUpdateSupport su = new StatusUpdateSupport();
		su.setStatusUpdate("+919810771083");
	}
 
	  public void setStatusUpdate(String msisdnUserChangingStatus) {
	      try
	      {   
	    	  uidSender = getuidFromMsisdn(msisdnUserChangingStatus);
	    	  tokenSender = getTokenFromMsisdn(msisdnUserChangingStatus);
	    	  String suMessage = "This is test SU";
		      String statusUpdateUrl = "http://" + httphost +":"+ httpport + AutomationConstants.StatusUpdateUrl; 
		      Map<String,String> headers = new HashMap<String,String>();
		      headers.put("Cookie", "user="+tokenSender + "; UID=" + uidSender);
		      JsonObject postData = new JsonObject();
		      postData.addProperty("status-message", suMessage);
		      postData.addProperty("mood", 1);
		      postData.addProperty("fb", false);
		      postData.addProperty("twitter", false);
		      postData.addProperty("timeofday", 2);
		      
		      httpService.postDataToUrl(statusUpdateUrl, JSONTYPE, headers, postData.toString() , "POST");  
		      Thread.sleep(1000);
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	 
	      }
	  }

}