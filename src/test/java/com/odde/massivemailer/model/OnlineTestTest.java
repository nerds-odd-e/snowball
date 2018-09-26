package com.odde.massivemailer.model;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void countAnsweredQuestions() {
        // given
        ArrayList<Option> options = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", options, null, null);
        Question question2 = new Question("", options, null, null);
        questions.add(question1);
        questions.add(question2);

        OnlineTest onlineTest = new OnlineTest(questions);

        // when
        question1.setAnsweredOptionId(1L);
        int count1 = onlineTest.countAnsweredQuestions();
        // then
        assertEquals(1, count1);

        // when
        question2.setAnsweredOptionId(1L);
        int count2 = onlineTest.countAnsweredQuestions();
        // then
        assertEquals(2, count2);
    }
}