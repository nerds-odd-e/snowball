package com.odde.massivemailer.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.odde.massivemailer.model.User;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBConnector {
    static MongoDBConnector dbConnector = null;
    MongoDatabase database;

    public static MongoDBConnector instance() {
        if (dbConnector == null) {
            dbConnector = new MongoDBConnector();
        }
        return dbConnector;
    }

    public MongoDBConnector() {
        MongoClient mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("kyouha_unit_test").withCodecRegistry(getCodecRegistry());
    }

    public <T>MongoCollection<T> getMongoCollection(Class<T> klass, String name) {
        return database.getCollection(name, klass);
    }

    private static CodecRegistry getCodecRegistry() {
        CodecRegistry codecRegistry = CodecRegistries.fromCodecs(new User.UserCodec());
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return CodecRegistries.fromRegistries(codecRegistry, pojoCodecRegistry);
    }

    public static void resetAll() {
        instance().database.drop();
    }
}