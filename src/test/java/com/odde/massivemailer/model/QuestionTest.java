package com.odde.massivemailer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    @Test
    public void questionShouldHaveDescriptionAndOptionsAndAdvice() {
        String description = "";
        List<QuestionOption> questionOptions = new ArrayList<>();
        String advice = "advice";
        Question question = new Question(description, questionOptions, advice, "Scrum");
        assertNotNull(question.getDescription());
        assertNotNull(question.getAdvice());
        assertNotNull(question.getQuestionOptions());
    }

    @Test
    public void questionIsAnswered() {
        Long OptionId = 1L;
        QuestionOption[] questionOptions = {new QuestionOption(OptionId, "Rugby", false)};
        Question question = new Question("What is Scrum?", Arrays.asList(questionOptions), null, "Scrum",OptionId);

        boolean isAnswered = question.isAnswered();
        assertTrue(isAnswered);
    }

    @Test
    public void questionIsNotAnswered() {
        QuestionOption[] questionOptions = {new QuestionOption(1L, "Rugby", false)};
        Question question = new Question("What is Scrum?", Arrays.asList(questionOptions), null, "Scrum",null);

        boolean isAnswered = question.isAnswered();
        assertFalse(isAnswered);
    }
}