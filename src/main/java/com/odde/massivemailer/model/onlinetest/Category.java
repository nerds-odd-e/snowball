package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.annotations.Table;

@Table("category")
public class Category extends ApplicationModel {
    private static final String NAME = "name";

    public Category() {
    }

    public static Category findByName(String name) {
        return Category.findFirst("name=?", name);
    }

    public static String getNameById(String id) {
        return Category.findById(id).getString("name");
    }

    public static Long getIdByName(String name) {
        return Category.findByName(name).getLongId();
    }

    public static void saveAdvice(String categoryId, String advice, String link) {
        Category cat = Category.findById(categoryId);
        cat.set("advice", advice);
        cat.set("link", link);
        cat.saveIt();
    }

    public String getName() {
        return this.getString("name");
    }
}
