package com.odde.massivemailer.model.tokkun;

import com.odde.massivemailer.model.ApplicationModel;
import com.odde.massivemailer.model.User;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Collections;
import java.util.List;


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


    public static List<QuestionResponseForTokkun> selectUserResponsedTokens(User user){
        List<QuestionResponseForTokkun> questionResponseForTokkuns = QuestionResponseForTokkun.find("user_id = ?", user.getLongId());
        return questionResponseForTokkuns;
    }
}
