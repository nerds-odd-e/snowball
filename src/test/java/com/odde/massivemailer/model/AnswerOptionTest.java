package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class AnswerOptionTest {

    @Test
    public void shouldBeAbleToPersistOption(){
        AnswerOption answerOption = AnswerOption.createIt("description","desc","question_id",1, "is_correct", 0);
        assertThat(answerOption.getLongId(), is(not(nullValue())));
    }

}
