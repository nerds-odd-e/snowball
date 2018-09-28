package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.service.OnlineTestService;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(TestWithDB.class)
public class OnlineTestTest {
    @Test
    public void optionShouldHaveIdAndValueAndAnswer() {
        Long id = 1L;
        String value = "";
        Boolean answer= false;
        QuestionOption questionOption = new QuestionOption(id, value, answer);
        assertNotNull(questionOption.getId());
        assertNotNull(questionOption.getBody());
        assertNotNull(questionOption.isCorrect());
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
        questions.add(new Question("", new ArrayList<>(), "","Scrum"));
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertEquals(1, onlineTest.getQuestions().size());
    }

    @Test
    public void countAnsweredQuestions() {
        // given
        ArrayList<QuestionOption> questionOptions = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", questionOptions, null, "Scrum", null);
        Question question2 = new Question("", questionOptions, null, "Scrum", null);
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
        Question question = new Question("Is same of feature and story?", new ArrayList<>(), "","Scrum");
        questions.add(question);
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        assertEquals(onlineTest.getCurrentQuestion(), Optional.of(question));
    }
}