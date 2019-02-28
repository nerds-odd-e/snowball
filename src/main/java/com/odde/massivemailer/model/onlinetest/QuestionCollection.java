package com.odde.massivemailer.model.onlinetest;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionCollection {
    private List<Question> allQuestions;

    public void setShouldShuffleQuestions(boolean shouldShuffleQuestions) {
        this.shouldShuffleQuestions = shouldShuffleQuestions;
    }

    private boolean shouldShuffleQuestions = true;

    public QuestionCollection(List<Question> questions) {
        this.allQuestions = questions;
    }

    public List<Question> generateQuestionList(Category[] categories, int numberOfQuestions) {
        if (numberOfQuestions <= 0 || allQuestions.isEmpty() || hasNoQuestionBelongCategory(categories)) {
            return new ArrayList<>();
        }

        List<Question> questions = new ArrayList<>();
        for (Category cat : categories) {
            int maxQuestionOfCategory = getMaxQuestionOfCategory(categories.length, numberOfQuestions, cat);
            questions.addAll(getShuffledQuestionsOfCategory(cat).subList(0, maxQuestionOfCategory));
            if (numberOfQuestions <= questions.size()) {
                break;
            }
        }
        questions.addAll(getRemainingQuestions(categories, numberOfQuestions - questions.size(), questions));

        return questions;
    }

    private List<Question> getRemainingQuestions(Category[] categories, int numberOfRemainingQuestions, List<Question> questions) {
        List<Question> remainingQuestions = new ArrayList<>(allQuestions);
        remainingQuestions.removeAll(questions);
        return new QuestionCollection(remainingQuestions).generateQuestionList(categories, numberOfRemainingQuestions);
    }

    private int getMaxQuestionOfCategory(int numberOfCategory, int numberOfQuestions, Category cat) {
        int numberOfQuestion = getQuestionsOfCategory(cat).size();
        return Math.min(Math.max(numberOfQuestions / numberOfCategory, 1), numberOfQuestion);
    }

    private boolean hasNoQuestionBelongCategory(Category[] categories) {
        for (Category category : categories) {
            if (allQuestions.stream().anyMatch(question -> question.belongsTo(category))) {
                return false;
            }
        }
        return true;
    }

    private List<Question> getShuffledQuestionsOfCategory(Category cat) {
        return getShuffledQuestions(getQuestionsOfCategory(cat));
    }

    private List<Question> getQuestionsOfCategory(Category cat) {
        return allQuestions.stream().filter(q -> q.belongsTo(cat)).collect(Collectors.toList());
    }

    private List<Question> getShuffledQuestions(List<Question> questions) {
        if (shouldShuffleQuestions) {
            Collections.shuffle(questions);
        }
        return questions;
    }
}
