package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Quiz {

    private List<Long> questionIds;
    private int numberOfAnsweredQuestions;
    private int NUMBER_OF_QUESTIONS=5;

    public Quiz(){
        questionIds = Question.getNRandomIds(NUMBER_OF_QUESTIONS).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public Optional<Question> getCurrentQuestion() {
        if (!hasNextQuestion()) {
            return Optional.empty();
        }
        Optional<Question> question = Question.getById(questionIds.get(numberOfAnsweredQuestions));
        numberOfAnsweredQuestions++;

        return question;
    }

    public Question getNextQuestion() {

        List<AnswerOption> options = new ArrayList<>();
        options.add(AnswerOption.create("Option1", false));
        options.add(AnswerOption.create("Option2", false));
        options.add(AnswerOption.create("Option3", false));
        options.add(AnswerOption.create("Option4", false));
        options.add(AnswerOption.create("Option5", true));
        return Question.createWithOptions("What is Scrum??????", "Scrum is a framework for agile development.",options );
    }

    public boolean hasNextQuestion() {
        return questionIds.size()>this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }
}
