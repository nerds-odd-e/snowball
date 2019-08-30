package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(TestWithDB.class)
public class OnlinePracticeTest {
    @Test
    public void 解答済みの問題が存在しない場合_問題が取得されないこと() {
        // setup
        User user1 = new User().save();
        generateQuestion(1);

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetitions(1, user1, null);
        assertTrue(questions.isEmpty());
    }

    @Test
    public void 解答済みの問題が存在する場合_問題が取得されること() {
        // setup
        User user1 = new User().save();
        List<Question> questions = generateQuestion(1);

        new Record(user1, questions.get(0)).save();

        List<Question> actual =
                OnlinePractice.findSpaceBasedRepetitions(1, user1, null);
        assertEquals(1, actual.size());
    }

    @Test
    public void 自分が解答済みの問題が存在する場合_その問題が取得できること() {
        // setup
        User user1 = new User().save();
        User user2 = new User().save();

        List<Question> questions = generateQuestion(2);

        new Record(user1, questions.get(0)).save();
        new Record(user2, questions.get(1)).save();
        // execute
        List<Question> actual =
                OnlinePractice.findSpaceBasedRepetitions(2, user1, null);
        assertEquals(questions.get(0).getId(), actual.get(0).getId());
        assertEquals(1, actual.size());
    }

    @Test
    public void 自分への次回出題日が指定の日付以前の質問が存在する場合_その問題が取得できること() {
        final LocalDate yesterday = LocalDate.of(2019, 8, 26);
        final LocalDate today = yesterday.plusDays(1);

        User user1 = new User().save();
        List<Question> questions = generateQuestion(2);

        Record record1 = new Record(user1, questions.get(0));
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user1, questions.get(1));
        record2.setNextShowDate(today.plusDays(1));
        record2.save();

        // execute
        List<Question> actual =
                OnlinePractice.findSpaceBasedRepetitions(2, user1, today);
        assertEquals(questions.get(0).getId(), actual.get(0).getId());
        assertEquals(1, actual.size());
    }

    @Test
    public void 指定の件数以下で次回出題日が古い順で取得されること() {
        final LocalDate yesterday = LocalDate.of(2019, 8, 26);
        final LocalDate today = yesterday.plusDays(1);

        // setup
        User user1 = new User().save();
        List<Question> questions = generateQuestion(3);

        Record record1 = new Record(user1, questions.get(0));
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user1, questions.get(1));
        record2.setNextShowDate(today);
        record2.save();
        Record record3 = new Record(user1, questions.get(2));
        record3.setNextShowDate(today.minusDays(1));
        record3.save();

        // execute
        List<Question> actual =
                OnlinePractice.findSpaceBasedRepetitions(2, user1, today);
        assertEquals(questions.get(2).getId(), actual.get(0).getId());
        assertEquals(2, actual.size());
    }

    @Test
    public void 指定の件数以下で次回出題日と最終更新日が古い順で取得されること() {
        final LocalDate yesterday = LocalDate.of(2019, 8, 26);
        final LocalDate today = yesterday.plusDays(1);

        // setup
        User user1 = new User().save();
        List<Question> questions = generateQuestion(4);

        Record record1 = new Record(user1, questions.get(0));
        record1.setNextShowDate(today);
        record1.save();
        Record record2 = new Record(user1, questions.get(1));
        record2.setNextShowDate(today);
        record2.save();
        Record record3 = new Record(user1, questions.get(2));
        record3.setNextShowDate(today.minusDays(1));
        record3.setLastUpdated(today);
        record3.save();
        Record record4 = new Record(user1, questions.get(3));
        record4.setNextShowDate(today.minusDays(1));
        record4.setLastUpdated(today.minusDays(1));
        record4.save();

        // execute
        List<Question> actual =
                OnlinePractice.findSpaceBasedRepetitions(3, user1, today);
        assertEquals(questions.get(3).getId(), actual.get(0).getId());
        assertEquals(3, actual.size());
    }

    @Test
    public void 最出題5回問題が取得できること() {
        final LocalDate today = LocalDate.now();
        User user = new User().save();
        List<Question> questions = generateQuestion(1);
        Record record = Record.getOrInitializeRecord(user, questions.get(0));
        record.setLastUpdated(today);
        record.setNextShowDate(today);

        for (int i = 1; i <= 5; i++) {
            record.setCycleState(i);
            record.save();
            List<Question> actual = OnlinePractice.findSpaceBasedRepetitions(1, user, today);
            assertFalse("index is " + i, actual.isEmpty());
        }
    }


    @Test
    public void 問題に最終回まで解答した場合_問題が取得されないこと() {
        final LocalDate yesterday = LocalDate.of(2019, 8, 1);
        final LocalDate today = yesterday.plusDays(1);
        // setup
        User user = new User().save();
        List<Question> Questions = generateQuestion(1);

        Record record = Record.getOrInitializeRecord(user, Questions.get(0));
        record.setLastUpdated(yesterday);
        record.setCycleState(Record.CYCLE.size() + 1);
        record.setNextShowDate(today);
        record.save();

        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetitions(1, user, today);
        assertTrue(questions.isEmpty());
    }


    @Test
    public void 一度も解答してない場合_次回出題日はnullである() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        assertNull(record.getNextShowDate());
    }

    @Test
    public void 問題に解答した場合_次回出題日が設定される() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        List<LocalDate> answeredDateList = Arrays.asList(
                LocalDate.of(2019, 8, 1),
                LocalDate.of(2020, 8, 1),
                LocalDate.of(2021, 8, 5),
                LocalDate.of(2022, 8, 15),
                LocalDate.of(2023, 9, 14)
        );
        for (int i = 0; i <= Record.CYCLE.size() - 1; i++) {
            LocalDate answeredDate = answeredDateList.get(i);
            record.update(answeredDate);
            assertEquals(answeredDate.plusDays(Record.CYCLE.get(i)), record.getNextShowDate());
        }
    }

    @Test
    public void 問題に最終回まで解答した場合_次回出題日が設定されない() {
        User user = new User();
        Question question = new Question();
        Record record = Record.getOrInitializeRecord(user, question);
        record.setCycleState(Record.CYCLE.size() - 1);
        record.update(LocalDate.of(2019, 8, 1));
        assertEquals(LocalDate.of(2019, 10, 30), record.getNextShowDate());

        record.update(LocalDate.of(2019, 8, 1));
        assertEquals(LocalDate.of(2019, 10, 30), record.getNextShowDate());

    }

    @Test
    public void 問題が3問存在する時_2問のみ返されること() {
        // setup
        User user = new User().save();

        generateQuestion(3);
        // exercise
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 2);
        // verify
        int numberOfQuestions = onlineTest.getNumberOfQuestions();
        assertEquals(2, numberOfQuestions);
    }

    private List<Question> generateQuestion(int numberOfQuestion) {
        Category category = new Category().save();
        return IntStream.range(0, numberOfQuestion).mapToObj(index -> new Question("desc" + index, "adv" + index, category.getId(), false, false).save()).collect(Collectors.toList());
    }

    @Test
    public void _2問中1問が未回答の場合に未回答の問題だけが出題される() {

        User user = new User().save();
        List<Question> allQuestions = generateQuestion(2);

        Question question1 = allQuestions.get(0);
        question1.recordQuestionForUser(user, LocalDate.now());

        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 2);
        List<Question> questions = onlineTest.getQuestions();
        assertEquals(1, questions.size());
        assertNotEquals(question1.getId(), questions.get(0).getId());
    }

    @Test
    public void _hasNotAnsweredAnyQuestions() {
        User user = new User().save();
        List<Question> allQuestions = generateQuestion(2);

        allQuestions.forEach(question -> question.recordQuestionForUser(user, LocalDate.now()));

        OnlineTest onlinePractice = OnlinePractice.createOnlinePractice(user, 10);

        assertEquals(2, onlinePractice.getQuestions().size());
    }


    @Test
    public void _hasNotAnswered1Questions() {
        User user = new User().save();
        List<Question> allQuestions = generateQuestion(2);

        allQuestions.get(0).recordQuestionForUser(user, LocalDate.now());
        OnlineTest onlinePractice = OnlinePractice.createOnlinePractice(user, 10);

        assertEquals(1, onlinePractice.getQuestions().size());
    }
}