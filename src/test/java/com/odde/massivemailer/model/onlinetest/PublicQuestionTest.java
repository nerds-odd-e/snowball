package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class PublicQuestionTest {
    @Test
    public void shouldGetPublicQuestionById() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "category", "scrum");
        PublicQuestion publicQuestion = PublicQuestion.createIt("question_id", question.getLongId().toString());
        Long id = publicQuestion.getLongId();
        PublicQuestion actual = PublicQuestion.getById(id);
        assertThat(actual, is(equalTo(publicQuestion)));
    }
}

