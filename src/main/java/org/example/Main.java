package org.example;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.databind.ObjectMapper;




@SpringBootApplication
public class Main implements CommandLineRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public void run(int[] sarr) {
        RedisConnection redisConnection=new RedisConnection();
        Jedis jedis=redisConnection.getJedis();
        Pipeline pipeline = jedis.pipelined();
        int[][] arr = Randomizer.Randomizer(sarr);
        RedisConnection.InsertRedis(arr,jedis,pipeline);
        MongoDBConnection.InsertMongo(arr);

    }
    @Override
    public void run(String... args) throws Exception {
        // Empty default run method, as we'll trigger the logic from the controller
    }
}
