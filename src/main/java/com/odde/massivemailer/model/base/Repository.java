package com.odde.massivemailer.model.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.odde.massivemailer.service.MongoDBConnector;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class Repository<T extends Entity> {
    private Class<T> klass;
    private String collectionName;

    public Repository(Class<T> klass, String collectionName) {
        this.klass = klass;
        this.collectionName = collectionName;
    }

    public T fromMap(Map map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, klass);
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
        if(object.onBeforeSave()) {
            MongoCollection<T> collection = getCollection();
            if (object.getId() == null) {
                object.setId(new ObjectId());
                collection.insertOne(object);
            } else {
                collection.replaceOne(eq("_id", object.getId()), object);
            }
        }
    }

    public T findFirstBy(String field, String value) {
        return findFirst(eq(field, value));
    }

    public List<T> findBy(String fieldName, Object value) {
        return find(eq(fieldName, value));
    }

    public List<T> find(Bson cond) {
        return getCollection().find(cond).into(new ArrayList<>());
    }

    public int count() {
        return findAll().size();
    }

    public T fromKeyValuePairs(String ...args) {
        return fromMap(asMap(args));
    }

    private Map<String, String> asMap(String... args) {
        Map<String, String> argMap = new HashMap<String, String>();
        for (int i = 0; i < args.length; i += 2) {
            String key;
            try {
                key = (String) args[i];
            } catch (ClassCastException cce) {
                System.err.println(cce.getMessage());
                System.err.println("args[" + i + "] " + args[i].toString());
                throw cce;
            }
            if (i + 1 < args.length) {
                String value = args[i + 1];
                argMap.put(key, value);
            }
        }
        return argMap;
    }

    public void createIt(String ...args) {
        save(fromKeyValuePairs(args));
    }
}