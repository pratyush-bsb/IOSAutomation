package com.bsb.hike.qa.apisupport;


import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import junit.framework.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.mqtt.client.MqttConnection;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;

public class FriendsActionSupport extends BaseClass{
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    	
	public static void main(String[] args) {
		FriendsActionSupport sendFriendRequest = new FriendsActionSupport();
		sendFriendRequest.setSmsCount("+919810771083", "100");
		int smsCount = sendFriendRequest.getRemainingSMSCredits("+917777779944");
		System.out.println(smsCount);
		sendFriendRequest.create2WayFriendship("+915544332222", "+919810771083");
		sendFriendRequest.sendFriendRequest("+915544332222", "+919810771083");
		sendFriendRequest.removeFriendShip("+919810771083", "+915544332222");
		
	}
	
	  public void create2WayFriendship(String msisdn1 , String msisdn2) {
	      try
	      {   
	    	  QueueMessageHandler queueHandler1 = null;
	    	  HikeMqttConnection mqttConnection1 = null;
	    	  String uid1 = getuidFromMsisdn(msisdn1);
	    	  String token1 = getTokenFromMsisdn(msisdn1);
	    	  queueHandler1 = new QueueMessageHandler();
	    	  mqttConnection1 = new HikeMqttConnection(msisdn1 , queueHandler1);
	    	  mqttConnection1.connect(mqtthost ,mqttport,uid1,token1);
	    	  Thread.sleep(100);
	    	  String msisdnToAdd = msisdn2; 
	    	  JsonObject jsonDataObj = new JsonObject();
	    	  jsonDataObj.addProperty("id", msisdnToAdd);           
	    	  JsonObject jsonData = new JsonObject();
	    	  jsonData.add("d", jsonDataObj);
	    	  jsonData.addProperty("t", "af");
	    	  mqttConnection1.publish(uid1+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
	    	  Thread.sleep(100);
	    	  //mqttConnection1.disconnect();
	    	  
	    	  QueueMessageHandler queueHandler2 = null;
	    	  HikeMqttConnection mqttConnection2 = null;
	    	  String uid2 = getuidFromMsisdn(msisdn2);
	    	  String token2 = getTokenFromMsisdn(msisdn2);
	    	  queueHandler2 = new QueueMessageHandler();
	    	  mqttConnection2 = new HikeMqttConnection(msisdn2 , queueHandler2);
	    	  mqttConnection2.connect(mqtthost ,mqttport,uid2,token2);
	    	  Thread.sleep(100);
	    	  msisdnToAdd = msisdn1; 
	    	  jsonDataObj = new JsonObject();
	    	  jsonDataObj.addProperty("id", msisdnToAdd);           
	    	  jsonData = new JsonObject();
	    	  jsonData.add("d", jsonDataObj);
	    	  jsonData.addProperty("t", "af");
	    	  mqttConnection2.publish(uid2+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
	    	  Thread.sleep(100);
	    	  //mqttConnection2.disconnect();
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	  Assert.fail();
	      }
	  }
	
	  public void sendFriendRequest(String msisdnFriendrequestSender , String msisdnFriendRequestReceiver) {
	      try
	      {   
	    	  QueueMessageHandler queueHandlerSender = null;
	    	  HikeMqttConnection mqttConnectionSender = null;
	    	  String uidSender = getuidFromMsisdn(msisdnFriendrequestSender);
	    	  String tokenSender = getTokenFromMsisdn(msisdnFriendrequestSender);
	    	  queueHandlerSender = new QueueMessageHandler();
	    	  mqttConnectionSender = new HikeMqttConnection(msisdnFriendrequestSender , queueHandlerSender);
	    	  mqttConnectionSender.connect(mqtthost ,mqttport,uidSender,tokenSender);
	    	  Thread.sleep(100);
	    	  String msisdnToAdd = msisdnFriendRequestReceiver; 
	    	  JsonObject jsonDataObj = new JsonObject();
	    	  jsonDataObj.addProperty("id", msisdnToAdd);           
	    	  JsonObject jsonData = new JsonObject();
	    	  jsonData.add("d", jsonDataObj);
	    	  jsonData.addProperty("t", "af");
	    	  mqttConnectionSender.publish(uidSender+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
	    	  Thread.sleep(100);
	    	  mqttConnectionSender.disconnect();
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	  Assert.fail();
	      }
	  }
	  
	  public void removeFriendShip(String msisdnSendingRemovePacket , String msisdnReceivingRemovePacket){
		  try {
			  QueueMessageHandler queueHandlerSender = null;
			  HikeMqttConnection mqttConnectionSender = null;
			  String uidSender = getuidFromMsisdn(msisdnSendingRemovePacket);
			  String tokenSender = getTokenFromMsisdn(msisdnSendingRemovePacket);
			  queueHandlerSender = new QueueMessageHandler();
			  mqttConnectionSender = new HikeMqttConnection(msisdnSendingRemovePacket , queueHandlerSender);
			  mqttConnectionSender.connect(mqtthost ,mqttport,uidSender,tokenSender);
			  Thread.sleep(100);
			 
			  String msisdnToRemove = msisdnReceivingRemovePacket; 
			  JsonObject jsonDataObj = new JsonObject();
			  jsonDataObj.addProperty("id", msisdnToRemove);           
			  JsonObject jsonData = new JsonObject();
			  jsonData.add("d", jsonDataObj);
			  jsonData.addProperty("t", "rf");
			  mqttConnectionSender.publish(uidSender+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
			  Thread.sleep(1000);
			  mqttConnectionSender.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public void setSmsCount(String msisdn , String smsCountToSet){
		  try {
			  String uid = getuidFromMsisdn(msisdn);
			  redis.hset("em_" + uid , "h2sjb", smsCountToSet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	 
	  
	  public void clearCacheAfterDelete(String msisdn){
		  try {
			  redis.hdel("delsmscredits", msisdn);
			  redis.hdel("recurringCredits", msisdn);
			  System.out.println("deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
  

public class QueueMessageHandler {
    private final BlockingQueue<String> receivedMessages = new LinkedBlockingDeque<String>();
    
    public void handleMessage(String message) {
        try
        {
            receivedMessages.put(message);
        }
        catch (InterruptedException e)
        {
        }
    }
    
    public String getReceivedMessage() throws InterruptedException {
        return receivedMessages.take();
    }
    
    public void printQueueState() {
    }
}

private static class HikeMqttConnection extends MqttConnection
{
    private QueueMessageHandler handler;
    public HikeMqttConnection(String id, QueueMessageHandler handler)
    {
        super(id);
        this.handler = handler;
    }
    
    @Override
    protected void handleMessage(PublishMessage msg,Runnable ack) {
        super.handleMessage(msg, ack);
        System.out.println("server logs:**** " + msg.getDataAsString());
        handler.handleMessage(msg.getDataAsString());
    }
}

public int getRemainingSMSCredits(String msisdn) {

	  try{

	  String uid = getuidFromMsisdn(msisdn);

	  Map<String , String> list = redis.hgetAll("em_"+uid);



	  double transactionScore = 0.0;

	  int sentMessages = (list.get("sm") !=null) ?  Integer.parseInt(list.get("sm")) : 0; 

	  int receivedMessages = (list.get("rm") !=null) ?  Integer.parseInt(list.get("rm")) : 0; 



	  int gcSentMessages = (list.get("gcsm") !=null) ?  Integer.parseInt(list.get("gcsm")) : 0;

	  int gcReceivedMessages = (list.get("gcrm") !=null) ?  Integer.parseInt(list.get("gcrm")) : 0;



	  transactionScore = (1 * Math.min(sentMessages, receivedMessages)) +

	  (0.2 * Math.min(gcSentMessages, gcReceivedMessages));



	  int h2sEmLinked = (int) Math.floor((2*(Math.pow(transactionScore, 0.55))));

	  int h2sJoiningBonus = (list.get("h2sjb") !=null) ?  Integer.parseInt(list.get("h2sjb")) : 0;

	  int h2sEarned = (list.get("h2se") !=null) ?  Integer.parseInt(list.get("h2se")) : 0;

	  int h2sManualBuffer = (list.get("h2smb") !=null) ?  Integer.parseInt(list.get("h2smb")) : 0;

	  int h2sSent = (list.get("h2ss") !=null) ?  Integer.parseInt(list.get("h2ss")) : 0;



	  int currentH2S = (h2sJoiningBonus + h2sEarned + h2sManualBuffer + h2sEmLinked) - h2sSent;

	  return (currentH2S >= 0) ? currentH2S : 0;

	  }

	  catch(Exception e){

	  e.printStackTrace();

	  }

	  return 0;

	 

	    }


}