package com.bsb.hike.qa.apisupport;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


import com.bsb.hike.base.BaseClass;
import com.bsb.hike.mqtt.client.MqttConnection;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;

public class SendFriendRequestSupport extends BaseClass{
	
	QueueMessageHandler queueHandlerSender;
	HikeMqttConnection mqttConnectionSender;
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    QueueMessageHandler queueHandlerRec;
	HikeMqttConnection mqttConnectionRec;
	String uidSender = "";
	String tokenSender = "";
	
	public static void main(String[] args) {
		SendFriendRequestSupport sendFriendRequest = new SendFriendRequestSupport();
		sendFriendRequest.sendFriendRequest("+915544332222", "+919810771083");
	}
	
	  public void sendFriendRequest(String msisdnFriendrequestSender , String msisdnFriendRequestReceiver) {
	      try
	      {   
	    	  uidSender = getuidFromMsisdn(msisdnFriendrequestSender);
	    	  tokenSender = getTokenFromMsisdn(msisdnFriendrequestSender);
	    	  queueHandlerSender = new QueueMessageHandler();
	    	  mqttConnectionSender = new HikeMqttConnection(msisdnFriendrequestSender , queueHandlerSender);
	    	  mqttConnectionSender.connect(mqtthost , mqttport , uidSender,tokenSender);
	          Thread.sleep(100);
	          String msisdnToAdd = msisdnFriendRequestReceiver; 
	          JsonObject jsonDataObj = new JsonObject();
	          jsonDataObj.addProperty("id", msisdnToAdd);           
	          JsonObject jsonData = new JsonObject();
	          jsonData.add("d", jsonDataObj);
	          jsonData.addProperty("t", "af");
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