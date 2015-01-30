package com.bsb.hike.qa.apisupport;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.http.HTTPService;
import com.bsb.hike.http.HTTPService.HttpPostResponse;
import com.bsb.hike.mqtt.client.MqttConnection;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GroupChatMessageSupport extends BaseClass{
	QueueMessageHandler queueHandlerSender;
	HikeMqttConnection mqttConnectionSender;
	RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();
    HTTPService httpService = new HTTPService();
    private static final String JSONTYPE = "application/json";
	
	QueueMessageHandler queueHandlerRec;
	HikeMqttConnection mqttConnectionRec;
	
	String uidSender = "";
	String tokenSender = "";
	public static String groupId = "";
	public String[] msisdns = {"+912100077854","+912100003458","+912100093780","+912100093662","+912100037104","+912100041780","+912100068467","+912100033663","+912100070013","+912100066346","+912100079424","+912100084750","+912100068863","+912100016725","+912100037557","+912100019437","+912100042801","+912100019862","+912100064776","+912100052238","+912100055408","+912100055692","+912100059742","+912100008948","+912100095145","+912100004662","+912100011167","+912100060142","+912100021873","+912100061214","+912100080072","+912100075901","+912100001597","+912100070689","+912100082501","+912100080717","+912100077549","+912100055145","+912100066431","+912100085787","+912100099110","+912100092032","+912100046069","+912100070776","+912100072674","+912100055170","+912100070081","+912100031959","+912100047055","+912100056211","+912100093178","+912100035465","+912100029668","+912100008798","+912100045808","+912100073046","+912100094068","+912100018673","+912100065291","+912100067122","+912100037449","+912100051675","+912100047339","+912100099473","+912100088778","+912100045038","+912100028249","+912100017554","+912100066292","+912100045594","+912100092305","+912100069426","+912100099154","+912100082686","+912100077227","+912100037168","+912100023870","+912100069052","+912100018024","+912100021844","+912100041321","+912100011084","+912100045210","+912100015913","+912100036114","+912100095994","+912100001694","+912100079253","+912100011245","+912100092974","+912100069698","+912100009490","+912100036456","+912100096017","+912100070344","+912100067555","+912100069492","+912100062477","+912100094936","+912100080078","+912100099536","+912100054907","+912100078060","+912100057300","+912100039703","+912100065563","+912100068757","+912100051925","+912100047371","+912100095382"};
	public static void main(String[] args) throws Exception {
		GroupChatMessageSupport gc = new GroupChatMessageSupport();
		List<String> list = new ArrayList<String>();
		list.add("+915544332222");
		list.add("+919810771083");
//		list.add("+918375975893");
//		list.add("+919716549529");
		for(int counter=0 ; counter<50; counter++){
			list.add(gc.msisdns[counter]);
		}
		gc.createGroupAndSendMessage("+917838901391", list);	
//		gc.changeGroupNameByMember("+919810771083", "apiscript");	
//		gc.changeGroupProfile("+919716549529");
//		gc.addMemberToGroup("+919810771083", "+919711118690");	//Maybe anyone
//		gc.removeMemberFromGroup("+915544332222" , "+919716549529");	//owner remove
//		gc.groupCreatorLeavingGroup("+915544332222");
	}
	public void setPin(String groupId , String msisdn , String pinText){
		  try {
		  String uid = getuidFromMsisdn(msisdn);
		  String token = getTokenFromMsisdn(msisdn);
		  QueueMessageHandler queueHandler = new QueueMessageHandler();
		  HikeMqttConnection mqttConnection = new HikeMqttConnection(msisdn , queueHandler);

		  mqttConnection.connect(mqtthost , mqttport , uid , token);
		  Thread.sleep(100);
		  
		  JsonObject pin = new JsonObject();
		  pin.addProperty("pin", 1);
		  
		  JsonObject data = new JsonObject();
		  data.addProperty("ts", System.currentTimeMillis());
		  data.add("md", pin);
		  data.addProperty("i", 10);
		  data.addProperty("hm", pinText);
		  
		  JsonObject json = new JsonObject();
		  json.addProperty("to", groupId);
		  json.add("d", data);
		  json.addProperty("t", "m");
		  
		  mqttConnection.publish(uid+"/p",json.toString().getBytes(), QoS.AT_LEAST_ONCE);
		  Thread.sleep(1000);

		  mqttConnection.disconnect();
		} catch (Exception e) {
		e.printStackTrace();
		}

		  }
	  public void addMemberToGroup(String msisdnAddingMember , String msisdnToAdd){
		  try {
			  String uid = getuidFromMsisdn(msisdnAddingMember);
			  String token = getTokenFromMsisdn(msisdnAddingMember);
			  QueueMessageHandler queueHandler = new QueueMessageHandler();
			  HikeMqttConnection mqttConnection = new HikeMqttConnection(msisdnAddingMember , queueHandler);
			  System.out.println("groupid2: "+groupId);
			  
			  
			  mqttConnection.connect(mqtthost , mqttport , uid , token);
			  Thread.sleep(100);
			  
			  JsonArray jsonMembersToAdd = new JsonArray();
			  JsonObject nameAndMsisdn = new JsonObject();
			  nameAndMsisdn.addProperty("msisdn", msisdnToAdd) ;
			  nameAndMsisdn.addProperty("name", msisdnToAdd);
			  jsonMembersToAdd.add(nameAndMsisdn);

			  JsonObject json = new JsonObject();
			  json.addProperty("to", groupId);
			  json.addProperty("f", msisdnAddingMember);
			  json.add("d", jsonMembersToAdd);
			  json.addProperty("t", "gcj");

			  mqttConnection.publish(uid+"/p",json.toString().getBytes(), QoS.AT_LEAST_ONCE);
			  Thread.sleep(100);    
			  mqttConnection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public void removeMemberFromGroup(String msisdnCreator , String msisdnToRemove){
		  try {
			  String uid = getuidFromMsisdn(msisdnCreator);
			  String token = getTokenFromMsisdn(msisdnCreator);

			  QueueMessageHandler queueHandler = new QueueMessageHandler();
			  HikeMqttConnection mqttConnection = new HikeMqttConnection(msisdnCreator , queueHandler);

			  mqttConnection.connect(mqtthost , mqttport , uid , token);
			  Thread.sleep(100);

			  JSONObject data = new JSONObject();
			  JSONArray msisdns = new JSONArray();
			  msisdns.add(msisdnToRemove);
			  data.put("msisdns", msisdns);        
			  //String msisdnCreator = randomMSISDN1;
			  JsonObject jsonGCK = new JsonObject();
			  jsonGCK.addProperty("to", groupId);
			  jsonGCK.addProperty("f", msisdnCreator);
			  jsonGCK.addProperty("d", data.toString());
			  jsonGCK.addProperty("t", "gck");    
			  mqttConnection.publish(uid+"/p",jsonGCK.toString().getBytes(), QoS.AT_LEAST_ONCE);
			  Thread.sleep(100);
			  mqttConnection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	public void changeGroupNameByMember(String msisdn , String groupNewName){
		try {
			
	        String uidSender = getuidFromMsisdn(msisdn);
	    	String tokenSender = getTokenFromMsisdn(msisdn);
	    	
                String changeGroupName = "http://"+httphost+":"+httpport+"/v1/group/"+groupId + "/name"; 
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Cookie", "user="+tokenSender + "; UID=" + uidSender);
                JsonObject postData = new JsonObject();
                postData.addProperty("name", groupNewName);
                httpService.postDataToUrl(changeGroupName, JSONTYPE, headers, postData.toString() , "POST");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public String createGroupAndSendMessage(String msisdnGroupCreator , List<String> msisdnReceiverList) {
      try
      {  
    	  String uidSender = getuidFromMsisdn(msisdnGroupCreator);
    	  String tokenSender = getTokenFromMsisdn(msisdnGroupCreator);
    	  long timestamp = System.currentTimeMillis();        
    	  groupId = uidSender + ":" + timestamp;
          System.out.println("groupid1: "+groupId);
          String msisdnCreator = msisdnGroupCreator;
          JsonArray jsonMembersToAdd = new JsonArray();
          
          for(int i=0 ; i<msisdnReceiverList.size() ; i++){
              JsonObject nameAndMsisdn = new JsonObject();
              nameAndMsisdn.addProperty("msisdn", msisdnReceiverList.get(i)) ;
              nameAndMsisdn.addProperty("name", msisdnReceiverList.get(i));
              jsonMembersToAdd.add(nameAndMsisdn);
          }
          
          JsonObject groupName = new JsonObject();
          groupName.addProperty("name", "apitest");

          JsonObject jsonGCJ = new JsonObject();
          jsonGCJ.addProperty("to", groupId);
          //jsonGCJ.addProperty("f", msisdnCreator);
          jsonGCJ.add("d", jsonMembersToAdd);
          jsonGCJ.addProperty("t", "gcj");
          jsonGCJ.add("md", groupName);
          
    	  queueHandlerSender = new QueueMessageHandler();
    	  mqttConnectionSender = new HikeMqttConnection(msisdnGroupCreator , queueHandlerSender);
    	  
    	  
    	  mqttConnectionSender.connect(mqtthost , mqttport , uidSender , tokenSender);
          Thread.sleep(100);
          mqttConnectionSender.publish(uidSender+"/p",jsonGCJ.toString().getBytes(), QoS.AT_LEAST_ONCE);

          Thread.sleep(1000);
         
          long ts = System.currentTimeMillis();
          String message = "Hello ! I am the group intiator";
          JsonObject jsonDataObj = new JsonObject();
          jsonDataObj.addProperty("ts", ts); 
          jsonDataObj.addProperty("hm", message);
          jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));

          JsonObject jsonData = new JsonObject();
          jsonData.addProperty("to", groupId);
          jsonData.add("d", jsonDataObj);
          jsonData.addProperty("t", "m");
          mqttConnectionSender.publish(uidSender+"/p",jsonData.toString().getBytes(), QoS.AT_LEAST_ONCE);
          Thread.sleep(100);
          mqttConnectionSender.disconnect();
          return groupId;
      }
      catch (Exception e)
      {
    	  e.printStackTrace();
    	 
      }
	return groupId;
  }


  public void groupCreatorLeavingGroup(String msisdnCreator){
	  try {
		  String uid = getuidFromMsisdn(msisdnCreator);
		  String token = getTokenFromMsisdn(msisdnCreator);
		  QueueMessageHandler queueHandler = new QueueMessageHandler();
		  HikeMqttConnection mqttConnection = new HikeMqttConnection(msisdnCreator , queueHandler);

		  mqttConnection.connect(mqtthost , mqttport , uid , token);
		  Thread.sleep(100);

		  JsonObject json = new JsonObject();
		  json.addProperty("to",groupId);
		  json.addProperty("f", msisdnCreator);
		  json.addProperty("d", msisdnCreator);
		  json.addProperty("t", "gcl");
		  mqttConnection.publish(uid+"/p",json.toString().getBytes(), QoS.AT_LEAST_ONCE);
		  Thread.sleep(100);

		  mqttConnection.disconnect();
          
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  
  public boolean changeGroupProfile(String msisdnChangingAvatar) throws Exception{
	  try {
		  String changeGroupAvatarUrl = "http://"+httphost+":"+httpport+"/v1/group/"+groupId + "/avatar"; 
		  String uid = getuidFromMsisdn(msisdnChangingAvatar);
		  String token = getTokenFromMsisdn(msisdnChangingAvatar);
	      Map<String,String> headers = new HashMap<String,String>();
	      headers.put("Cookie", "user="+token + "; UID=" + uid);
	      //String workingDir = AutomationConstants.WorkingDir;
	      HttpPostResponse response = httpService.postFileToURL(changeGroupAvatarUrl, "", headers, "images.jpeg");   
	      
	      if(response.responseCode==200)
	    	  return true;
	      else
	    	  return false;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;

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



