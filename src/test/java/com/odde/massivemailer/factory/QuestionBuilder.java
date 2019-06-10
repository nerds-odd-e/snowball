package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import org.bson.types.ObjectId;

public class QuestionBuilder {
    private Question currentQuestion;

    @SuppressWarnings("UnusedReturnValue")
    public Question please() {
        return currentQuestion;
    }

    public QuestionBuilder aQuestion( String questionDescription, String advice, ObjectId categoryId) {
        currentQuestion = new Question(questionDescription, advice, categoryId, false, false).save();
        return this;
    }

    public QuestionBuilder aQuestion(Category category) {
        currentQuestion = new Question("myTest", null, category.getId(), false, false).save();
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

    public QuestionBuilder mutipleSelections() {
        currentQuestion.setMultiQuestion(true);
        currentQuestion.save();
        return this;
    }
}
