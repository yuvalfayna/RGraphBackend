// מחלקה המשתמשת בSPRING BOOT על מנת לנהל בקשות POST מהצד לקוח לכיוון הצד שרת
package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;


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
    public String[] postRedisGraph(@RequestBody RedisRequset RedisRequset) {
        try {
            Jedis jedis = RedisConnection.getJedis();
            String ip = RedisRequset.getIp();
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
    public String[] postRedisMap(@RequestBody RedisRequset RedisRequset) {
        try {
            Jedis jedis = RedisConnection.getJedis();
            String ip = RedisRequset.getIp();
            String points = jedis.get("maprandom#" + ip);
            String data = jedis.get("dataarrmap#" + ip);
            jedis.close();
            return new String[]{points, data};
        } catch (Exception e) {
            logger.error("Error getting data from redis map:{}", e.getMessage());
            return new String[]{"Error getting data from redis map{}", e.getMessage()};
        }
    }

    @PostMapping("/mongodbgraph")
    public String postMongoDBGraph(){
        ArrayList<Document> arrayList=new ArrayList<Document>();
        MongoClient mongoClient = MongoDBConnection.getInstance();
        MongoDatabase MDB = mongoClient.getDatabase("RGraph");
        MongoCollection<Document> ArrayCollection = MDB.getCollection("arrays");
        Bson projectionFields = Projections.fields(
                Projections.include("array:", "data:","runtime:"),
                Projections.excludeId());
        try (MongoCursor<Document> mongoCursor = ArrayCollection.find()
                .projection(projectionFields)
                .sort(Sorts.descending("runtime:"))
            .iterator())
        {
            while (mongoCursor.hasNext()) {
                arrayList.add(mongoCursor.next());
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(arrayList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    @PostMapping("/mongodbmap")
    public String postMongoDBMap(){
        ArrayList<Document> arrayList=new ArrayList<Document>();
        MongoClient mongoClient = MongoDBConnection.getInstance();
        MongoDatabase MDB = mongoClient.getDatabase("RGraph");
        MongoCollection<Document> ArrayCollection = MDB.getCollection("maps");
        Bson projectionFields = Projections.fields(
                Projections.include("map:", "data:","runtime:"),
                Projections.excludeId());
        try (MongoCursor<Document> mongoCursor = ArrayCollection.find()
                .sort(Sorts.descending("runtime:"))
                .projection(projectionFields)
                .iterator()) {
            while (mongoCursor.hasNext()) {
                arrayList.add(mongoCursor.next());
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(arrayList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error on post data map";
        }
    }
}
