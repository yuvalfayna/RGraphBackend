// מחלקה האחראית על יצירת הCONNECTION עם הMONGODB והכנסת המידע לMONGODB
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

    private static final ObjectMapper objectMapper = new ObjectMapper();// לצורך כתיבת המידע בפורמט JSON
    private static MongoClient mongoClient = MongoClients.create("mongodb+srv://yovalfayna98:lPpAMaj1wiYxSIoH@mdb.t9lvb.mongodb.net/?retryWrites=true&w=majority&appName=MDB");


    private MongoDBConnection() {
    }
    // יצירת החיבור הMONGODB
    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb+srv://yovalfayna98:lPpAMaj1wiYxSIoH@mdb.t9lvb.mongodb.net/?retryWrites=true&w=majority&appName=MDB&tls=true");
        }
        return mongoClient;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף הגרף
    public static void InsertMongoGraph(int[][] arr, double[][] dataarr) {
        try {
            MongoDatabase MDB = mongoClient.getDatabase("RGraph");
            MongoCollection<Document> ArrayCollection = MDB.getCollection("arrays");
            String jarr = objectMapper.writeValueAsString(arr);
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Jerusalem"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Document arrayDoc = new Document("runtime:", formattedDateTime).append("array:", jarr);
            String jdarr = objectMapper.writeValueAsString(dataarr);
            arrayDoc.append("data:", jdarr);
            ArrayCollection.insertOne(arrayDoc);

        } catch (Exception e) {
            e.printStackTrace();  // Print the actual error for debugging
            throw new Error("Connection or insert failed");
        }
    }
    // פעולה האחראית על הכנסת מידע של מופע לתוך הרדיס בדף המפה
    public static void InsertMongoMap(double[][] arr, double[][] dataarr) {
        try {
            MongoDatabase MDB = mongoClient.getDatabase("RGraph");
            MongoCollection<Document> ArrayCollection = MDB.getCollection("maps");
            String jarr = objectMapper.writeValueAsString(arr);
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Jerusalem"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Document arrayDoc = new Document("runtime:", formattedDateTime).append("map:", jarr);
            String jdarr = objectMapper.writeValueAsString(dataarr);
            arrayDoc.append("data:", jdarr);
            ArrayCollection.insertOne(arrayDoc);

        } catch (Exception e) {
            e.printStackTrace();  // Print the actual error for debugging
            throw new Error("Connection or insert failed");
        }
    }
}
