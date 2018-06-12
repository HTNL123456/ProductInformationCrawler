/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.DAO;

import com.dineshonjava.thongtinnhanhang.crawler.POJO.ProductInformation;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.UnknownHostException;

/**
 *
 * @author Huy Thinh
 */
public class MongoDBService {

    private static String DB_HOSTNAME;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_NAME;

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private static final MongoDBService instance = new MongoDBService();

    private MongoDBService() {
        InputStream inputStream = null;
        try {
            out.println("\nStart get connection information... ");
            Properties prop = new Properties();
            String propFileName = "db.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            DB_HOSTNAME = prop.getProperty("DB_HOSTNAME");
            DB_USER = prop.getProperty("DB_USER");
            DB_PASSWORD = prop.getProperty("DB_PASSWORD");
            DB_NAME = prop.getProperty("DB_NAME");

        } catch (Exception e) {
            out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        out.println("\nGet connection information successful... ");

        MongoCredential credential = MongoCredential.createCredential(DB_USER, DB_NAME, DB_PASSWORD.toCharArray());
        List<MongoCredential> credentialArrayList = new ArrayList<MongoCredential>();
        credentialArrayList.add(credential);

        try {
            out.println("\nStart connection...");
            this.mongoClient = new MongoClient(new ServerAddress(DB_HOSTNAME));
        } catch (Exception e) {
            out.println("\nConnection failed...");
            e.printStackTrace();
        }
        out.println("\nConnection successful...");

        try {
            out.println("\nStart get database...");
            this.db = this.mongoClient.getDatabase(DB_NAME);
        } catch (Exception e) {
            System.out.println("\nGet database failed...");
            e.printStackTrace();
        }
        out.println("\nGet database successful...");

        boolean hasCollection = false;

        try {
            out.println("\nGet database collection...");
//            MongoCursor<String> iterator = this.db.listCollectionNames().iterator();
//
//            while (iterator.hasNext()) {
//                if (iterator.next().equals("ProductInformation")) {
//                    hasCollection = true;
//                    System.out.println("\nGet database collection successfull...");
//                    break;
//                }
//            }
            this.collection = this.db.getCollection("ProductInformation");
            out.println("\nGet database collection successfull...");
        } catch (Exception e) {
            out.println("\nGet database collection failed...");
            e.printStackTrace();
        }

        // If not found collection
//        if (!hasCollection) {
//            this.db.createCollection("ProductInformation");
//        }
//        this.collection = this.db.getCollection("ProductInformation");
        System.out.println("\nFinish initialize database...");
    }

    public static MongoDBService getInstance() {
        return instance;
    }

    public void insertProductInformation(ProductInformation productInformation) {
        this.collection.insertOne(productInformation.getDocument());
    }
}
