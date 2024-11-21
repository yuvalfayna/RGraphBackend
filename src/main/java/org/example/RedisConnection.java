// מחלקה האחראית על יצירת הCONNECTION עם הREDIS והכנסת המידע לREDIS
package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

public class RedisConnection {
    private static final ObjectMapper objectMapper = new ObjectMapper(); // לצורך כתיבת המידע בפורמט JSON
    private final JedisPool jedisPool; // אובייקט המשמש לניהול חיבורים עם הREDIS
    // קונסטרקטור של המחלקה היוצר חיבור עם הREDIS
    public RedisConnection() {
        String redisUrl = "redis://default:p7LS233UJKNl8F4eCSrb8OSWnluC9MLB@redis-19666.c16.us-east-1-3.ec2.redns.redis-cloud.com:19666";
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(poolConfig, redisUrl);
    }
    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף הגרף
    public static void InsertRedisGraph(int[][] arr, double[][] dataarr, String ip, Jedis jedis, Pipeline pipeline) {
        try (jedis) {
            for (int i = 0; i < arr.length; i++) {
                List<DataPoint> data = new ArrayList<>();
                data.add(new DataPoint(arr[i][0], arr[i][1], arr[i][2]));
                String jsonArray = objectMapper.writeValueAsString(data);
                pipeline.set(ip + "#random" + i, jsonArray);
                pipeline.expire(ip + "#random" + i, arr[i][2]);
            }
            String jsonDataArray = objectMapper.writeValueAsString(dataarr);
            pipeline.set(ip + "#dataarr", jsonDataArray);
            pipeline.expire(ip + "#dataarr", 60);
            pipeline.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף המפה
    public static void InsertRedisMap(double[][] arr, double[][] dataarr, String ip, Jedis jedis, Pipeline pipeline) {
        try (jedis) {
            for (int i = 0; i < arr.length; i++) {
                List<MapPoint> data = new ArrayList<>();
                data.add(new MapPoint(arr[i][0], arr[i][1], arr[i][2]));
                String jsonArray = objectMapper.writeValueAsString(data);
                pipeline.set(ip + "#maprandom" + i, jsonArray);
                pipeline.expire(ip + "#maprandom" + i, (int) arr[i][2]);
            }
            String jsonDataArray = objectMapper.writeValueAsString(dataarr);
            pipeline.set(ip + "#dataarrmap", jsonDataArray);
            pipeline.expire(ip + "#dataarrmap", 60);
            pipeline.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }

    }
}