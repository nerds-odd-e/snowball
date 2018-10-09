package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;

@Table("options")
public class AnswerOption extends ApplicationModel{

    private static final String DESCRIPTION = "description";
    private static final String IS_CORRECT = "is_correct";
    private static final String QUESTION_ID = "question_id";

    public Map<String, String> attributes = new HashMap<>();

    static {
        validatePresenceOf(DESCRIPTION, QUESTION_ID, IS_CORRECT);
    }

    public AnswerOption() {}

    public AnswerOption(String description, long questionId, boolean isCorrect) {
        set(DESCRIPTION, description);
        set(QUESTION_ID, questionId);
        set(IS_CORRECT, isCorrect);
    }

    public static Collection<AnswerOption> getForQuestion(Long questionId) {
        return where("question_id = ?", questionId);
    }

    static Optional<AnswerOption> getById(Long optionId) {
        LazyList<AnswerOption> answerOption = where("id = ?", optionId);
        return answerOption.stream().findFirst();
    }

    public Long getQuestionId() {
        return getLong(QUESTION_ID);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public boolean isCorrect() {
        return getInteger(IS_CORRECT) == 1;
    }
}
