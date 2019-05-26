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
    private Category scrum = Category.createIt("name", "Scrum");
    private Category tech = Category.createIt("name", "Tech");

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
        mockQuestion(2, scrum.getStringId());
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongMultiCategoryMessage(){
        mockQuestion(1, scrum.getStringId());
        mockQuestion(1, tech.getStringId());
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("ScrumとTechをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showChangeAdvice() {
        mockQuestion(5, scrum.getStringId());
        OnlineTest onlineTest = new OnlineTest(5);

        onlineTest.setCorrectAnswerCount(4);
        assertEquals("", onlineTest.getCategoryMessage());

        onlineTest.setCorrectAnswerCount(1);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void shouldReturn2CorrectAnswer() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0, "category", String.valueOf(scrum.getId()));
        String id = question.getStringId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = QuestionOption.<QuestionOption>createIt(id, "desc1", true).getStringId();
        answeredOption[1] = QuestionOption.<QuestionOption>createIt(id, "desc2", true).getStringId();

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
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0, "category", String.valueOf(scrum.getId()));
        String id = question.getStringId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = QuestionOption.<QuestionOption>createIt(id, "desc1", false).getStringId();
        answeredOption[1] = QuestionOption.<QuestionOption>createIt(id, "desc2", true).getStringId();
        QuestionOption.<QuestionOption>createIt(id, "desc3", true).getStringId();

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
        mockQuestion(3,scrum.getStringId());
        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList("0"));
        assertEquals(1, onlineTest.answers.size());
    }

    @Test
    public void calculateCorrectRate() {
        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption it = QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList(it.getStringId()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(100, correctRate);
    }

    @Test
    public void calculateCorrectRate2() {
        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d1", true);
        QuestionOption wrongOption = QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Arrays.asList(wrongOption.getStringId()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(0, correctRate);
    }

    @Test
    public void calculateCorrectRate70percent() {

        Question q1 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption c1 = QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q1.getStringId(), "d2", false);

        Question q2 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption c2 = QuestionOption.<QuestionOption>createIt(q2.getStringId(), "d1", false);
        QuestionOption.<QuestionOption>createIt(q2.getStringId(), "d2", true);

        Question q3 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption c3 = QuestionOption.<QuestionOption>createIt(q3.getStringId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q3.getStringId(), "d2", false);

        Question q4 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption c4 = QuestionOption.<QuestionOption>createIt(q4.getStringId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q4.getStringId(), "d2", false);

        Question q5 = Question.createIt("description", "d1", "advice", "a1", "is_multi_question", 0, "category", scrum.getStringId());
        QuestionOption.<QuestionOption>createIt(q5.getStringId(), "d1", true);
        QuestionOption c5 = QuestionOption.<QuestionOption>createIt(q5.getStringId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(5);
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> o.toString()).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().getCorrectOption().stream().map(o -> "nonexist").collect(Collectors.toList()) );

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();

        assertEquals(80, correctRate);
    }

    private void mockQuestion(int numberOfQuestion, String category) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", category));
    }

    private void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", String.valueOf(scrum.getStringId())));
    }

}
