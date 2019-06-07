package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Entity {
    private String name="";
    private String link="";
    private String advice="";

    public static Repository<Category> repository() {
        return new Repository(Category.class, "categories");
    }

    public String getName() {
        return this.name;
    }

    public static Category findByName(String name) {
        return repository().findFirst(eq("name", name));
    }

    public static ObjectId getIdByName(String name) {
        return Category.findByName(name).id;
    }

    public static void saveAdvice(String categoryId, String advice, String link) {
        Category cat = repository().findByStringId(categoryId);
        cat.advice = advice;
        cat.link = link;
        if(link == null) {
            cat.link = "";
        }
        cat.saveIt();
    }

    @Override
    public boolean onBeforeSave() {

        return true;
    }

    public static class CategoryCodec implements Codec<Category> {
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

    public static Category createIt(String category_name) {
        return new Category(category_name, "", "").saveIt();
    }

    protected Category saveIt() {
        repository().save(this);
        return this;
    }
}
