package com.bsb.hike.qa.apisupport;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.mqtt.client.MqttConnection;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;

public class MqttSupport extends BaseClass{

	QueueMessageHandler queueHandlerSender;
	HikeMqttConnection mqttConnectionSender;
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    QueueMessageHandler queueHandlerRec;
	HikeMqttConnection mqttConnectionRec;
	
	String uid = "";
	String token = "";
	
	String uidReceiver = "";
	String tokenReceiver = "";
	
	public static void main(String[] args){
		MqttSupport t = new MqttSupport();
		t.getUserOnline("+919818789836");
	}
	
	public void getUserOnline(String msisdn){
		try {
			  uid = getuidFromMsisdn(msisdn);
	    	  token = getTokenFromMsisdn(msisdn);
	    	  
	    	  queueHandlerSender = new QueueMessageHandler();
	    	  mqttConnectionSender = new HikeMqttConnection(msisdn , queueHandlerSender);
	    	  
	    	  System.out.println(uid);
	    	  System.out.println(token);
	    	  mqttConnectionSender.connect("staging.im.hike.in",1883,uid,token);
	          Thread.sleep(100);
	          mqttConnectionSender.subscribe(uid+"/u",QoS.AT_LEAST_ONCE);
	          mqttConnectionSender.subscribe(uid+"/s",QoS.AT_LEAST_ONCE);
	          mqttConnectionSender.subscribe(uid+"/a",QoS.AT_LEAST_ONCE);
	          
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void sendTypingNotification(String msisdnSender , String msisdnReceiver){
		try {
			  uid = getuidFromMsisdn(msisdnSender);
	    	  token = getTokenFromMsisdn(msisdnSender);
	    	  
	    	  queueHandlerSender = new QueueMessageHandler();
	    	  mqttConnectionSender = new HikeMqttConnection(msisdnSender , queueHandlerSender);
	    	  
	    	  System.out.println(uid);
	    	  System.out.println(token);
	    	  mqttConnectionSender.connect(mqtthost , mqttport , uid , token);
	          Thread.sleep(100);
	          mqttConnectionSender.subscribe(uid+"/u",QoS.AT_LEAST_ONCE);
	          mqttConnectionSender.subscribe(uid+"/s",QoS.AT_LEAST_ONCE);
	          mqttConnectionSender.subscribe(uid+"/a",QoS.AT_LEAST_ONCE);
	          
	          JsonObject jsonData = new JsonObject();
	          jsonData.addProperty("t", "st");
	          jsonData.addProperty("f", msisdnSender);
	          jsonData.addProperty("ts", System.currentTimeMillis());
	          mqttConnectionSender.publish(uid+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
	          Thread.sleep(5000);
	          
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public boolean isStartTypingNotifReceived(String msisdnTypingText){
		boolean isSTReceived = false;
		try {
			String grepTextST = "\"f\":\"" + msisdnTypingText + "\",\"t\":\"st\"";
			isSTReceived = grepTextInMqttChannel(grepTextST);
			//"\"f\":\"+917777775841\",\"t\":\"st\"";
			
			return isSTReceived;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isSTReceived;
	}
	
	public boolean isEndTypingNotifReceived(String msisdnTypingText){
		boolean isETReceived = false;
		try {
			String grepTextET = "\"f\":\"" + msisdnTypingText + "\",\"t\":\"et\"";
			isETReceived = grepTextInMqttChannel(grepTextET);
			//"\"f\":\"+917777775841\",\"t\":\"st\"";
			
			return isETReceived;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isETReceived;
	}
	
	public void deliverMessage(String msisdn){
		try {
			getUserOnline(msisdn);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void generateReadMessagePacket(String msisdnTo , String msisdnFrom){
		try {
	          JsonObject jsonData = new JsonObject();
	          jsonData.addProperty("t", "mr");
	          jsonData.addProperty("to", msisdnTo);
	          jsonData.addProperty("d", "");
	          mqttConnectionSender.publish(uid+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void isMessageReceived(){
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public boolean grepTextInMqttChannel(String text){
		try {
			boolean isTextPresent = false;
	        int waitTime = 0;
	        while(waitTime<20){
	            BlockingQueue<String> messages = queueHandlerSender.receivedMessages;
	            Object[] messageList = messages.toArray();
	            for(int i=0;i<messageList.length;i++){
	                if(messageList[i].toString().contains(text)){
	                    isTextPresent = true;
	                    System.out.println("wait time before success(sec): " + waitTime);
	                    return isTextPresent;
	                }
	            }
	            if(!isTextPresent){
	                waitTime++;
	                Thread.sleep(1000);
	            }

	        }
	        System.out.println("wait time before failure(sec): " + waitTime);
	        return isTextPresent;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	
	public void closeConnection(){
		try {
			mqttConnectionSender.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
