package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MongoDBConnection {

    private static MongoClient mongoClient = MongoClients.create("mongodb+srv://yovalfayna98:lPpAMaj1wiYxSIoH@mdb.t9lvb.mongodb.net/?retryWrites=true&w=majority&appName=MDB");;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    // Private constructor to prevent instantiation
    private MongoDBConnection() {}

    // Get the MongoClient instance
    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb+srv://yovalfayna98:lPpAMaj1wiYxSIoH@mdb.t9lvb.mongodb.net/?retryWrites=true&w=majority&appName=MDB&tls=true");
        }
        return mongoClient;
    }

    // Close the MongoClient connection (optional)
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    public static void InsertMongo(int[][]arr){
        try {
            MongoDatabase MDB = mongoClient.getDatabase("RGraph");
            MongoCollection<Document> ArrayCollection = MDB.getCollection("arrays");
            String jarr = objectMapper.writeValueAsString(arr);
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Jerusalem"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss");
            String formattedDateTime = now.format(formatter);
            Document arrayDoc = new Document("runtime:", formattedDateTime).append("array:", jarr);
            ArrayCollection.insertOne(arrayDoc);

        } catch (Exception e) {
            e.printStackTrace();  // Print the actual error for debugging
            throw new Error("Connection or insert failed");
        }
    }
}
