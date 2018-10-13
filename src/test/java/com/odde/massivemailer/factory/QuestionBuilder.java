package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.Question;

public class QuestionBuilder {
    private Question currentQuestion;

    public Question please() {
        return currentQuestion;
    }

    public QuestionBuilder aQuestion() {
        return aQuestion("myTest", null);
    }

    public QuestionBuilder aQuestion(String questionDescription, String advice) {
        currentQuestion = Question.createIt("description", questionDescription, "advice", advice);
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
