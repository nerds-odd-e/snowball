package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice extends OnlineTest {
    private OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user) {
        List<Question> allQuestions = repo(Question.class).findAll();
        List<Question> dueQuestions = allQuestions.stream().filter(q-> q.isDueForUser(user)).collect(Collectors.toList());
        if (dueQuestions.size() == 0) {
            Optional<Question> newQuestions = allQuestions.stream().filter(q -> q.notAnswered(user)).findFirst();
            newQuestions.ifPresent(dueQuestions::add);
        }
        List<Question> questions = new QuestionCollection(dueQuestions).generateQuestionList(repo(Category.class).findAll(), dueQuestions.size());
        return new OnlinePractice(questions);
    }

    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}
