package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class QuestionCategoryTest {

    @Test
    public void createQuestionCategoryTest() {
        QuestionCategory questionCategory = QuestionCategory.createQuestionCategory("Scrum");
        assertNotNull(questionCategory);
        assertEquals("Scrum", questionCategory.getName());
    }

    @Test
    public void createQuestionCategoryTest2() {
        QuestionCategory questionCategory = QuestionCategory.createQuestionCategory("Tech");
        assertNotNull(questionCategory);
        assertEquals("Tech", questionCategory.getName());
    }


}