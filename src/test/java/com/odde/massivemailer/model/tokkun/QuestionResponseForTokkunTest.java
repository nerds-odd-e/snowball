package com.odde.massivemailer.model.tokkun;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.onlinetest.Question;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class QuestionResponseForTokkunTest {

    private User createUser() {
        User user = new User("hoge@test.com");
        user.setPassword("password");
        user.saveIt();
        return user;
    }

    @Test
    public void shouldBeAbleToPersistQuestionResponseForTokkun() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "category", "scrum");
        QuestionResponseForTokkun questionResponse = QuestionResponseForTokkun.createIt(
                "user_id", createUser().getId(), "question_id", question.getId(), "counter", 1, "answered_at", "2019-05-12 10:20:23");
        assertThat(questionResponse.getLongId(), is(not(nullValue())));
    }

    @Ignore
    public void selectUserResponseQuestions() {
        User user = createUser();
        List<QuestionResponseForTokkun> tokkuns = QuestionResponseForTokkun.selectUserResponsedTokens(user);
        assertThat(CollectionUtils.isEmpty(tokkuns), is(false));
        for (QuestionResponseForTokkun tokkun : tokkuns) {
            assertThat(tokkun.getLongId(), is(equals(user.getLongId())));
        }
    }

}
