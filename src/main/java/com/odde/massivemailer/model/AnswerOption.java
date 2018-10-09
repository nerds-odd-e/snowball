package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Table("options")
public class AnswerOption extends ApplicationModel{

    private static final String DESCRIPTION = "description";
    private static final String IS_CORRECT = "is_correct";
    private static final String QUESTION_ID = "question_id";

    public Map<String, String> attributes = new HashMap<>();


    public static Collection<AnswerOption> getOptionsForQuestion(Long questionId) {
        return where("question_id = ?", questionId);
    }

    static {
        validatePresenceOf(DESCRIPTION, QUESTION_ID, IS_CORRECT);
    }

    public Long getQuestionId() {
        return getLong(QUESTION_ID);
    }
}
