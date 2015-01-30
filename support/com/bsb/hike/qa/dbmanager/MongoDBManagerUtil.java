package com.bsb.hike.qa.dbmanager;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.bsb.hike.common.HikeConstants;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class MongoDBManagerUtil
{
    private Mongo mongo;
    private DB userDB;

    private static MongoDBManagerUtil mongoDBManager;
    List<ServerAddress> mongoReplicaProps;
    public static final String MONGO_GROUP_USERS = "users";
    public static final String MONGO_USER_GROUPS = "groups";
    public static final String MONGO_GROUP_ID = "gid";

    /*
     * This is for unit testing
     */
    public static MongoDBManagerUtil getInstance(String dbPrefix)
    {
        if (mongoDBManager == null)
        {
            synchronized (MongoDBManagerUtil.class)
            {
                if (mongoDBManager == null)
                {
                    mongoDBManager = new MongoDBManagerUtil(dbPrefix);
                }
            }
        }
        return mongoDBManager;
    }

    private MongoDBManagerUtil(String dbPrefix) {
        init(dbPrefix);
    }
    
    public static MongoDBManagerUtil getInstance()
    {
        return getInstance("");
    }

    private void init(String dbPrefix)
    {
        try
        {
            mongoReplicaProps = new ArrayList<ServerAddress>(); 
            //mongoReplicaProps.add(new ServerAddress("mongodb1.qa.sg1.hike.in", 27017));
        	mongoReplicaProps.add(new ServerAddress("10.0.1.253", 27017));
        	mongoReplicaProps.add(new ServerAddress("10.0.1.253", 27017));
            MongoOptions options = new MongoOptions();
            options.autoConnectRetry = true;
            options.readPreference = ReadPreference.primaryPreferred();
            options.setThreadsAllowedToBlockForConnectionMultiplier(50);
            options.setConnectionsPerHost(100);
            options.slaveOk = true;
            mongo = new Mongo(mongoReplicaProps,options);
            userDB = mongo.getDB("userdb");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public List<String> getListOfDB(){
    	return mongo.getDatabaseNames();
    }
    
    public Mongo getMongo(){
    	return mongo;
    }
    

    public int messageEntryInDB(String uid){
  	  
    	  List<String> list = getListOfDB();		
    	    for (String messagedb : list) {
    	    	if(messagedb.startsWith("message")){
    	    		DB messageDB = getMongo().getDB(messagedb);
    	            DBCollection collection = messageDB.getCollection("messages");
    	            BasicDBObject query = new BasicDBObject();
    	            query.put("uid", uid);
    	            DBCursor result = collection.find(query);
    	            if (result!= null && result.hasNext()){
    	            {          
    	            	System.out.println("DB for messsage==>>" + messagedb);
    	            	return result.count();
    	            	}
    	            }  
    	    	}
    	    }
    		  
    		  return 0;
    	  }
    
    public List<String> getGroupsOfUser(String msisdn)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
        BasicDBObject query = new BasicDBObject(MONGO_GROUP_USERS+"."+HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject(MONGO_GROUP_ID, 1);
        DBCursor cursor = collection.find(query, fields);
        List<String> list=new ArrayList<String>();
        while(cursor.hasNext())
            list.add((String)cursor.next().get(MONGO_GROUP_ID));
        return list;
    }
    
    public String getUidFromMsisdn(String msisdn , DB userdb)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append("reward_token", 1));
        if ((result == null) || !result.containsField("reward_token"))
        {
            return null;
        }

        return result.get("reward_token").toString();
    }

    
    public int getFavState(String m_clientId, String msisdn){
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        String key=HikeConstants.MONGO_FAVORITES + "." + msisdn;
        BasicDBObject selector = new BasicDBObject().append(key, 1);
        BasicDBObject query = new BasicDBObject().append(HikeConstants.MSISDN, m_clientId);
        DBObject result = collection.findOne(query,selector);
        if(result == null)
                return -1; // no record found
        Object obj=result.get(HikeConstants.MONGO_FAVORITES);
        if(obj == null) //"favorites" is null
                return -1;

        Object  obj2= ((BasicDBObject)obj).get(msisdn);
        if(obj2 == null)
                return -1; // "favorites" : { "x" :y } [ but not "msisdn" ]
        if( ((BasicDBObject) obj2).get("pending")==null)
                return 0; // "msisdn" :{ } 
        boolean pending = (Boolean)((BasicDBObject)obj2).get("pending");
            if(!pending)
            {
                return 2; // "msisdn" {"pending" : false}
            }
            else
                return 1; //"msisdn" {"pending" : true}
       
    }
    
   
    public int getFavoriteState(String msisdn1, String msisdn2)
    {
        int forwardState = getFavState(msisdn1,msisdn2);
        int backwardState = getFavState(msisdn2,msisdn1);
        
        if(forwardState == 0 && backwardState == 0 ){
            return 2; //two way friendship
        }
        else if(forwardState == 0 && backwardState == 1){
            return 1; //msisdn1 has sent the request and still waiting for msisdn2 to respond (When AF Packet is sent by msisdn1)
        }
        else if(forwardState == 0 && backwardState == 2){
            return -1; //msisdn1 has sent the request but msisdn2 has tapped on "Not Now" (When PF/RF packet is sent by msisdn2)
        }
        else if(forwardState == 1 && backwardState == 0){
            return 3; //msisdn2 has sent the request and still waiting for msisdn1 to respond (When AF Packet is sent by msisdn2)
        }
        else if(forwardState == 2 && backwardState == 0){
            return -3; //msisdn2 has sent the request but  msisdn1  has tapped on "Not Now" (When PF/RF packet is sent by  msisdn1 )
        }
        
        return 0 ; // default action (I do not think they exchanged any packets for friendship )
        
    }
         
    
    public String getCurrentToken(String msisdn , DB userDB)
    {
        try{
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        
        BasicDBList deviceList = (BasicDBList) result.get("devices");
        BasicDBObject device = (BasicDBObject) deviceList.get(0);     
        return  device.get("token").toString();
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getRewardToken(String msisdn , DB userDB)
    {
        try{
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        
        String rewardToken = result.get("reward_token").toString();
        return rewardToken;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getFavorites(String msisdn , DB userDB)
    {
        try{
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        
        String rewardToken = result.get("favorites").toString();
        return rewardToken;
        }
        catch(Exception e){
            return null;
        }
    }

    
    public String getInvitedContacts(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        System.out.println(result.get("invited").toString());
        return result.get("invited").toString();
    }

    public String getDetailsFromMsisdn(String msisdn , String field)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append(field, 1));
        if ((result == null) || !result.containsField(field))
        {
            return null;
        }

        return result.get(field).toString();
    }

    
    @SuppressWarnings("unchecked")
	public Map<String,List<Map<String,String>>> getAddressbook(String msisdn, DB db) {
        DBCollection collection = db.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        DBObject result = collection.findOne(new BasicDBObject(HikeConstants.MSISDN,msisdn));
        
        if(result == null || result.get("addressbook") == null) {
            return null;
        }
        Map<String,List<Map<String,String>>> addressBookUploadedList = (Map<String, List<Map<String, String>>>) result.get("addressbook");
        System.out.println(addressBookUploadedList);
        System.out.println(addressBookUploadedList.size());
        return (Map<String,List<Map<String,String>>>)result.get("addressbook");
    }
    
    public enum FAVORITE_STATE
    {
        ADDED(null),
        PENDING(Boolean.TRUE),
        REMOVED(Boolean.FALSE);
        
        Boolean booleanNotation;
        private FAVORITE_STATE(Boolean booleanNotation)
        {
            this.booleanNotation = booleanNotation;
        }
        
        public Boolean getBooleanNotation()
        {
            return booleanNotation;
        }
    };
    
    public void modifyFavorites(String m_clientId, String msisdn, boolean add, boolean isHikeUser,DB userDB)
    {
        FAVORITE_STATE state = add ? FAVORITE_STATE.ADDED : FAVORITE_STATE.REMOVED;
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        modifyFavorites(collection, m_clientId, msisdn, state);
        if(add)
        {
            collection = isHikeUser ? userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION) : userDB.getCollection(HikeConstants.MONGO_NON_HIKERS_COLLECTION);
            modifyFavorites(collection, msisdn, m_clientId, FAVORITE_STATE.PENDING);
        }
    }
    
    private void modifyFavorites(DBCollection collection, String m_clientId, String msisdn, FAVORITE_STATE favoriteState)
    {
        BasicDBObject query = new BasicDBObject(HikeConstants.MSISDN, m_clientId);
        String key = HikeConstants.MONGO_FAVORITES + "." + msisdn;
        String op = "$set";
        BasicDBObject value = new BasicDBObject();
        if(favoriteState == FAVORITE_STATE.PENDING) 
        {
            value.append("pending", true);
            DBObject clause1 = new BasicDBObject(key, new BasicDBObject("$exists", false));
            DBObject clause2 = new BasicDBObject(key, new BasicDBObject("pending", false));
            BasicDBList or = new BasicDBList();
            or.add(clause1);
            or.add(clause2);
            query.append("$or", or);
        }
        else if(favoriteState == FAVORITE_STATE.REMOVED)
        {
            value.append("pending", false);
            query.append(key, new BasicDBObject("$exists", true));
        }
        
        BasicDBObject update = new BasicDBObject(op,
												 new BasicDBObject(key, value));
        collection.update(query, update, true, false);
    }
    

    
    public String getAccountConnected(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append("accounts", 1));
        if ((result == null) || !result.containsField("accounts"))
        {
            return null;
        }

        return result.get("accounts").toString();
    }

    public List<String> getGroups(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject(HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject("groups", 1);
        DBObject value = collection.findOne(query, fields);
        if (value == null)
        {
            return Collections.EMPTY_LIST;
        }

        List<String> elements = (List<String>) value.get("groups");
        if (elements != null)
        {
            return elements;
        }
        else
        {
            return Collections.EMPTY_LIST;
        }
    }
    
    public String getBlockedUserList(String msisdnBlocker,DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.COMMON_BLOCKLIST);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdnBlocker);
        DBObject result = collection.findOne(query);
        if ((result == null) || !result.containsField("blocklist") || result.get("blocklist").toString().equals("[ ]"))
        {
            return null;
        }

        return result.get("blocklist").toString();
    }
    
    public List<String> getGroupsOfUser(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
        BasicDBObject query = new BasicDBObject(MONGO_GROUP_USERS+"."+HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject(MONGO_GROUP_ID, 1);
        DBCursor cursor = collection.find(query, fields);
        List<String> list=new ArrayList<String>();
        while(cursor.hasNext())
            list.add((String)cursor.next().get(MONGO_GROUP_ID));
        return list;
    }
   

}
