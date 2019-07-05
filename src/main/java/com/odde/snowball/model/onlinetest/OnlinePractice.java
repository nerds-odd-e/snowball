package com.odde.snowball.model.onlinetest;

import com.odde.snowball.enumeration.TestType;

import java.util.List;

public class OnlinePractice extends OnlineTest {
    public OnlinePractice(List<Question> questions) {
        super(questions);
    }
    public String getNextPageName() {
        if (hasNextQuestion()) {
            return "/onlinetest/question.jsp";
        }
        return "/practice/completed_practice.jsp";
    }

    public TestType getTestType() {
        return TestType.Practice;
    }
}
