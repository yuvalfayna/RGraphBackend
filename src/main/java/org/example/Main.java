package org.example;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    public void GraphRun(int[] sarr,String ip) {
        int[][] arr = Randomizer.GraphRandomizer(sarr);
        double [][]dataarr=PointAnalysis.GraphAnalysisExporter(arr);
        RedisConnection redisConnection=new RedisConnection();
        Jedis jedis=redisConnection.getJedis();
        Pipeline pipeline = jedis.pipelined();
        RedisConnection.InsertRedisGraph(arr,dataarr,ip,jedis,pipeline);
        MongoDBConnection.InsertMongoGraph(arr,dataarr);
    }

    public void MapRun(double[]sarr,String ip){
        double [][]arr=Randomizer.MapRandomizer(sarr);
        double[][] dataarr=MapAnalysis.MapAnalysisExporter(arr);
        RedisConnection redisConnection=new RedisConnection();
        Jedis jedis=redisConnection.getJedis();
        Pipeline pipeline = jedis.pipelined();
        RedisConnection.InsertRedisMap(arr,dataarr,ip,jedis,pipeline);
        MongoDBConnection.InsertMongoMap(arr,dataarr);
    }


    @Override
    public void run(String... args) throws Exception {
        // Empty default run method, as we'll trigger the logic from the controller
    }
}
