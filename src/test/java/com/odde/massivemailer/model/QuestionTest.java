package com.odde.massivemailer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    @Test
    public void questionShouldHaveDescriptionAndOptionsAndAdvice() {
        String description = "";
        List<Option> options = new ArrayList<>();
        String advice = "advice";
        Question question = new Question(description, options, advice);
        assertNotNull(question.getDescription());
        assertNotNull(question.getAdvice());
        assertNotNull(question.getOptions());
    }

}