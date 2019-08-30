package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;

import java.time.LocalDate;
import java.util.List;

public class OnlinePractice1 {
    public User user;
    public int max;

    public OnlinePractice1(User user, int max) {
        this.user = user;
        this.max = max;
    }

    static OnlineTest create(OnlinePractice1 onlinePractice1) {
        // SpaceBasedRepetition
        List<Question> repetitionQuestions = OnlinePractice.findSpaceBasedRepetitions(onlinePractice1.max, onlinePractice1.user, LocalDate.now());
        if (repetitionQuestions.size() >= onlinePractice1.max) {
            return new OnlinePractice(repetitionQuestions);
        }

        // SimpleReview
        List<Question> visibleQuestions = OnlinePractice.createVisibleQuestions(onlinePractice1.user);
        List<Record> recordList = OnlinePractice.getRecord(onlinePractice1.user);
        if (recordList.isEmpty()) {
            return OnlinePractice.createQuestionsForNewUser(onlinePractice1.max, visibleQuestions);
        } else {
            return OnlinePractice.createQuestions(recordList, onlinePractice1.max, visibleQuestions);
        }
    }
}
