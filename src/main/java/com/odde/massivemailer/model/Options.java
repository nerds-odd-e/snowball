package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.List;


@Table("options")
public class Options extends ApplicationModel {

    public static List<Options> findByQuestionId(int i) {
        return Options.where("question_id=?", i);
    }

    public boolean getIsCorrect() {
        return (int)get("is_correct") == 1;
    }

    public void setOption(String option) {
        set("description", option);
    }

    public String getDescription() {
        return (String) get("description");
    }

    public void setIsCorrect(boolean isCorrect) {
        set("is_correct", isCorrect ? 1 : 0);
    }

    public static Options getById(int id) {
        return (Options) findAll().get(id - 1);
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public void setQuestionId(int questionId) {
        set("question_id", questionId);
    }

    public int getQuestionId() {
        return (int)get("question_id");
    }
}
