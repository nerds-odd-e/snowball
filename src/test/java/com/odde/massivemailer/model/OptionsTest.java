package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class OptionsTest {
    @Before
    public void setup() {
        for (int i = 0; i < 2; i++) {
            Options option = new Options();
            option.setQuestionId(i);
            option.setDescription("option" + i);
            option.setIsCorrect(true);
            option.saveIt();
        }
    }

    @Test
    public void testShouldFindByQuestion() throws Exception {
        List<Options> options = Options.findByQuestionId(1);
        assertEquals(1, options.size());
        assertEquals(1, options.get(0).getQuestionId());
        assertEquals("option1", options.get(0).getDescription());
        assertTrue(options.get(0).getIsCorrect());
    }
}