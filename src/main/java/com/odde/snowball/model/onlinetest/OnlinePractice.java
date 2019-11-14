package com.odde.snowball.model.onlinetest;

import static com.odde.snowball.model.base.Repository.repo;

import com.odde.snowball.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OnlinePractice extends OnlineTest {
    private OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user, int max) {
        List<String> answerList = repo(UserAnswer.class).findBy("userId", user.stringId())
                .stream()
                .map(a -> a.getQuestion().stringId())
                .collect(Collectors.toList());
        List<Question> visibleQuestions = repo(Question.class).findAll().stream()
                .filter(q -> q.isVisibleForUser(user))
                .filter(q -> !answerList.contains(q.stringId()))
                .collect(Collectors.toList());
        List<Question> questions = new QuestionCollection(visibleQuestions).generateQuestionList(repo(Category.class).findAll(), max);
        return new OnlinePractice(questions);
    }


    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}
