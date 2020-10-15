package com.odde.snowball.model.onlinetest;


import com.odde.snowball.model.User;

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
        Date today = new Date();
        User user = repo(User.class).findAll().get(0);
        if (user.getAnswerInfo().size() > 0) {
            for (AnswerInfo info : user.getAnswerInfo()) {
                if (today.compareTo(info.getNextShowDate()) <= 0) {
                    return new ArrayList<>();
                } else {
                    Question question = repo(Question.class).findByStringId(info.getQuestionId());
                    questions.add(question);
                    return questions;

                }
            }
        }

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
