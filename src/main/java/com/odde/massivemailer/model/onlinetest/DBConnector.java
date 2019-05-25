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
    static DBConnector dbConnector = null;
    MongoDatabase database;

    static DBConnector instance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public DBConnector() {
        MongoClient mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("kyouha_unit_test").withCodecRegistry(getCodecRegistry());
    }

    public static MongoCollection<Category> getCategoryMongoCollection() {
        return instance().getMongoCollection(Category.class, "categories");
    }

    private <T>MongoCollection<T> getMongoCollection(Class<T> klass, String name) {
        return database.getCollection(name, klass);
    }

    private static CodecRegistry getCodecRegistry() {
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(new Category.IntegerCodec());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        return CodecRegistries.fromRegistries(codecRegistry, defaultCodecRegistry);
    }

    public static void resetAll() {
        getCategoryMongoCollection().deleteMany(new BasicDBObject());
    }
}