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

public class SendStickerSupport extends BaseClass{
	
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
		SendStickerSupport sticker = new SendStickerSupport();
		sticker.sendSticker("+915544332222", "+919810771083");
	}
 
  public void sendSticker(String msisdnSender , String msisdnReceiver) {
      try
      {  
    	  uidSender = getuidFromMsisdn(msisdnSender);
    	  tokenSender = getTokenFromMsisdn(msisdnSender);
    	  uidReceiver = getuidFromMsisdn(msisdnReceiver);
    	  tokenReceiver = getTokenFromMsisdn(msisdnReceiver);
    	  
    	  queueHandlerSender = new QueueMessageHandler();
    	  mqttConnectionSender = new HikeMqttConnection(msisdnSender , queueHandlerSender);
    	  
    	  mqttConnectionSender.connect(mqtthost , mqttport , uidSender , tokenSender);
          Thread.sleep(100);

          JsonObject mdObj = new JsonObject();
          mdObj.addProperty("catId", "bollywood"); 
          mdObj.addProperty("stId", "001_salu.png");
                   
          long ts = System.currentTimeMillis();
          String uniqueId = RandomStringUtils.randomNumeric(6);
          JsonObject jsonDataObj = new JsonObject();
          jsonDataObj.addProperty("ts", ts); 
          jsonDataObj.addProperty("hm", "");
          jsonDataObj.addProperty("i", uniqueId);
          jsonDataObj.add("md", mdObj);
                     
          JsonObject jsonData = new JsonObject();
          jsonData.addProperty("to", msisdnReceiver);
          jsonData.add("d", jsonDataObj);
          jsonData.addProperty("st", "stk");
          jsonData.addProperty("t", "m");
          
          mqttConnectionSender.publish(uidSender+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
          Thread.sleep(1000);
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