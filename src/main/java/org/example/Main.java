// המחלקה האחראית על תהליך של בניית המופע
package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;


@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // פעולה האחראית על יצירת מופע בדף הגרף
    public void GraphRun(int[] sarr, String ip) {
        int[][] arr = Randomizer.GraphRandomizer(sarr);// יצירת מערך המכיל ערכים בטווח הערכים שהלקוח הגדיר
        AnalyzeGraphInstance analyzeGraphInstance = new AnalyzeGraphInstance(arr);// יצירת אובייקט המכיל נתונים מתמטיים על המופע
        double[][] dataarr = analyzeGraphInstance.toArray();
        Jedis jedis = RedisConnection.getJedis();
        RedisConnection.InsertRedisGraph(arr, dataarr, ip, jedis);// הכנסת המידע לתוך הREDIS
        MongoDBConnection.InsertMongoGraph(arr, dataarr);// הכנסת המידע לתוך הMONGODB
    }
    // פעולה האחראית על יצירת מופע בדף המפה
    public void MapRun(double[] sarr, String ip) {
        double[][] arr = Randomizer.MapRandomizer(sarr);// יצירת מערך המכיל ערכים בטווח הערכים שהלקוח הגדיר
        AnalyzeMapInstance analyzeMapInstance =new AnalyzeMapInstance(arr);// יצירת אובייקט המכיל נתונים מתמטיים על המופע
        double[][] dataarr = analyzeMapInstance.ToArray();
        Jedis jedis = RedisConnection.getJedis();
        RedisConnection.InsertRedisMap(arr, dataarr, ip, jedis);// הכנסת המידע לתוך הREDIS
        MongoDBConnection.InsertMongoMap(arr, dataarr);// הכנסת המידע לתוך הMONGODB
    }


    @Override
    public void run(String... args) throws Exception {
        // Empty default run method, as we'll trigger the logic from the controller
    }
}
