package com.odde.massivemailer.model.onlinetest;

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

    public static DBConnector instance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public DBConnector() {
        MongoClient mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("kyouha_unit_test").withCodecRegistry(getCodecRegistry());
    }

    public <T>MongoCollection<T> getMongoCollection(Class<T> klass, String name) {
        return database.getCollection(name, klass);
    }

    private static CodecRegistry getCodecRegistry() {
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(
                new Category.CategoryCodec(),
                new QuestionOption.QuestionOptionCodec());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        return CodecRegistries.fromRegistries(codecRegistry, defaultCodecRegistry);
    }

    public static void resetAll() {
        instance().database.drop();
    }
}