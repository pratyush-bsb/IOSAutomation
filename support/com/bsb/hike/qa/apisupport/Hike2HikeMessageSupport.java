package com.bsb.hike.qa.apisupport;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang.RandomStringUtils;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.mqtt.client.MqttConnection;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;

public class Hike2HikeMessageSupport extends BaseClass{
	
	QueueMessageHandler queueHandlerSender;
	HikeMqttConnection mqttConnectionSender;
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
	QueueMessageHandler queueHandlerRec;
	HikeMqttConnection mqttConnectionRec;
	
	String uidSender = "";
	String tokenSender = "";
	
	String uidReceiver = "";
	String tokenReceiver = "";
	
	public static void main(String[] args) {
		Hike2HikeMessageSupport hikeMessage = new Hike2HikeMessageSupport();
		String message = "auto h2h#" + RandomStringUtils.randomNumeric(4);
		hikeMessage.sendHikeMessage("+915544332222", "+919810771083" , message);
	}
 
  public void sendHikeMessage(String msisdnSender , String msisdnReceiver , String message) {
      try
      {  
    	  System.out.println("Inside send hike message");
    	  uidSender = getuidFromMsisdn(msisdnSender);
    	  tokenSender = getTokenFromMsisdn(msisdnSender);
    	  uidReceiver = getuidFromMsisdn(msisdnReceiver);
    	  tokenReceiver = getTokenFromMsisdn(msisdnReceiver);
    	  
    	  System.out.println(msisdnSender);
    	  System.out.println(uidSender);
    	  System.out.println(tokenSender);
    	  System.out.println(msisdnReceiver);
    	  System.out.println(uidReceiver);
    	  System.out.println(tokenReceiver);
    	  
    	  queueHandlerSender = new QueueMessageHandler();
    	  mqttConnectionSender = new HikeMqttConnection(msisdnSender , queueHandlerSender);
    	  
    	  mqttConnectionSender.connect(mqtthost , mqttport , uidSender , tokenSender);
          Thread.sleep(100);
          long ts = System.currentTimeMillis();
          //String message = "API automated message#"+RandomStringUtils.randomNumeric(4);
          JsonObject jsonDataObj = new JsonObject();
          jsonDataObj.addProperty("ts", ts); 
          jsonDataObj.addProperty("hm", message);
          jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(4));
          
          JsonObject jsonData = new JsonObject();
          jsonData.addProperty("to", msisdnReceiver);
          jsonData.add("d", jsonDataObj);
          jsonData.addProperty("t", "m");
          mqttConnectionSender.publish(uidSender+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
          Thread.sleep(100);
          mqttConnectionSender.disconnect();

      }
      catch (Exception e)
      {
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


}