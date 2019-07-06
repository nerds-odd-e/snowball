package com.odde.snowball.factory;

import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.Question;
import org.bson.types.ObjectId;

import static com.odde.snowball.model.base.Repository.repo;

public class QuestionBuilder {
    private Question currentQuestion;

    public static QuestionBuilder buildDefaultQuestion(String category) {
        return new QuestionBuilder()
                .aQuestion("myTest", "", category)
                .withWrongOption("wrongOption")
                .withCorrectOption("correctOption");
    }

    public Question please() {
        return currentQuestion;
    }

    public QuestionBuilder aQuestion(String questionDescription, String advice, String categoryName) {
        ObjectId categoryId = getOrCreateCategory(categoryName);
        currentQuestion = new Question(questionDescription, advice, categoryId, false, false).save();
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

    private ObjectId getOrCreateCategory(String categoryName) {
        Category category = repo(Category.class).findFirstBy("name", categoryName);
        if (category == null) {
            category = Category.create(categoryName);
        }
        return category.getId();
    }

}
