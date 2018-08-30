package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;


@Table("options")
public class Options extends ApplicationModel {

    public int getIsCorrect() {
        return (Integer) get("is_correct");
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

    public void setQuestionId(long questionId) {
        set("question_id", questionId);
    }

    public long getQuestionId() {
        return (long)get("question_id");
    }
}
