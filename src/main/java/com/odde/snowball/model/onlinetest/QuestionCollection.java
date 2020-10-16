package com.odde.snowball.model.onlinetest;


import com.odde.snowball.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class QuestionCollection {
    private List<Question> allQuestions;

    public void setShouldShuffleQuestions(boolean shouldShuffleQuestions) {
        this.shouldShuffleQuestions = shouldShuffleQuestions;
    }

    private boolean shouldShuffleQuestions = true;

    public QuestionCollection(List<Question> questions) {
        this.allQuestions = questions;
    }

    public List<Question> generateQuestionList(List<Category> categories, int numberOfQuestions) {
        if (numberOfQuestions <= 0 || allQuestions.isEmpty() || hasNoQuestionBelongCategory(categories)) {
            return new ArrayList<>();
        }

        List<Question> questions = new ArrayList<>();
        for (Category cat : categories) {
            int maxQuestionOfCategory = getMaxQuestionOfCategory(categories.size(), numberOfQuestions, cat);
            questions.addAll(getShuffledQuestionsOfCategory(cat).subList(0, maxQuestionOfCategory));
            if (numberOfQuestions <= questions.size()) {
                break;
            }
        }
        questions.addAll(getRemainingQuestions(categories, numberOfQuestions - questions.size(), questions));

        return questions;
    }

    //TODO: concerns: is it ok multi category case?
    public List<Question> generateQuestionListForPractice(List<Category> categories, int numberOfQuestions, User user) throws ParseException {
        if (numberOfQuestions <= 0 || allQuestions.isEmpty() || hasNoQuestionBelongCategory(categories)) {
            return new ArrayList<>();
        }
        if (user.getAnswerInfo().size() == 0) {
            return allQuestions;
        }

        List<Question> resultQuestions = new ArrayList<>();
        List<Question> notAnsweredQuestions = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date today = sdf.parse(sdf.format(new Date()));
        Date nextShowDate;
        for (Question question : allQuestions){
            boolean isAnswered = false;
            for (AnswerInfo info : user.getAnswerInfo()) {
                if (question.stringId().equals(info.getQuestionId())) {
                    isAnswered = true;
                    if (info.isNextShowDate(today)) {
                        resultQuestions.add(question);
                        break;
                    }
                }
            }
            if(isAnswered == false){
                notAnsweredQuestions.add(question);
            }
        }
        resultQuestions.addAll(notAnsweredQuestions);
        return resultQuestions;
    }

    private List<Question> getRemainingQuestions(List<Category> categories, int numberOfRemainingQuestions, List<Question> questions) {
        List<Question> remainingQuestions = new ArrayList<>(allQuestions);
        remainingQuestions.removeAll(questions);
        return new QuestionCollection(remainingQuestions).generateQuestionList(categories, numberOfRemainingQuestions);
    }

    private int getMaxQuestionOfCategory(int numberOfCategory, int numberOfQuestions, Category cat) {
        int numberOfQuestion = getQuestionsOfCategory(cat).size();
        return Math.min(Math.max(numberOfQuestions / numberOfCategory, 1), numberOfQuestion);
    }

    private boolean hasNoQuestionBelongCategory(List<Category> categories) {
        for (Category category : categories) {
            if (!getQuestionsOfCategory(category).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private List<Question> getShuffledQuestionsOfCategory(Category cat) {
        return getShuffledQuestions(getQuestionsOfCategory(cat));
    }

    private List<Question> getQuestionsOfCategory(Category cat) {
        return allQuestions.stream().filter(q -> cat.getId().equals(q.getCategoryId())).collect(Collectors.toList());
    }

    private List<Question> getShuffledQuestions(List<Question> questions) {
        if (shouldShuffleQuestions) {
            Collections.shuffle(questions);
        }
        return questions;
    }
}
