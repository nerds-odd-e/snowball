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
        QuestionCategory scrumCategory = QuestionCategory.createQuestionCategory("Scrum");
        assertNotNull(scrumCategory);
        assertEquals("Scrum", scrumCategory.getName());
        QuestionCategory techCategory = QuestionCategory.createQuestionCategory("Tech");
        assertNotNull(techCategory);
        assertEquals("Tech", techCategory.getName());
    }


}