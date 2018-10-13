package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.Question;

public class QuestionBuilder {
    private Question currentQuestion;

    public Question please() {
        return currentQuestion;
    }

    public QuestionBuilder aQuestion() {
        currentQuestion = Question.createIt("description", "MyTest", "advice", "eeee");
        return this;
    }

    public QuestionBuilder withWrongOption(String optionText) {
        currentQuestion.createWrongOption(optionText);
        return this;
    }

    public QuestionBuilder withCorrectOption(String optionText) {
        currentQuestion.createCorrectOption(optionText);
        return this;
    }

}
