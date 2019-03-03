package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class OnlineTestTest {

    @Test
    public void shouldNotGetANewOnlineTestWithNQuestionIdsIfEnoughQuestionsInDatabase() {
        OnlineTest newOnlineTest = new OnlineTest(5);
        assertEquals(0, newOnlineTest.getNumberOfQuestions());
    }

    @Test
    public void shouldGetNextQuestionWhenHaveMoreQuestionsAvailable() {
        mockQuestion(5);
        OnlineTest newOnlineTest = new OnlineTest(5);
        assertEquals(5, newOnlineTest.getNumberOfQuestions());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotGetNextQuestionWhenNoMoreQuestionsLeft() {
        OnlineTest newOnlineTest = new OnlineTest(5);
        newOnlineTest.getNextQuestion();
    }

    @Test
    public void shouldNotRepeatQuestions() {
        mockQuestion(6);
        OnlineTest newOnlineTest = new OnlineTest(5);
        Set<Question> questions = new HashSet<>();
        while(newOnlineTest.hasNextQuestion()) {
            questions.add(newOnlineTest.getCurrentQuestion());
            newOnlineTest.addAnsweredQuestionNumber();
        }
        assertEquals(5, questions.size());
    }

    @Test
    public void showMultiCategoryMessage(){
        mockQuestion(2, "1");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(2);
        assertEquals("", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongSingleCategoryMessage(){
        mockQuestion(1, "2");
        mockQuestion(1, "2");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("Techをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongMultiCategoryMessage(){
        mockQuestion(1, "2");
        mockQuestion(1, "1");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("TechとScrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showChangeAdvice() {
        mockQuestion(5, "1");
        OnlineTest onlineTest = new OnlineTest(5);

        onlineTest.setCorrectAnswerCount(4);
        assertEquals("", onlineTest.getCategoryMessage());

        onlineTest.setCorrectAnswerCount(1);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void shouldReturn2CorrectAnswer() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0, "category", String.valueOf(Category.SCRUM.getId()));
        Long id = (Long) question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = AnswerOption.<AnswerOption>createIt("description", "desc1", "question_id", id, "is_correct", 1).getLongId().toString();
        answeredOption[1] = AnswerOption.<AnswerOption>createIt("description", "desc2", "question_id", id, "is_correct", 1).getLongId().toString();

        OnlineTest onlineTest = new OnlineTest(1);

        Answer answer = onlineTest.answerCurrentQuestion(Arrays.asList(answeredOption));
        boolean result = answer.isCorrect();
        assertTrue(result);
    }

    @Test
    public void aQuestionThatWasAnsweredOneDayAgoKnowsItIsOneDayOld() {
        LocalDate answeredTime = LocalDate.now().minusDays(1);
        OnlineTest onlineTest = new OnlineTest(1);

        onlineTest.recordAnswerWithTime(answeredTime);
        long passedDays = onlineTest.getPassedDaysSinceAnswered();
        assertEquals(1, passedDays);
    }

    @Test
    public void shouldReturnOneIncorrectAndOneCorrectAnswer() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0, "category", String.valueOf(Category.SCRUM.getId()));
        Long id = (Long) question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = AnswerOption.<AnswerOption>createIt("description", "desc1", "question_id", id, "is_correct", 0).getLongId().toString();
        answeredOption[1] = AnswerOption.<AnswerOption>createIt("description", "desc2", "question_id", id, "is_correct", 1).getLongId().toString();
        AnswerOption.<AnswerOption>createIt("description", "desc3", "question_id", id, "is_correct", 1).getLongId().toString();

        OnlineTest onlineTest = new OnlineTest(1);
        Answer answer = onlineTest.answerCurrentQuestion(Arrays.asList(answeredOption));
        boolean result = answer.isCorrect();
        assertFalse(result);
    }

    @Test
    public void getEmptyFailedCategoryTestResults() {
        OnlineTest onlineTest = new OnlineTest(0);
        List<CategoryTestResult> failedCategoryTestResults = onlineTest.getFailedCategoryTestResults();

        assertEquals(0, failedCategoryTestResults.size());
    }

    @Test
    public void answerCurrentQuestion() {
        mockQuestion(3,"1");
        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList("0"));
        assertEquals(1, onlineTest.answers.size());
    }

    @Test
    public void calculateCorrectRate() {
        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption it = AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q1.getId(), "is_correct", 1);
        AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q1.getId(), "is_correct", 0);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList(it.getLongId().toString()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(100, correctRate);
    }

    @Test
    public void calculateCorrectRate2() {
        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q1.getId(), "is_correct", 1);
        AnswerOption wrongOption = AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q1.getId(), "is_correct", 0);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList(wrongOption.getLongId().toString()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(0, correctRate);
    }

    @Test
    public void calculateCorrectRate70percent() {

        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption c1 = AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q1.getId(), "is_correct", 1);
        AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q1.getId(), "is_correct", 0);

        Question q2 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption c2 = AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q2.getId(), "is_correct", 0);
        AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q2.getId(), "is_correct", 1);

        Question q3 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption c3 = AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q3.getId(), "is_correct", 1);
        AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q3.getId(), "is_correct", 0);

        Question q4 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption c4 = AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q4.getId(), "is_correct", 1);
        AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q4.getId(), "is_correct", 0);

        Question q5 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", "1");
        AnswerOption.<AnswerOption>createIt("description", "d1", "question_id", q5.getId(), "is_correct", 1);
        AnswerOption c5 = AnswerOption.<AnswerOption>createIt("description", "d2", "question_id", q5.getId(), "is_correct", 0);

        OnlineTest onlineTest = new OnlineTest(5);
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> Long.valueOf(o + 10L).toString()).collect(Collectors.toList()) );

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();

        assertEquals(80, correctRate);
    }

    private static void mockQuestion(int numberOfQuestion, String category) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", category));
    }

    private static void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", String.valueOf(Category.SCRUM.getId())));
    }

}
