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
    private Category tech = Category.create("Tech");
    private Category retro = Category.create("Retro");

    private User user = new User("email@email.com");

    @Before
    public void setUp(){
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
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false).save();
        ObjectId id = question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = QuestionOption.createIt(id, "desc1", true).getStringId();
        answeredOption[1] = QuestionOption.createIt(id, "desc2", true).getStringId();

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);

        Answer answer = onlineTest.answerCurrentQuestion(Arrays.asList(answeredOption), user, LocalDate.now());
        boolean result = answer.isCorrect();
        assertTrue(result);
    }

    @Test
    public void shouldReturnOneIncorrectAndOneCorrectAnswer() {
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false).save();
        ObjectId id = question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = QuestionOption.createIt(id, "desc1", false).getStringId();
        answeredOption[1] = QuestionOption.createIt(id, "desc2", true).getStringId();
        QuestionOption.createIt(id, "desc3", true).getStringId();

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
        Question q1 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption it = QuestionOption.createIt(q1.getId(), "d1", true);
        QuestionOption.createIt(q1.getId(), "d2", false);

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(it.getStringId()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();
        assertEquals(100, correctRate);
    }

    @Test
    public void calculateCorrectRate2() {
        Question q1 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption.createIt(q1.getId(), "d1", true);
        QuestionOption wrongOption = QuestionOption.createIt(q1.getId(), "d2", false);

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(wrongOption.getStringId()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();
        assertEquals(0, correctRate);
    }

    @Test
    public void calculateCorrectRate80percent() {

        Question q1 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c1 = QuestionOption.createIt(q1.getId(), "d1", true);
        QuestionOption.createIt(q1.getId(), "d2", false);

        Question q2 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c2 = QuestionOption.createIt(q2.getId(), "d1", false);
        QuestionOption.createIt(q2.getId(), "d2", true);

        Question q3 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c3 = QuestionOption.createIt(q3.getId(), "d1", true);
        QuestionOption.createIt(q3.getId(), "d2", false);

        Question q4 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c4 = QuestionOption.createIt(q4.getId(), "d1", true);
        QuestionOption.createIt(q4.getId(), "d2", false);

        Question q5 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption.createIt(q5.getId(), "d1", true);
        QuestionOption c5 = QuestionOption.createIt(q5.getId(), "d2", false);

        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(5);
        for (int i=0;i<4;i++) {
            onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(ObjectId::toString).collect(Collectors.toList()), user, LocalDate.now());
        }
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(o -> new ObjectId().toString()).collect(Collectors.toList()), user, LocalDate.now());

        TestResult result = onlineTest.testResult();

        int correctRate = result.correctPercentage();

        assertEquals(80, correctRate);
    }

    @Test
    public void practiceShouldShowAllDueQuestionsWhenTheyAreDue() {
        User user = new User();
        List<Question> questions = mockQuestion(3, retro.getId());
        questions.get(0).recordQuestionForUser(user, LocalDate.now().minusDays(3));
        questions.get(0).recordQuestionForUser(user, LocalDate.now().minusDays(2));
        questions.get(1).recordQuestionForUser(user, LocalDate.now().minusDays(1));
        questions.get(2).recordQuestionForUser(user, LocalDate.now());
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user);
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
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user);
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
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user);
        assertEquals(onlineTest.endPageName(), "/practice/completed_practice.jsp");
    }


    private void mockQuestion(int numberOfQuestion) {
        mockQuestion(numberOfQuestion, scrum.getId());
    }

}
