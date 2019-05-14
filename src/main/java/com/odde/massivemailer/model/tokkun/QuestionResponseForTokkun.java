package com.odde.massivemailer.model.tokkun;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.annotations.Table;


@Table("question_responses_for_tokkun")
public class QuestionResponseForTokkun extends ApplicationModel {
    private static final String USER_ID = "user_id";
    private static final String QUESTION_ID = "question_id";
    private static final String COUNTER = "counter";
    private static final String ANSWERED_AT = "answered_at";

    static {
       validatePresenceOf(USER_ID, QUESTION_ID, COUNTER, ANSWERED_AT);
    }

    public QuestionResponseForTokkun() {}


}
