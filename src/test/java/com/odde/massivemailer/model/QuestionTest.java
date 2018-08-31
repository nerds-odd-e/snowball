package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class QuestionTest {

    @RunWith(TestWithDB.class)
    static public class oneRowTest {
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

    @RunWith(TestWithDB.class)
    static public class MultiRowTest {

        @Before
        public void setup() {
            for (int i = 1; i < 10; i++) {
                Question question = new Question();
                question.setAdvice("Advice" + i);
                question.setDescription("Description" + i);
                question.setIsMultiQuestion(false);
                question.saveIt();
            }
        }

        @Test
        public void testShouldFind10rows() throws Exception {
            int rows = 3;
            List<Question> questionList = Question.getList(rows);
            assertEquals(3, questionList.size());
        }
    }

}