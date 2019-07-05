package com.odde.snowball.model.onlinetest;

import com.odde.snowball.enumeration.TestType;

import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlineQuiz extends OnlineTest {
    public OnlineQuiz(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlineQuiz(int questionCount) {
        OnlineTest onlineTest = new OnlineQuiz(new QuestionCollection(repo(Question.class).findAll()).generateQuestionList(repo(Category.class).findAll(), questionCount));
        return onlineTest;
    }

    public TestType getTestType() {
        return TestType.OnlineTest;
    }
}
