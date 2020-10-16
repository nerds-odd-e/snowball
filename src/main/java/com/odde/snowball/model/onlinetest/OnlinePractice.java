package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice extends OnlineTest {
    private OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user, String categoryId, int max) throws ParseException {
        List<Question> all;
        all = repo(Question.class).findAll();
        if ( categoryId !=null && !categoryId.isEmpty()) {
            all = repo(Question.class).findBy("categoryId", new ObjectId(categoryId));
        }
        List<Question> visibleQuestions = all.stream()
                .filter(q -> q.isVisibleForUser(user))
                .collect(Collectors.toList());
        List<Question> questions = new QuestionCollection(visibleQuestions).generateQuestionList(repo(Category.class).findAll(), max);
        return new OnlinePractice(questions);
    }


    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}
