package com.odde.snowball.service;

import com.odde.TestWithDB;
import com.odde.snowball.controller.EnrollParticipantController;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import com.odde.snowball.model.onlinetest.QuestionOption;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(TestWithDB.class)
public class RecordQuestionTest {
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");
    private Category team = Category.create("Team");
    private Category retro = Category.create("Retro");

    @Before
    public void setUpMockService() {

    }

    @Test
    public void whenNoRecordInsertNewDafaultRecordTest(){
        User user = createUser();
        ObjectId userid = user.getId();
        OnlineTest onlineTest = OnlineTest.getOnlineTest(userid, "Retro");
        Question firstQuestion = createQuestionWithOptions(retro.getId());
        Question question = onlineTest.getQuestionsByRecords(userid);
        Assert.assertEquals(firstQuestion,question);
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
