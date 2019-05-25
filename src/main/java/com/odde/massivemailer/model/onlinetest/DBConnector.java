package com.odde.massivemailer.model.onlinetest;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public class DBConnector {
    public static MongoCollection<Category> getCategoryMongoCollection() {
        MongoCollection<Category> categories = getCollection("categories");
        MongoClients.create();
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(new Category.IntegerCodec());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry codecRegistry1 = CodecRegistries.fromRegistries(codecRegistry, defaultCodecRegistry);
        return categories.withCodecRegistry(codecRegistry1);
    }

    private static MongoCollection<Category> getCollection(String name) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("kyouha_unit_test");
        return database.getCollection(name, Category.class);
    }

    public static void resetAll() {
        getCategoryMongoCollection().deleteMany(new BasicDBObject());
    }
}