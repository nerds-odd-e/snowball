package com.odde.massivemailer.model.base;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.odde.massivemailer.model.onlinetest.DBConnector;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Repository<T extends Entity> {
    private Class<T> klass;
    private String collectionName;

    public Repository(Class<T> klass, String collectionName) {
        this.klass = klass;
        this.collectionName = collectionName;
    }

    public T findByStringId(String stringId) {
        return findById(new ObjectId(stringId));
    }

    public T findById(ObjectId objectId) {
        return findFirst(Filters.eq("_id", objectId));
    }

    public T findFirst(Bson query) {
        return getCollection().find(query).first();
    }

    public MongoCollection<T> getCollection() {
        return DBConnector.instance().getMongoCollection(klass, collectionName);
    }

    public List<T> findAll() {
        return getCollection().find().into(new ArrayList<T>());
    }

    public void save(T object) {
        object.onBeforeSave();
        MongoCollection<T> collection = getCollection();
        if (object.getId() == null) {
            object.setId(new ObjectId());
            collection.insertOne(object);
        } else {
            collection.replaceOne(Filters.eq("_id", object.getId()), object);
        }
        ;
    }
}