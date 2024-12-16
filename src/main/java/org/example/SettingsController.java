// מחלקה המשתמשת בSPRING BOOT על מנת לנהל בקשות POST מהצד לקוח לכיוון הצד שרת
package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
    private final Main main;

    @Autowired
    public SettingsController(Main main) {
        this.main = main;
    }

    //ניהול בקשות לקוח בדף המפה
    @PostMapping("/settings")
    public String saveSettings(@RequestBody SettingsRequest settingsRequest) {
        try {
            int[] settings = settingsRequest.getSettings();// קבלת מידע של ההרצה שאותו הגדיר הלקוח
            String ip = settingsRequest.getIp();// קבלת כתובת הIP של הלקוח
            logger.info("Received graph data settings: {}", settings);
            logger.info(ip);
            main.GraphRun(settings, ip);// קריאה לפעולה האחראית על הרצת המופע
        } catch (Exception e) {
            logger.error("Error processing graph data settings: {}", e.getMessage());
            return "Error processing settings";
        }

        return "Settings saved and processed successfully";
    }

    @PostMapping("/settingsmap")
    public String saveSettingsMap(@RequestBody SettingsRequestMap settingsRequestMap) {
        try {
            double[] settings = settingsRequestMap.getSettings();// קבלת מידע של ההרצה שאותו הגדיר הלקוח
            String ip = settingsRequestMap.getIp();// קבלת כתובת הIP של הלקוח
            logger.info("Received map data settings: {}", settings);
            logger.info(ip);
            main.MapRun(settings, ip);// קריאה לפעולה האחראית על הרצת המופע
        } catch (Exception e) {
            logger.error("Error processing map data settings: {}", e.getMessage());
            return "Error processing settings";
        }

        return "Settings saved and processed successfully";
    }

    @PostMapping("/redisgraph")
    public String[] postRedisGraph(@RequestBody GraphRedisRequset graphRedisRequset) {
        try {
            Jedis jedis = RedisConnection.getJedis();
            String ip = graphRedisRequset.getIp();
            logger.info(ip);
            String points = jedis.get("random#" + ip);
            String data = jedis.get("dataarr#" + ip);
            logger.info(points+"----"+data);
            jedis.close();
            return new String[]{points, data};
        } catch (Exception e) {
            logger.error("Error getting data from redis graph:{}", e.getMessage());
            return new String[]{"Error getting data from redis graph{}", e.getMessage()};
        }
    }

    @PostMapping("/redismap")
    public String[] postRedisMap(@RequestBody GraphRedisRequset graphRedisRequset) {
        try {
            Jedis jedis = RedisConnection.getJedis();
            String ip = graphRedisRequset.getIp();
            String points = jedis.get("maprandom#" + ip);
            String data = jedis.get("dataarrmap#" + ip);
            jedis.close();
            return new String[]{points, data};
        } catch (Exception e) {
            logger.error("Error getting data from redis map:{}", e.getMessage());
            return new String[]{"Error getting data from redis map{}", e.getMessage()};
        }
    }
}
