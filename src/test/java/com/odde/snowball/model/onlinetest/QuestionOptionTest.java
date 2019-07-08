package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.base.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestWithDB.class)
public class QuestionOptionTest {
    private final Question question1 = QuestionBuilder.buildDefaultQuestion("scrum").please();

    @Test(expected = ValidationException.class)
    public void shouldFailWhenDescriptionIsEmpty() {
        question1.withOption("", false).save();
    }
}
