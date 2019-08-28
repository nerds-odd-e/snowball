package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.Arrays;
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

    @Test
    public void 指定の件数以下で次回出題日が古い順で取得されること() {
        final LocalDate yesterday = LocalDate.of(2019,8,26);
        final LocalDate today = yesterday.plusDays(1);
        final ObjectId objectId = new ObjectId();

        // setup
        User user1 = new User().save();

        Question question1 = new Question("desc", "adv", objectId, false, false).save();
        Question question2 = new Question("desc", "adv", objectId, false, false).save();
        Question question3 = new Question("desc", "adv", objectId, false, false).save();

        Record record1 = new Record(user1, question1);
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user1, question2);
        record2.setNextShowDate(today);
        record2.save();

        Record record3 = new Record(user1, question3);
        record3.setNextShowDate(today.minusDays(1));
        record3.save();


        // execute
        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(2, user1, today);
        assertEquals(question3.getId(), questions.get(0).getId());
        assertEquals(2, questions.size());
    }


    @Test
    public void 一度も解答してない場合次回出題日はnullである() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.of(2019,8,1));
        record.setCycleState(0);
        record.setNextShowDate();
        assertEquals(null,record.getNextShowDate());

    }

    @Test
    public void 問題に解答したら次回出題日が設定される() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.of(2019,8,1));
        List<LocalDate> expected = Arrays.asList(
            LocalDate.of(2019,8,2),
            LocalDate.of(2019,8,4),
            LocalDate.of(2019,8,11),
            LocalDate.of(2019,8,31),
            LocalDate.of(2019,10,30)
        );
        for (int i = 1; i <= record.getCycle().size(); i++) {
            record.setCycleState(i);
            record.setNextShowDate();
            assertEquals(expected.get(i -1),record.getNextShowDate());
        }
    }

    @Test
    public void 問題に最終回まで解答したら次回出題日が設定されない() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.of(2019, 8, 1));

        record.setCycleState(record.getCycle().size());
        record.setNextShowDate();
        assertEquals(LocalDate.of(2019, 10, 30), record.getNextShowDate());

        record.setCycleState(record.getCycle().size() + 1);
        record.setNextShowDate();
        assertEquals(LocalDate.of(2019, 10, 30), record.getNextShowDate());

    }

//    @Test
    public void name() {
        User user1 = new User().save();
        Category scrum = Category.create("Scrum");
        Question question1 = new Question("Q1", "adv", scrum.getId(), false, false).save();
        Record record1 = new Record(user1, question1);
        record1.setNextShowDate();
        record1.save();

        Question question2 = new Question("Q2", "adv", scrum.getId(), false, false).save();
        Record record2 = new Record(user1, question2);
        record2.setNextShowDate();
        record2.save();

        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user1, 10);
        List<Question> expectedList = Arrays.asList(question1, question2);

        assertEquals(expectedList.get(0).getDescription(), onlineTest.getQuestions().get(0).getDescription());
        assertEquals(expectedList.get(1).getDescription(), onlineTest.getQuestions().get(1).getDescription());
    }

    @Test
    public void 問題が11問存在する時_10問のみ返されること() {
        // setup
        User user = new User().save();

        Category scrum = Category.create("Scrum");
        final ObjectId objectId = scrum.getId();
        for (int i = 0 ; i < 3 ; i++) {
            new Question("desc", "adv", objectId, false, false).save();
        }
        // exercise
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 2);
        // verify
        int numberOfQuestions = onlineTest.getNumberOfQuestions();
        assertEquals(2, numberOfQuestions);
    }
}