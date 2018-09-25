package com.odde.massivemailer.model;

import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OnlineTestTest {
    @Test
    public void optionShouldHaveIdAndValueAndAnswer() {
        Long id = 1L;
        String value = "";
        Boolean answer= false;
        Option option = new Option(id, value, answer);
        assertNotNull(option.getId());
        assertNotNull(option.getValue());
        assertNotNull(option.isAnswer());
    }

    @Test
    public void shouldCreateATestWithNoQuestionsWhenTheQuestionListIsEmpty() {
        List<Question> questions = new ArrayList<>();
        OnlineTest onlineTest = OnlineTest.createTestWithQuestions(questions, 10);
        assertEquals(0, onlineTest.getQuestions().size());
    }

    @Test
    public void shouldCreateATestWithOnequestionWhenTheQuestionListHasOne() {

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("", new ArrayList<>(), ""));
        OnlineTest onlineTest = OnlineTest.createTestWithQuestions(questions, 10);
        assertEquals(1, onlineTest.getQuestions().size());
    }
}