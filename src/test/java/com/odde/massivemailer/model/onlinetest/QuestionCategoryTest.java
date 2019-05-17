package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestWithDB.class)
public class QuestionCategoryTest {

    @Test
    public void createQuestionCategoryWithNameTest() {
        QuestionCategory categoryFirst = QuestionCategory.createQuestionCategory("Healthy food");
        assertNotNull(categoryFirst);
        assertEquals("Healthy food", categoryFirst.getName());
        QuestionCategory categorySecond = QuestionCategory.createQuestionCategory("Long vacation");
        assertNotNull(categorySecond);
        assertEquals("Long vacation", categorySecond.getName());
    }


}