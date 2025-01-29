// מחלקה האחראית על יצירת הCONNECTION עם הREDIS והכנסת המידע לREDIS
package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

public class RedisConnection {
    private static final ObjectMapper objectMapper = new ObjectMapper(); // לצורך כתיבת המידע בפורמט JSON
    public static JedisPoolConfig poolConfig = new JedisPoolConfig();
    public static String redisUrl = "redis://default:p7LS233UJKNl8F4eCSrb8OSWnluC9MLB@redis-19666.c16.us-east-1-3.ec2.redns.redis-cloud.com:19666";
    public static JedisPool jedisPool = new JedisPool(poolConfig, redisUrl); // אובייקט המשמש לניהול חיבורים עם הREDIS

    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף הגרף
    public static void InsertRedisGraph(int[][] arr, double[][] dataarr, String ip, Jedis jedis) {
        try (jedis) {
            List<DataPoint> data = new ArrayList<>();
            int max = -1;
            for (int[] ints : arr) {
                data.add(new DataPoint(ints[0], ints[1], ints[2]));
                if (ints[2] > max) {
                    max = ints[2];
                }
            }
            data.sort((a,b)->{return Integer.compare(b.getSeconds(), a.getSeconds());});
            String jsonArray = objectMapper.writeValueAsString(data);
            jedis.set("random#" + ip, jsonArray);
            jedis.expire("random#" + ip, max);

            String jsonDataArray = objectMapper.writeValueAsString(dataarr);
            jedis.set("dataarr#" + ip, jsonDataArray);
            jedis.expire("dataarr#" + ip, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף המפה
    public static void InsertRedisMap(double[][] arr, double[][] dataarr, String ip, Jedis jedis) {
        try (jedis) {
            List<MapPoint> data = new ArrayList<>();
            int max = -1;
            for (double[] doubles : arr) {
                data.add(new MapPoint(doubles[0], doubles[1], doubles[2]));
                if (doubles[2] > max) {
                    max = (int) doubles[2];
                }
            }
            data.sort((a,b)->{return Double.compare(b.getSeconds(), a.getSeconds());});
            String jsonArray = objectMapper.writeValueAsString(data);
            jedis.set("maprandom#" + ip, jsonArray);
            jedis.expire("maprandom#" + ip, max);
            String jsonDataArray = objectMapper.writeValueAsString(dataarr);
            jedis.set("dataarrmap#" + ip, jsonDataArray);
            jedis.expire("dataarrmap#" + ip, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}