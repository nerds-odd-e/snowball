package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class QuestionTest {

    @Before
    public void setup() {
        Question question = new Question();
        question.setAdvice("Scrum is a framework for agile development.");
        question.setDescription("What is scrum?");
        question.setIsMultiQuestion(false);
        question.saveIt();
    }

    @Test
    public void testShouldFindOne() throws Exception {
        Question question = Question.getOne();
        assertEquals("Scrum is a framework for agile development.", question.getAdvice());
        assertEquals("What is scrum?", question.getDescription());
        assertFalse(question.getIsMultiQuestion());
    }
}