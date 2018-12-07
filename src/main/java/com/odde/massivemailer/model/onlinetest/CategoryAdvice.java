package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.annotations.Table;

@Table("category_advices")
public class CategoryAdvice extends ApplicationModel {
    public static void saveAdvice(String categoryId, String advice) {
        String query = "category_id = " + categoryId;
        CategoryAdvice categoryAdvice = CategoryAdvice.findFirst(query);
        categoryAdvice.set("advice", advice);
        categoryAdvice.save();
    }
}
