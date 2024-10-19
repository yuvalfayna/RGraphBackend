package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

public class RedisConnection {
    private JedisPool jedisPool;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RedisConnection() {
        String redisUrl = "redis://default:p7LS233UJKNl8F4eCSrb8OSWnluC9MLB@redis-19666.c16.us-east-1-3.ec2.redns.redis-cloud.com:19666";
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(poolConfig, redisUrl);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }

    }

    public static void InsertRedis(int[][]arr,double[][]dataarr,Jedis jedis,Pipeline pipeline){
        try (jedis) {
            for (int i = 0; i < arr.length; i++) {
                List<DataPoint> data = new ArrayList<>();
                data.add(new DataPoint(arr[i][0], arr[i][1], arr[i][2]));
                String jsonArray = objectMapper.writeValueAsString(data);
                pipeline.set("random" + i, jsonArray);
                pipeline.expire("random" + i, arr[i][2]);
            }
            String jsonDataArray=objectMapper.writeValueAsString(dataarr);
            pipeline.set("dataarr",jsonDataArray);
            pipeline.expire("dataarr", 60);
            pipeline.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}