package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(TestWithDB.class)
public class OnlinePracticeTest {
    @Test
    public void 解答済みの問題が存在しない場合_問題が取得されないこと(){
        final LocalDate yesterday = LocalDate.of(2019,8,26);
        final LocalDate today = yesterday.plusDays(1);
        final ObjectId objectId = new ObjectId();

        // setup
        User user1 = new User().save();
        Question question1 = new Question("desc", "adv", objectId, false, false).save();

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user1, today);
        assertTrue(questions.isEmpty());
    }
    @Test
    public void 解答済みの問題が存在する場合_問題が取得されること(){
        final LocalDate yesterday = LocalDate.of(2019,8,26);
        final LocalDate today = yesterday.plusDays(1);
        final ObjectId objectId = new ObjectId();

        // setup
        User user1 = new User().save();
        Question question1 = new Question("desc", "adv", objectId, false, false).save();
        Record record1 = new Record(user1, question1);
        record1.setNextShowDate(today);
        record1.save();

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user1, today);
        assertEquals(1, questions.size());
    }
    @Test
    public void 次回出題日が現在日である問題が存在する場合_その問題が取得できること() {
        final LocalDate yesterday = LocalDate.of(2019,8,26);
        final ObjectId objectId = new ObjectId();
        final LocalDate today = yesterday.plusDays(1);

        // setup
        User user1 = new User().save();
        User user2 = new User().save();

        Question question2 = new Question("desc", "adv", objectId, false, false).save();
        Question question1 = new Question("desc", "adv", objectId, false, false).save();
        Record record1 = new Record(user1, question1);
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user2, question2);
        record2.setNextShowDate(today);
        record2.save();
        // execute
        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user1, today);
        assertEquals(question1.getId(), questions.get(0).getId());
    }

}