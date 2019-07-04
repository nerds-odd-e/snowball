package com.odde.snowball.service;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import com.odde.snowball.model.onlinetest.QuestionOption;
import com.odde.snowball.model.practice.Record;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(TestWithDB.class)
public class RecordQuestionTest {
    private Category retro = Category.create("Retro");

    @Test
    public void whenNoRecordInsertNewDafaultRecordTest() {
        User user = createUser();
        Question firstQuestion = createQuestionWithOptions(retro.getId());
        OnlineTest onlineTest = createPracticeForFirstTime(user);
        Question shownQuestion = onlineTest.getQuestionsByRecords(user.getId());
        Assert.assertEquals(firstQuestion, shownQuestion);
    }

    @Test
    public void whenUserHasMoreThanOneExistingPracticeRecords() {
        User user = createUser();
        Question expectedQuestion = createMockUserDataInRecordTable(user);
        OnlineTest onlineTest = createPracticeForFirstTime(user);
        Question actualQuestion = onlineTest.getQuestionsByRecords(user.getId());
        Assert.assertEquals(expectedQuestion, actualQuestion);
    }

    private Question createMockUserDataInRecordTable(User user) {
        Question expectedQuestion = null;
        for (int i = 0; i < 3; i++) {
            Question question = createQuestionWithOptions(retro.getId());
            if (i < 1) {
                expectedQuestion = question;
            }
            Record record = new Record(user.getId(), question.getId(), new Date(), i * 2);
            record.save();
        }
        return expectedQuestion;

    }

    private OnlineTest createPracticeForFirstTime(User inputUser) {
        return OnlineTest.getOnlineTest(inputUser.getId(), "Retro");
    }

    private User createUser() {
        User user = new User("mary@example.com");
        user.setupPassword("abcd1234");
        user.save();
        return user;
    }

    private Question createQuestionWithOptions(ObjectId categoryId) {
        Question question = new Question("What is Retro? 5 is correct answer", "Retro advvice", categoryId, true, false).save();
        ObjectId id = question.getId();
        QuestionOption.createIt(id, "desc1", false);
        QuestionOption.createIt(id, "desc2", false);
        QuestionOption.createIt(id, "desc3", false);
        QuestionOption.createIt(id, "desc4", false);
        QuestionOption.createIt(id, "desc5", true);

        return question;
    }
}
