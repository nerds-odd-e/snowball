package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private Quiz(){

    }

    public static Quiz create(int numberOfQuestions){
        return new Quiz();
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
        return false;
    }

}
