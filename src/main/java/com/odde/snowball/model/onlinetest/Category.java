package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import static com.odde.snowball.model.base.Repository.repo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Entity<Category> {
    private String name="";
    private String link="";
    private String advice="";

    public static Category create(String category_name) {
        return new Category(category_name, "", "").save();
    }

}
