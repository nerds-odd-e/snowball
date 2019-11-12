package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;

public class PracticeHistory extends Entity<PracticeHistory> {

    private Question question;
    private User user;

    public PracticeHistory(Question question, User user) {
        this.question = question;
        this.user = user;
    }

}
