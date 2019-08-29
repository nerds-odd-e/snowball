package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlineQuiz extends OnlineTest {
    private OnlineQuiz(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlineQuiz(int questionCount) {
        return new OnlineQuiz(new QuestionCollection(repo(Question.class).findAll().stream().filter(q -> q.isPublic()).collect(Collectors.toList())
        ).generateQuestionList(repo(Category.class).findAll(), questionCount));
    }


    public static OnlineTest createOnlineQuiz(int questionCount, User user) {
        return new OnlineQuiz(new QuestionCollection(repo(Question.class).findAll().stream()
                .filter(q -> q.isVisibleForUser(user))
                .collect(Collectors.toList())
        ).generateQuestionList(repo(Category.class).findAll(), questionCount));
    }

    public String endPageName() {
        return "/onlinetest/end_of_test.jsp";
    }

}
