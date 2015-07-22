package com.bsb.hike.qa.apisupport;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.http.HTTPService;
import com.bsb.hike.qa.constants.AutomationConstants;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;

public class UserModificationSupport extends BaseClass{
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    HTTPService httpService = new HTTPService();
	
	public static void main(String[] args){
		UserModificationSupport user = new UserModificationSupport();
		user.createHikeUserWithMsisdn("+911231231242");
		user.deleteUser("+911231231242");
	}
	
	public boolean createHikeUserWithMsisdn(String msisdn){
		boolean isUserCreated = false;
		try {
		      String accountUrl = "http://" + httphost + ":"+httpport+AutomationConstants.CreateAccountUrl;
		      Map<String,String> headers = new HashMap<String,String>();
	          headers.put("X-MSISDN", msisdn);
		      JsonObject postData = registerDevice(msisdn);
		      httpService.postDataToUrl(accountUrl, "application/json", headers, postData.toString() , "POST");
		      String uid = getuidFromMsisdn(msisdn);
		      System.out.println(uid);
		      System.out.println("Is account created?: " + !StringUtils.isBlank(uid));
		      Thread.sleep(200);
		      isUserCreated = !StringUtils.isBlank(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUserCreated;
	}
	
	public boolean deleteUser(String msisdn){
		boolean isDeleted = false;
		try {
			isDeleted = httpService.delete(msisdn);
			System.out.println("Is account deleted: " + isDeleted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}
	

	
	  public JsonObject registerDevice(String msisdn) throws Exception{
          httpService.registerPinRequest(httphost , httpport , msisdn);
          Thread.sleep(AutomationConstants.MAX_WAIT_Time);
          RedisServiceManagerUtil redisManager = RedisServiceManagerUtil.getInstance();
          String pinCode = redisManager.generateUserPin(msisdn);
          JsonObject postData = new JsonObject();
          if(StringUtils.isNotBlank(pinCode)) {
              postData.addProperty("pin", pinCode);
              postData.addProperty("msisdn", msisdn);
          }
	          
	      postData = new JsonObject();
	      postData.addProperty("deviceid", "and:9bbbf07099d7d4db0126d6c3c998e3b"+ RandomStringUtils.randomNumeric(5) + RandomStringUtils.randomAlphabetic(2) +RandomStringUtils.randomNumeric(2));  
	      postData.addProperty("devicetype", "android");
	      postData.addProperty("deviceversion", "Micromax A75");
	      postData.addProperty("appversion", "2.6.0");
	      postData.addProperty("_os_version", "2.3.4");
	      postData.addProperty("_os", "android");
	      return postData;
	  }
	  

}
