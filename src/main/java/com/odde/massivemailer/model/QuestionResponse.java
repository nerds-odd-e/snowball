package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.HashMap;
import java.util.Map;

@Table("question_responses")
public class QuestionResponse extends ApplicationModel{
    //private String questionId;
    //private String testId;
    //private Boolean isAnswerCorrect;

    private static final String TEST_ID = "test_id";
    private static final String QUESTION_ID = "question_id";
    private static final String IS_ANSWER_CORRECT = "is_answer_correct";

    public Map<String, String> attributes = new HashMap<>();

    static {
        validatePresenceOf(TEST_ID, QUESTION_ID, IS_ANSWER_CORRECT);
    }

    public QuestionResponse(String testId, String questionId, Boolean isAnswerCorrect) {
        set(TEST_ID, testId);
        set(QUESTION_ID, questionId);
        set(IS_ANSWER_CORRECT, isAnswerCorrect);
    }


}
