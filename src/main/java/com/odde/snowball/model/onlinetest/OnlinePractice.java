package com.odde.snowball.model.onlinetest;

import com.mongodb.BasicDBObject;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice extends OnlineTest {
    public OnlinePractice(List<Question> questions) {
        super(questions);
    }

    public static OnlineTest createOnlinePractice(User user, int max) {
        return new OnlinePractice1(user, max).create();
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
                lte("cycleState", Record.CYCLE.size())),
                sortCond, count);
    }

    public String endPageName() {
        return "/practice/completed_practice.jsp";
    }
}
