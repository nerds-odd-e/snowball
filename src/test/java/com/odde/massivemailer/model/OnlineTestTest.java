package com.odde.massivemailer.model;

import com.odde.massivemailer.service.OnlineTestService;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertEquals(0, onlineTest.getQuestions().size());
    }

    @Test
    public void shouldCreateATestWithOnequestionWhenTheQuestionListHasOne() {

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("", new ArrayList<>(), ""));
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
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

        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);

        // when
        question1.setAnsweredOptionId(1L);
        int count1 = onlineTest.countAnsweredQuestions();
        // then
        assertEquals(1, count1);
    }

    @Test
    public void shouldReturnCurrentQuestion() {
        List<Question> questions = new ArrayList<>();
        Question question = new Question("Is same of feature and story?", new ArrayList<>(), "");
        questions.add(question);
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertEquals(onlineTest.getCurrentQuestion(), Optional.of(question));
    }

    @Test
    public void shouldReturnTrueIsOver() {
        List<Question> questions = new ArrayList<>();
        ArrayList<Option> options = new ArrayList<>();
        questions.add(new Question("", options, null, 1L));
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertTrue(onlineTest.isOver());
    }

    @Test
    public void shouldReturnFalseIsOver() {
        List<Question> questions = new ArrayList<>();
        ArrayList<Option> options = new ArrayList<>();
        questions.add(new Question("", options, null, null));
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertFalse(onlineTest.isOver());
    }
}