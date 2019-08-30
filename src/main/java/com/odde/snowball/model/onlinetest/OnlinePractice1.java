package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlinePractice1 {
    public User user;
    public int max;

    public OnlinePractice1(User user, int max) {
        this.user = user;
        this.max = max;
    }

    OnlineTest create() {
        // SpaceBasedRepetition
        List<Question> repetitionQuestions = OnlinePractice.findSpaceBasedRepetitions(max, user, LocalDate.now());
        if (repetitionQuestions.size() >= max) {
            return new OnlinePractice(repetitionQuestions);
        }

        List<Question> simpleQuestions = null;
        // SimpleReview
        List<Question> visibleQuestions = createVisibleQuestions();
        List<Record> recordList = getRecord();
        if (recordList.isEmpty()) {
            simpleQuestions = createQuestionsForNewUser(visibleQuestions).getQuestions();
        } else {
            simpleQuestions =  createQuestions(recordList, visibleQuestions).getQuestions();
        }

        return new OnlinePractice(concatQuestions(repetitionQuestions, simpleQuestions, max));
    }

    private OnlineTest createQuestions(List<Record> recordList, List<Question> visibleQuestionList) {

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

    private List<Record> getRecord() {
        return repo(Record.class).findBy("userId", user.getId());
    }

    private List<Question> createVisibleQuestions() {
        return repo(Question.class).findAll().stream()
                    .filter(q -> q.isVisibleForUser(user))
                    .collect(Collectors.toList());
    }

    private OnlineTest createQuestionsForNewUser(List<Question> dueQuestions) {
        int maxQuestionCount = dueQuestions.size() > max ? max : dueQuestions.size();
        List<Question> questions = new QuestionCollection(dueQuestions).generateQuestionList(repo(Category.class).findAll(), maxQuestionCount);
        return new OnlinePractice(questions);
    }


    private List<Question> concatQuestions(List<Question> questions1, List<Question> questions2, int max){
        if(questions1.size() >= max){
            return questions1.subList(0, max);
        }
        List<Question> concatQuestions = new ArrayList<>(questions1);
        for(Question question : questions2) {
            if(!concatQuestions.contains(question)) {
                concatQuestions.add(question);
                if (concatQuestions.size() >= max) {
                    return concatQuestions;
                }
            }
        }

        return concatQuestions;
    }

}
