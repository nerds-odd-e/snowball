package com.odde.massivemailer.model.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.odde.massivemailer.service.MongoDBConnector;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class Repository<T extends Entity> {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Class<T> klass;
    private MongoCollection<T> collection;

    public static <S extends Entity> Repository<S> repo(Class<S> klass) {
        return new Repository<>(klass);
    }

    public Repository(Class<T> klass) {
        this.klass = klass;
        String collectionName = klass.getName();
        this.collection = MongoDBConnector.instance().getMongoCollection(klass, collectionName);
    }

    public T fromMap(Map map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, klass);
    }

    public T fromKeyValuePairs(String... args) {
        return fromMap(asMap(args));
    }

    public T findByStringId(String stringId) {
        return findById(new ObjectId(stringId));
    }

    public T findById(ObjectId objectId) {
        return findFirstBy("_id", objectId);
    }

    public T findFirstBy(String field, Object value) {
        return collection.find(eq(field, value)).first();
    }

    public List<T> findBy(String fieldName, Object value) {
        return find(eq(fieldName, value));
    }

    public List<T> find(Bson cond) {
        return collection.find(cond).into(new ArrayList<>());
    }

    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    public void save(T object) {
        object.onBeforeSave();
        validate(object);
        createOrUpdate(object);
    }

    public int count() {
        return findAll().size();
    }

    private void createOrUpdate(T object) {
        MongoCollection<T> collection = this.collection;
        if (object.getId() == null) {
            object.setId(new ObjectId());
            collection.insertOne(object);
        } else {
            collection.replaceOne(eq("_id", object.getId()), object);
        }
    }

    private void validate(T object) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Entity>> validate = validator.validate(object);
        if (validate.size() > 0) {
            throw new ValidationException(validate);
        }
    }

    private Map<String, String> asMap(String... args) {
        Map<String, String> argMap = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            String key;
            try {
                key = args[i];
            } catch (ClassCastException cce) {
                System.err.println(cce.getMessage());
                System.err.println("args[" + i + "] " + args[i]);
                throw cce;
            }
            if (i + 1 < args.length) {
                String value = args[i + 1];
                argMap.put(key, value);
            }
        }
        return argMap;
    }
}