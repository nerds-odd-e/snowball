package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.HashMap;
import java.util.Map;

@Table("options")
public class AnswerOption extends ApplicationModel{

    public Map<String, String> attributes = new HashMap<>();

//    static {
//        validatePresenceOf("description", "question_id", "is_correct");
//    }

    public String getQuestionId() {
        return null;
    }


}
