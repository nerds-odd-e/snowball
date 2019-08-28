package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
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
    public void shouldReturn2CorrectAnswer() {
        final String[] answeredOption = new String[2];
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false);
        answeredOption[0] = question.addOption("desc1", true).stringId();
        answeredOption[1] = question.addOption("desc2", true).stringId();
        question.save();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);

        Answer answer = onlineTest.answerCurrentQuestion(Arrays.asList(answeredOption), user, LocalDate.now());
        boolean result = answer.isCorrect();
        assertTrue(result);
    }

    @Test
    public void shouldReturnOneIncorrectAndOneCorrectAnswer() {
        final String[] answeredOption = new String[2];
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false);
        answeredOption[0] = question.addOption("desc1", false).stringId();
        answeredOption[1] = question.addOption("desc2", true).stringId();
        question.addOption("desc3", true);
        question.save();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        Answer answer = onlineTest.answerCurrentQuestion(Arrays.asList(answeredOption), user, LocalDate.now());
        boolean result = answer.isCorrect();
        assertFalse(result);
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
        QuestionOption it = q1.addOption("d1", true);
        q1.addOption("d2", false);
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
        q1.addOption("d1", true);
        QuestionOption wrongOption = q1.addOption("d2", false);
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

    @Test
    public void practiceShouldShowAllDueQuestionsWhenTheyAreDue() {
        User user = new User();
        List<Question> questions = mockQuestion(3, retro.getId());
        questions.get(0).recordQuestionForUser(user, LocalDate.now().minusDays(4));
        questions.get(0).recordQuestionForUser(user, LocalDate.now().minusDays(3));
        questions.get(1).recordQuestionForUser(user, LocalDate.now().minusDays(3));
        questions.get(2).recordQuestionForUser(user, LocalDate.now());
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 10);
        assertEquals(2, onlineTest.getNumberOfQuestions());
        Set<ObjectId> expected = new HashSet<ObjectId>();
        expected.add(questions.get(0).getId());
        expected.add(questions.get(1).getId());
        Set<ObjectId> actual = onlineTest.getQuestions().stream().map(Entity::getId).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void practiceShouldShowNotShowQuestionWhenCycleIsComplete() {
        User user = new User();
        Question question = mockQuestion(1, retro.getId()).get(0);
        question.recordQuestionForUser(user, LocalDate.now().minusDays(38));
        question.recordQuestionForUser(user, LocalDate.now().minusDays(37));
        question.recordQuestionForUser(user, LocalDate.now().minusDays(35));
        question.recordQuestionForUser(user, LocalDate.now().minusDays(31));
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 10);
        assertEquals(0, onlineTest.getNumberOfQuestions());
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

}
