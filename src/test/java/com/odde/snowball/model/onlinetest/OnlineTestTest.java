package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class OnlineTestTest {
    private static final QuestionOption correctOption1 = new QuestionOption("desc1", true);
    private static final QuestionOption correctOption2 = new QuestionOption("desc2", true);
    private static final QuestionOption wrongOption = new QuestionOption("desc1", false);
    private Category scrum = Category.create("Scrum");
    private Category retro = Category.create("Retro");

    private User user = new User("email@email.com");

    @Before
    public void setUp() {
        user.save();
    }

    @Test
    public void shouldNotGetANewOnlineTestWithNQuestionIdsIfEnoughQuestionsInDatabase() {
        OnlineTest newOnlineTest = OnlineQuiz.createOnlineQuiz(5);
        assertEquals(0, newOnlineTest.getNumberOfQuestions());
    }

    @Test
    public void shouldGetNextQuestionWhenHaveMoreQuestionsAvailable() {
        mockQuestion(5);
        OnlineTest newOnlineTest = OnlineQuiz.createOnlineQuiz(5);
        assertEquals(5, newOnlineTest.getNumberOfQuestions());
    }

    @Test
    public void answerCorrectly() {
        createQuestionWith(correctOption1, correctOption2);
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        Answer answer = onlineTest.answerCurrentQuestion(stringIdsOf(correctOption1, correctOption2), user, LocalDate.now());
        assertTrue(answer.isCorrect());
    }
    @Test
    public void answerIncorrectly() {
        createQuestionWith(wrongOption, correctOption2, correctOption1);
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        Answer answer = onlineTest.answerCurrentQuestion(stringIdsOf(wrongOption, correctOption2), user, LocalDate.now());
        assertFalse(answer.isCorrect());
    }

    @Test
    public void answerCurrentQuestion() {
        mockQuestion(3, scrum.getId());
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(new ObjectId().toString()), user, LocalDate.now());
        assertEquals(1, onlineTest.getNumberOfAnsweredQuestions());
        assertEquals(0, onlineTest.testResult().getCorrectAnswerCount());
    }

    @Test
    public void calculateCorrectRate() {
        Question q1 = new Question("d1", "a1", scrum.getId(), false, false);
        QuestionOption it = q1.addOption(new QuestionOption("d1", true));
        q1.addOption(new QuestionOption("d2", false));
        q1.save();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(it.stringId()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();
        assertEquals(100, correctRate);
    }

    @Test
    public void calculateCorrectRate2() {
        Question q1 = new Question("d1", "a1", scrum.getId(), false, false);
        q1.addOption(new QuestionOption("d1", true));
        QuestionOption wrongOption = q1.addOption(new QuestionOption("d2", false));
        q1.save();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(wrongOption.stringId()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();
        assertEquals(0, correctRate);
    }

    @Test
    public void calculateCorrectRate80percent() {

        new Question("d1", "a1", scrum.getId(), false, false)
                .withOption("d1", true)
                .withOption("d2", false)
                .save();

        new Question("d1", "a1", scrum.getId(), false, false)
                .withOption("d1", false)
                .withOption("d2", true)
                .save();

        new Question("d1", "a1", scrum.getId(), false, false)
                .withOption("d1", true)
                .withOption("d2", false)
                .save();

        new Question("d1", "a1", scrum.getId(), false, false)
                .withOption("d1", true)
                .withOption("d2", false)
                .save();

        new Question("d1", "a1", scrum.getId(), false, false)
                .withOption("d1", true)
                .withOption("d2", false)
                .save();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(5);
        for (int i = 0; i < 4; i++) {
            onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions(), user, LocalDate.now());
        }
        onlineTest.answerCurrentQuestion(Collections.singletonList(new ObjectId().toString()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();

        assertEquals(80, correctRate);
    }

    private List<Question> mockQuestion(int numberOfQuestion, ObjectId category) {
        return IntStream.range(0, numberOfQuestion).mapToObj(index -> new Question("desc" + index, "adv" + index, category, false, false).save()).collect(Collectors.toList());
    }

    @Test
    public void should_return_completed_test_page_if_test_ends() {
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(0);
        assertEquals(onlineTest.endPageName(), "/onlinetest/end_of_test.jsp");
    }

    @Test
    public void should_return_completed_practice_if_practice_ends() {
        User user = new User();
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 10);
        assertEquals(onlineTest.endPageName(), "/practice/completed_practice.jsp");
    }

    private void mockQuestion(int numberOfQuestion) {
        mockQuestion(numberOfQuestion, scrum.getId());
    }

    @Test
    public void 問題に解答したら次回出題日が更新される() {
        User user = new User();
        LocalDate answerDate = LocalDate.of(2019, 9, 1);
        Question question = mockQuestion(1, retro.getId()).get(0);
        question.recordQuestionForUser(
                user,
                answerDate
        );

        LocalDate actual = Record.getOrInitializeRecord(user, question).getNextShowDate();
        LocalDate expected = answerDate.plusDays(Record.CYCLE.get(0));

        assertEquals(expected, actual);
    }

    private List<String> stringIdsOf(QuestionOption... options) {
        return Arrays.stream(options).map(QuestionOption::stringId).collect(Collectors.toList());
    }

    private void createQuestionWith(QuestionOption... options) {
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false);
        for (QuestionOption option : options) {
            question.withOption(option);
        }
        question.save();
    }
}
