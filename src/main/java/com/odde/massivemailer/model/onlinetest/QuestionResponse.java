package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.annotations.Table;

@Table("question_responses")
public class QuestionResponse extends ApplicationModel {

    private static final String TEST_ID = "test_id";
    private static final String QUESTION_ID = "question_id";
    private static final String IS_ANSWER_CORRECT = "is_answer_correct";

    static {
        validatePresenceOf(TEST_ID, QUESTION_ID, IS_ANSWER_CORRECT);
    }

    public QuestionResponse(){}


}
