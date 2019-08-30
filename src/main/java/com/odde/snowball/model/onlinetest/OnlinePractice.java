package com.odde.snowball.model.onlinetest;

import com.mongodb.BasicDBObject;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice extends OnlineTest {
    private OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user, int max) {
        // SpaceBasedRepetition
        List<Question> repetitionQuestions = findSpaceBasedRepetitions(max, user, LocalDate.now());
        if (repetitionQuestions.size() >= max) {
            return new OnlinePractice(repetitionQuestions);
        }

        // SimpleReview
        List<Question> visibleQuestions = createVisibleQuestions(user);
        List<Record> recordList = getRecord(user);
        if (recordList.isEmpty()) {
            return createQuestionsForNewUser(max, visibleQuestions);
        } else {
            return createQuestions(recordList);
        }
    }

    private static List<Record> getRecord(User user) {
        return repo(Record.class).findBy("userId", user.getId());
    }

    private static List<Question> createVisibleQuestions(User user) {
        return repo(Question.class).findAll().stream()
                    .filter(q -> q.isVisibleForUser(user))
                    .collect(Collectors.toList());
    }

    private static OnlineTest createQuestions(List<Record> recordList) {
        recordList.sort((s1, s2) -> s2.getLastUpdated().compareTo(s1.getLastUpdated()));
        List<Question> questList = new ArrayList<>();
        for (Record record : recordList) {
            questList.add(repo(Question.class).findFirst(eq("_id", record.getQuestionId())));
        }
        return new OnlinePractice(questList);
    }

    private static OnlineTest createQuestionsForNewUser(int max, List<Question> dueQuestions) {
        int maxQuestionCount = dueQuestions.size() > max ? max : dueQuestions.size();
        List<Question> questions = new QuestionCollection(dueQuestions).generateQuestionList(repo(Category.class).findAll(), maxQuestionCount);
        return new OnlinePractice(questions);
    }

    public static List<Question> findSpaceBasedRepetitions(int count, User user, LocalDate currentDate) {
        List<Record> records = findUserRecords(count, user, currentDate);
        if (records.isEmpty())
            return Collections.emptyList();

        List<ObjectId> questionIds = records.stream().map(Record::getQuestionId).collect(Collectors.toList());
        List<Question> questions = repo(Question.class).findBy("_id", questionIds.toArray());

        questions.sort((o1, o2) -> {
            int o1i = questionIds.indexOf(o1.getId());
            int o2i = questionIds.indexOf(o2.getId());
            return o1i - o2i;
        });

        return questions;
    }

    private static List<Record> findUserRecords(int count, User user, LocalDate currentDate) {
        // 1：昇順
        //-1：降順
        BasicDBObject sortCond = new BasicDBObject("nextShowDate", 1).append("lastUpdated", 1);
        return repo(Record.class).find(and(
                eq("userId", user.getId()),
                lte("nextShowDate", currentDate),
                lt("cycleState", Record.CYCLE.size())),
                sortCond, count);
    }

    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}
