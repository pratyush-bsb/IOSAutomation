package com.bsb.hike.qa.dbmanager;

import java.util.Map;

import com.bsb.hike.qa.constants.AutomationConstants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisServiceManagerUtil {
	private JedisPool redisPool;
	public static final int DEFAULT_TIMEOUT = 5000;
	private static volatile RedisServiceManagerUtil redisServiceManager = null;


    public static RedisServiceManagerUtil getInstance()
    {
        if (redisServiceManager == null)
        {
            synchronized(RedisServiceManagerUtil.class) {
                if(redisServiceManager == null) {
                    redisServiceManager = new RedisServiceManagerUtil(AutomationConstants.Redis_Staging_Host, AutomationConstants.Redis_Staging_Port, 0);
                }
            }
        }
        return redisServiceManager;
    }
    
    public RedisServiceManagerUtil(String redisHost, int redisPort, int dbnum)
    {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxActive(100);
        poolConfig.setMaxIdle(100);
        redisPool = new JedisPool(poolConfig, redisHost, redisPort, DEFAULT_TIMEOUT, null, dbnum);
    }
    
	public String generateUserPin(String msisdn){	    
		   String pin =  redisPool.getResource().get("pincodes-"+msisdn);
		   return pin;
	}
	
	public String getUserUid(String msisdn){
	    return RedisServiceManagerUtil.getInstance().hget("msisdns", msisdn);
	}
	
	public String getVMN(String from,String to){
		String value=redisPool.getResource().hget("comviva::"+to, from);
		System.out.println(value.split("::")[1]);
		int index=Integer.parseInt(value.split("::")[0])-1;
		return redisPool.getResource().lindex("scCOMVIVA", index);
	}
    public Map<String, String> hgetAll(String key)
    {
        Jedis redis = null;
        try
        {
            redis = redisPool.getResource();
            return redis.hgetAll(key);
        }
        finally
        {
            if (redis != null)
            {
                redisPool.returnResource(redis);
            }
        }
    }
    
    public String hget(String key, String field)
    {
        Jedis redis = null;
        try
        {
            redis = redisPool.getResource();
            String val = redis.hget(key, field);
            return val;
        }
        finally
        {
            if (redis != null)
            {
                redisPool.returnResource(redis);
            }
        }
    }
    
    public Long hset(String key, String field, String value)
    {
        Jedis redis = null;
        try
        {
            redis = redisPool.getResource();
            return redis.hset(key, field, value);
        }
        finally
        {
            if (redis != null)
            {
                redisPool.returnResource(redis);
            }
        }
    }
    
    public Long hdel(String key, String... field)
    {
        Jedis redis = null;
        try
        {
            redis = redisPool.getResource();
            return redis.hdel(key, field);
        }
        finally
        {
            if (redis != null)
            {
                redisPool.returnResource(redis);
            }
        }
    }
    
    public String get(String keyName)

    {

        Jedis redis = null;

        try

        {

            redis = redisPool.getResource();

            return redis.get(keyName);

        }

        finally

        {

            if (redis != null)

            {

                redisPool.returnResource(redis);

            }

        }

    }
    
    public String setKey(String field, String value)
    {
        Jedis redis = null;
        try
        {
            redis = redisPool.getResource();
            return redis.set(field, value);
        }
        finally
        {
            if (redis != null)
            {
                redisPool.returnResource(redis);
            }
        }
    }
    
}
