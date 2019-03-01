package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;

public class QuestionBuilder {
    private Question currentQuestion;

    @SuppressWarnings("UnusedReturnValue")
    public Question please() {
        return currentQuestion;
    }

    public QuestionBuilder aQuestion() {
        return aQuestion("myTest", null);
    }

//    public QuestionBuilder aQuestion() {
//        return aQuestion("myTest", null,  String.valueOf(Category.SCRUM.getId()));
//    }

    public QuestionBuilder aQuestion( String questionDescription, String advice, String category) {
        currentQuestion = Question.createIt("description", questionDescription, "advice", advice, "category", category);
        return this;
    }

    public QuestionBuilder aQuestion(String questionDescription, String advice) {
        currentQuestion = Question.createIt("description", questionDescription, "advice", advice);
        return this;
    }

    public QuestionBuilder aQuestion(String categoryId) {
        currentQuestion = Question.createIt("description", "myTest", "advice", null, "category", categoryId);
        return this;
    }

    public QuestionBuilder aQuestion(Category category) {
        currentQuestion = Question.createIt("description", "myTest", "advice", null, "category", String.valueOf(category.getId()));
        return this;
    }

    public QuestionBuilder aQuestion(int type) {
        currentQuestion = Question.createIt("description", "myTest", "advice", null, "category", String.valueOf(Category.SCRUM.getId()), "is_multi_question", type);
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
