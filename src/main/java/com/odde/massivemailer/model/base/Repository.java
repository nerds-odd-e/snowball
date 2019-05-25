package com.odde.massivemailer.model.base;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.DBConnector;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Repository<T extends Entity> {
    private Class<T> klass;

    public Repository(Class<T> klass) {
        this.klass = klass;
    }

    public T findById(String categoryId) {
        return findFirst(Filters.eq("_id", new ObjectId(categoryId)));
    }

    public T findFirst(Bson query) {
        return getCollection().find(query).first();
    }

    private MongoCollection<T> getCollection() {
        return DBConnector.instance().getMongoCollection(klass, "categories");
    }

    public List<T> findAll() {
        return getCollection().find().into(new ArrayList<T>());
    }

    public void save(T cat) {
        MongoCollection<T> collection = getCollection();
        if (cat.getId() == null) {
            cat.setId(new ObjectId());
            collection.insertOne(cat);
        } else {
            collection.replaceOne(Filters.eq("_id", cat.getId()), cat);
        }
        ;
    }
}