package com.odde.snowball.model.onlinetest;

import com.mongodb.BasicDBObject;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.in;
import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice1 {
    public User user;
    public int max;

    public OnlinePractice1(User user, int max) {
        this.user = user;
        this.max = max;
    }

    private OnlineTest createQuestions(List<Record> recordList, int max, List<Question> visibleQuestionList) {

        List<ObjectId> answeredQuestionIdList = recordList.stream()
                .sorted(Comparator.comparing(Record::getLastUpdated))
                .map(Record::getQuestionId)
                .collect(Collectors.toList());

        List<Question> noAnsweredQuestions = visibleQuestionList.stream()
                .filter(visibleQuestion -> !answeredQuestionIdList.contains(visibleQuestion.getId()))
                .limit(max)
                .collect(Collectors.toList());

        if (!noAnsweredQuestions.isEmpty()) {
            return new OnlinePractice(noAnsweredQuestions);
        }

        List<Question> answeredQuestionList = answeredQuestionIdList.stream()
                .map(answeredQuestionId -> repo(Question.class).findById(answeredQuestionId))
                .limit(max)
                .collect(Collectors.toList());
        return new OnlinePractice(answeredQuestionList);
    }

    private List<Record> getRecord(User user) {
        return repo(Record.class).findBy("userId", user.getId());
    }

    private List<Question> createVisibleQuestions(User user) {
        return repo(Question.class).findAll().stream()
                    .filter(q -> q.isVisibleForUser(user))
                    .collect(Collectors.toList());
    }

    private OnlineTest createQuestionsForNewUser(int max, List<Question> dueQuestions) {
        int maxQuestionCount = dueQuestions.size() > max ? max : dueQuestions.size();
        List<Question> questions = new QuestionCollection(dueQuestions).generateQuestionList(repo(Category.class).findAll(), maxQuestionCount);
        return new OnlinePractice(questions);
    }

    OnlineTest create() {
        // SpaceBasedRepetition
        List<Question> repetitionQuestions = OnlinePractice.findSpaceBasedRepetitions(max, user, LocalDate.now());
        if (repetitionQuestions.size() >= max) {
            return new OnlinePractice(repetitionQuestions);
        }

        // SimpleReview
        List<Question> visibleQuestions = createVisibleQuestions(user);
        List<Record> recordList = getRecord(user);
        if (recordList.isEmpty()) {
            return createQuestionsForNewUser(max, visibleQuestions);
        } else {
            return createQuestions(recordList, max, visibleQuestions);
        }
    }
}
