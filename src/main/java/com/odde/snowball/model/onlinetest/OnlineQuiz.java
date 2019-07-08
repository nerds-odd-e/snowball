package com.odde.snowball.model.onlinetest;

import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlineQuiz extends OnlineTest {
    private OnlineQuiz(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlineQuiz(int questionCount) {
        return new OnlineQuiz(new QuestionCollection(repo(Question.class).findAll()).generateQuestionList(repo(Category.class).findAll(), questionCount));
    }

    public String endPageName() {
        return "/onlinetest/end_of_test.jsp";
    }

}
