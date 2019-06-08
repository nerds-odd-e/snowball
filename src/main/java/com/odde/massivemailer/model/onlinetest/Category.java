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

    public static Category createIt(String category_name) {
        return new Category(category_name, "", "").saveIt();
    }

    protected Category saveIt() {
        repository().save(this);
        return this;
    }
}
