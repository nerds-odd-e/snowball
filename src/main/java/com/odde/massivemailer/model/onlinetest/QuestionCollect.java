package com.odde.massivemailer.model.onlinetest;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionCollect {
    private List<Question> allQuestions;

    public void setShouldShuffleQuestions(boolean shouldShuffleQuestions) {
        this.shouldShuffleQuestions = shouldShuffleQuestions;
    }

    private boolean shouldShuffleQuestions = true;

    public QuestionCollect(List<Question> questions) {
        this.allQuestions = questions;
    }

    public List<Question> generateQuestionList(Category[] categories, int numberOfQuestions) {
        if (numberOfQuestions <= 0 || allQuestions.isEmpty() || !hasQuestionBelongCategory(categories)) {
            return new ArrayList<>();
        }
        List<Question> questions = new ArrayList<>();
        for(Category cat: categories) {
            int count = getQuestionsOfCategory(cat).size();
            int average = numberOfQuestions / categories.length;
            if (average == 0) {
                average = 1;
            }
            int maxQuestionOfCategory = Math.min(average, count);
            if (numberOfQuestions > questions.size()) {
                questions.addAll(getShuffledQuestionsOfCategory(cat).subList(0, maxQuestionOfCategory));
            }
        }

        List<Question> remainingQuestions = new ArrayList(allQuestions);
        remainingQuestions.removeAll(questions);
        List<Question> remaining = new QuestionCollect(remainingQuestions).generateQuestionList(categories, numberOfQuestions - questions.size());
        questions.addAll(remaining);

        return questions;
    }

    private boolean hasQuestionBelongCategory(Category[] categories) {
        for (Category category : categories) {
            if (allQuestions.stream().anyMatch(question -> question.belongsTo(category))) {
                return true;
            }
        }
        return false;
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
