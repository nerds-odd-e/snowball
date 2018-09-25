package com.odde.massivemailer.model;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void testAnswered() {
        Option[] options = {new Option(1L, "Rugby", false)};
        Question question = new Question("What is Scrum?", Arrays.asList(options), null);

        boolean isAnswered = question.isAnswered();
        assertFalse(isAnswered);
    }
}