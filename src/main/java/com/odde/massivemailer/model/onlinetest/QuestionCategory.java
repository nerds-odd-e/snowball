package com.odde.massivemailer.model.onlinetest;


import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.annotations.Table;

@Table("category")
public class QuestionCategory extends ApplicationModel {
    private static final String NAME = "name";

    public QuestionCategory() {
    }

    public static QuestionCategory createQuestionCategory(String name) {
        return QuestionCategory.createIt("name", name);
    }

    public String getName() {
        return this.getString("name");
    }

}
