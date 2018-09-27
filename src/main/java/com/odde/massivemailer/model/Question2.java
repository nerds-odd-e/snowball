package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

@Table("questions")
public class Question2 extends ApplicationModel {

    public Question2(String body, String advice) {
        set("body", body);
        set("advice", advice);
    }

    public Question2() {

    }
}
