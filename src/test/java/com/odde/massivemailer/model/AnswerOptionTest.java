package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class AnswerOptionTest {

    @Test
    public void shouldBeAbleToPersistOption(){
        AnswerOption answerOption = AnswerOption.createIt("description","desc","question_id",1, "is_correct", 0);
        assertThat(answerOption.getLongId(), is(not(nullValue())));
    }

    @Test
    public void shouldReturnOptionsForQuestion() {
        AnswerOption answerOption1 = AnswerOption.createIt("description","desc","question_id",1, "is_correct", 0);
        AnswerOption answerOption2 = AnswerOption.createIt("description","desc","question_id",1, "is_correct", 0);
        AnswerOption answerOption3 = AnswerOption.createIt("description","desc","question_id",2, "is_correct", 0);

        Collection<AnswerOption> optionsForQuestion = AnswerOption.getOptionsForQuestion(1L);
        optionsForQuestion.forEach(option -> assertEquals(option.getQuestionId(), Long.valueOf(1)));
    }

}
