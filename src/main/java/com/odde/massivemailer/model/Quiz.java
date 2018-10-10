package com.odde.massivemailer.model;

public class Quiz {

    private Quiz(){

    }

    public static Quiz create(int numberOfQuestions){
        return new Quiz();
    }

    public Question getNextQuestion() {
        return null;
    }

    public boolean hasNextQuestion() {
        return false;
    }

}
