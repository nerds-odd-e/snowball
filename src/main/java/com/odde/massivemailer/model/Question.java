package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.HashMap;

@Table("questions")
public class Question extends ApplicationModel{

    public static Question getLast() {
        return (Question) findAll().get((int) (count() - 1));
    }

    public static Question createSingleChoiceQuestion(HashMap<String, String> content) {
        Question question = new Question();
        question.fromMap(content)
                .set("is_multi_question", 0)
                .saveIt();
        return question;
    }

    public String getDescription() {
        return (String) get("description");
    }

    public void setDescription(String description) {
        set("description", description);
    }


    public void setIsMultiQuestion(int isMultiQuestion) {
        set("is_multi_question", isMultiQuestion);
    }

    public void setIsMultiQuestion(boolean isMultiQuestion) {
        this.setIsMultiQuestion(isMultiQuestion ? 1 : 0);
    }

    public void setAdvice(String advice) {
        set("advice", advice);
    }

    public String getAdvice() {
        return (String) get("advice");
    }

    public void addOption(boolean is_correct, String parameter) {
        new Options()
                .set("description", parameter)
                .set("question_id", getLast().getId())
                .set("is_correct", is_correct)
                .saveIt();
    }
}
