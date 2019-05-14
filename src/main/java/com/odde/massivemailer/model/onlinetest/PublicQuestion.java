package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

@Table("public_questions")
public class PublicQuestion extends ApplicationModel {
    private static final String QUESTION_ID = "question_id";

    public PublicQuestion() {

    }

    public PublicQuestion(Long questionId) {
        set(QUESTION_ID, questionId);
    }

    static PublicQuestion getById(Long questionId) {
        LazyList<PublicQuestion> publicQuestion = where("id = ?", questionId);
        return publicQuestion.stream().findFirst().orElseThrow(() -> new IllegalArgumentException("No question found by given id."));
    }
}
