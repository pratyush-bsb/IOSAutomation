package com.bsb.hike.base;

import com.bsb.hike.qa.dbmanager.MongoDBManagerUtil;
import com.mongodb.DB;

public class BaseClass
{
    public MongoDBManagerUtil mongoManager;
    public DB userDB;
    
    public String httphost = "";
    public int httpport;
    public String mqtthost = "";
    public int mqttport;
    
    public BaseClass(){
        setEnvValues();
        mongoDBSetup();
    }
    
  public void setEnvValues() {
      try {
          httphost =    "staging.im.hike.in";
          httpport =    8080;
          mqtthost =    "staging.im.hike.in";
          mqttport =     1883;
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
  
  public void mongoDBSetup(){
        try
        {
            mongoManager = MongoDBManagerUtil.getInstance();    
            userDB = mongoManager.getMongo().getDB("userdb");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
  }
  
  public String getuidFromMsisdn(String msisdn){
      try
    {
          String rewardsDetails[] = mongoManager.getUidFromMsisdn(msisdn , userDB).split(":");
          String uid = rewardsDetails[0];
          return uid;
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return null;
  }
  
  public String getTokenFromMsisdn(String msisdn){
      try
    {
          String token = mongoManager.getCurrentToken(msisdn, userDB);
          return token;
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return null;
  }
  

}
