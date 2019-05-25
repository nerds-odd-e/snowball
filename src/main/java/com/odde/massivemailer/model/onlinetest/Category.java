package com.odde.massivemailer.model.onlinetest;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

@Getter
public class Category {
    private ObjectId id = null;
    private String name="";
    private String link="";
    private String advice="";

    public Category() {
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public static Category findByName(String name) {
        return DBConnector.getCategoryMongoCollection().find(eq("name", name)).first();
    }

    public static ObjectId getIdByName(String name) {
        return Category.findByName(name).id;
    }

    public static void saveAdvice(String categoryId, String advice, String link) {
        Category cat = Category.findById(categoryId);
        cat.advice = advice;
        cat.link = link;
        if(link == null) {
            cat.link = "";
        }
        cat.saveIt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(link, category.link) &&
                Objects.equals(advice, category.advice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, link, advice);
    }

    public static Category findById(String categoryId) {
        return DBConnector.getCategoryMongoCollection().find(eq("_id", new ObjectId(categoryId))).first();
    }


    public static List<Category> findAll() {
        return DBConnector.getCategoryMongoCollection().find().into(new ArrayList<>());
    }

    public String getStringId() {
        return getId().toString();
    }

    static class IntegerCodec implements Codec<Category> {
        @Override
        public void encode(final BsonWriter writer, final Category value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("name");
            writer.writeString(value.getName());
            writer.writeName("advice");
            writer.writeString(value.advice);
            writer.writeName("link");
            writer.writeString(value.link);
            writer.writeEndDocument();
        }

        @Override
        public Category decode(final BsonReader reader, final DecoderContext decoderContext) {
            Category cat = new Category();
            reader.readStartDocument();
            cat.id = reader.readObjectId("_id");
            reader.readName();
            cat.name = reader.readString();
            reader.readName();
            cat.advice = reader.readString();
            reader.readName();
            cat.link = reader.readString();
            reader.readEndDocument();
            return cat;
        }

        @Override
        public Class<Category> getEncoderClass() {
            return Category.class;
        }
    }

    private void saveIt() {
        MongoCollection<Category> collection = DBConnector.getCategoryMongoCollection();
        if (id == null) {
            id = new ObjectId();
            collection.insertOne(this);
        }
        else {
            collection.replaceOne(eq("_id", id), this);
        };
    }

    public static Category createIt(String name, String category_name) {
        Category category = new Category();
        category.name = category_name;
        category.saveIt();
        return category;
    }
}
