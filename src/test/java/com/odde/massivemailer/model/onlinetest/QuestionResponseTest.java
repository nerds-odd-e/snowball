package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class QuestionResponseTest {

    @Test
    public void shouldBeAbleToPersistQuestionResponse(){
        QuestionResponse questionResponse = QuestionResponse.createIt("test_id", "TestId1","question_id", 1 ,"is_answer_correct",true);
        assertThat(questionResponse.getLongId(), is(not(nullValue())));
    }

}
