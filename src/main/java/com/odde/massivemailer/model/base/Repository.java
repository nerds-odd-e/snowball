package com.odde.massivemailer.model.base;

import com.mongodb.client.MongoCollection;
import com.odde.massivemailer.service.MongoDBConnector;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
        return findFirst(eq("_id", objectId));
    }

    public T findFirst(Bson query) {
        return getCollection().find(query).first();
    }

    public MongoCollection<T> getCollection() {
        return MongoDBConnector.instance().getMongoCollection(klass, collectionName);
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
            collection.replaceOne(eq("_id", object.getId()), object);
        }
        ;
    }

    public T findFirstBy(String field, String value) {
        return findFirst(eq(field, value));
    }

    public List<T> findBy(String fieldName, String value) {
        return find(eq(fieldName, value));
    }

    public List<T> find(Bson cond) {
        return getCollection().find(cond).into(new ArrayList<>());
    }

    public int count() {
        return findAll().size();
    }
}