package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(TestWithDB.class)
public class OnlinePracticeTest {
    @Test
    public void 解答済みの問題が存在しない場合_問題が取得されないこと() {
        // setup
        User user1 = new User().save();
        new Question("desc", "adv", new ObjectId(), false, false).save();

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user1, null);
        assertTrue(questions.isEmpty());
    }
    @Test
    public void 解答済みの問題が存在する場合_問題が取得されること() {
        // setup
        User user1 = new User().save();
        Question question1 = new Question("desc", "adv", new ObjectId(), false, false).save();
        new Record(user1, question1).save();

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user1, null);
        assertEquals(1, questions.size());
    }

    @Test
    public void 自分が解答済みの問題が存在する場合_その問題が取得できること() {
        final ObjectId objectId = new ObjectId();

        // setup
        User user1 = new User().save();
        User user2 = new User().save();

        Question question1 = new Question("desc", "adv", objectId, false, false).save();
        Question question2 = new Question("desc", "adv", objectId, false, false).save();
        new Record(user1, question1).save();
        new Record(user2, question2).save();
        // execute
        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(2, user1, null);
        assertEquals(question1.getId(), questions.get(0).getId());
        assertEquals(1, questions.size());
    }

    @Test
    public void 自分への次回出題日が指定の日付以前の質問が存在する場合_その問題が取得できること() {
        final LocalDate yesterday = LocalDate.of(2019,8,26);
        final LocalDate today = yesterday.plusDays(1);
        final ObjectId objectId = new ObjectId();

        // setup
        User user1 = new User().save();

        Question question1 = new Question("desc", "adv", objectId, false, false).save();
        Question question2 = new Question("desc", "adv", objectId, false, false).save();
        Record record1 = new Record(user1, question1);
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user1, question2);
        record2.setNextShowDate(today.plusDays(1));
        record2.save();
        // execute
        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(2, user1, today);
        assertEquals(question1.getId(), questions.get(0).getId());
        assertEquals(1, questions.size());
    }
    //TODO
    // 指定の件数以下で次回出題日、最終回答日が古い順で取得されること

    // @Test
    public void 問題に解答したら次回出題日が更新される() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        record.setNextShowDate();
        assertEquals(LocalDate.of(2019,8,28),record.getNextShowDate());

    }
}