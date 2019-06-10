package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import static com.odde.massivemailer.model.base.Repository.repo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Entity<Category> {
    private String name="";
    private String link="";
    private String advice="";

    public static ObjectId getIdByName(String name) {
        return repo(Category.class).findFirstBy("name", name).id;
    }

    public static Category create(String category_name) {
        return new Category(category_name, "", "").save();
    }

}
