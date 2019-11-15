package com.odde.snowball.model.onlinetest;

import static com.odde.snowball.model.base.Repository.repo;

import com.odde.snowball.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OnlinePractice extends OnlineTest {
    private OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user, int max) {
        List<UserAnswer> answerList = repo(UserAnswer.class).findBy("userId", user.stringId());
        Map<String,Boolean> answerMap = new HashMap<>();
        for (UserAnswer answer:answerList) {
            if(answer.getCorrect()) {
                answerMap.put(answer.getQuestion().getId().toString(), answer.getCorrect());
            }

        }
        List<Question> visibleQuestions = repo(Question.class).findAll().stream()
                .filter(q -> q.isVisibleForUser(user))
                .filter(q -> {
                    if(answerMap.size() != 0) {
                        return !answerMap.getOrDefault(q.stringId(), false);
                    }
                    return true;
                })
                .collect(Collectors.toList());
        List<Question> questions = new QuestionCollection(visibleQuestions).generateQuestionList(repo(Category.class).findAll(), max);
        return new OnlinePractice(questions);
    }


    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}

