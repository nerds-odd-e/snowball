package com.odde.massivemailer.model;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    @VisibleForTesting
    protected Quiz(){

    }

    public static Quiz create(int numberOfQuestions){
        return new Quiz();
    }

    public int getNumberOfAnsweredQuestions(){
        return 0;
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
