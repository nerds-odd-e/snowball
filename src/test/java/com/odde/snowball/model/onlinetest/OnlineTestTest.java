package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class OnlineTestTest {
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");

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
        mockQuestion(2, scrum.getId());
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(2);
        assertEquals("", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongSingleCategoryMessage(){
        mockQuestion(2, scrum.getId());
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongMultiCategoryMessage(){
        mockQuestion(1, scrum.getId());
        mockQuestion(1, tech.getId());
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("ScrumとTechをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showChangeAdvice() {
        mockQuestion(5, scrum.getId());
        OnlineTest onlineTest = new OnlineTest(5);

        onlineTest.setCorrectAnswerCount(4);
        assertEquals("", onlineTest.getCategoryMessage());

        onlineTest.setCorrectAnswerCount(1);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void shouldReturn2CorrectAnswer() {
        Question question = new Question("desc1", "adv1", scrum.getId(), false, false).save();
        ObjectId id = question.getId();

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
        Question question = new Question("desc1", "adv1", scrum.getId(),false, false).save();
        ObjectId id = question.getId();

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
        mockQuestion(3,scrum.getId());
        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(new ObjectId().toString()));
        assertEquals(1, onlineTest.answers.size());
    }

    @Test
    public void calculateCorrectRate() {
        Question q1 = new Question("d1", "a1", scrum.getId(),false, false).save();
        QuestionOption it = QuestionOption.<QuestionOption>createIt(q1.getId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q1.getId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(it.getStringId()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(100, correctRate);
    }

    @Test
    public void calculateCorrectRate2() {
        Question q1 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption.<QuestionOption>createIt(q1.getId(), "d1", true);
        QuestionOption wrongOption = QuestionOption.<QuestionOption>createIt(q1.getId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(1);
        onlineTest.answerCurrentQuestion(Collections.singletonList(wrongOption.getStringId()));

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();
        assertEquals(0, correctRate);
    }

    @Test
    public void calculateCorrectRate70percent() {

        Question q1 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c1 = QuestionOption.<QuestionOption>createIt(q1.getId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q1.getId(), "d2", false);

        Question q2 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c2 = QuestionOption.<QuestionOption>createIt(q2.getId(), "d1", false);
        QuestionOption.<QuestionOption>createIt(q2.getId(), "d2", true);

        Question q3 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c3 = QuestionOption.<QuestionOption>createIt(q3.getId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q3.getId(), "d2", false);

        Question q4 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption c4 = QuestionOption.<QuestionOption>createIt(q4.getId(), "d1", true);
        QuestionOption.<QuestionOption>createIt(q4.getId(), "d2", false);

        Question q5 = new Question("d1", "a1", scrum.getId(), false, false).save();
        QuestionOption.<QuestionOption>createIt(q5.getId(), "d1", true);
        QuestionOption c5 = QuestionOption.<QuestionOption>createIt(q5.getId(), "d2", false);

        OnlineTest onlineTest = new OnlineTest(5);
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(ObjectId::toString).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(ObjectId::toString).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(ObjectId::toString).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(ObjectId::toString).collect(Collectors.toList()) );
        onlineTest.answerCurrentQuestion(onlineTest.getCurrentQuestion().correctOptions().stream().map(o -> new ObjectId().toString()).collect(Collectors.toList()) );

        TestResult result = onlineTest.generateTestResult();

        int correctRate = result.correctPercentage();

        assertEquals(80, correctRate);
    }

    @Test
    public void onlineTestObjectShouldHaveTestTypeAttributePractice() {
        User user = new User();
        OnlineTest onlineTest = OnlineTest.getOnlineTest(user.getId());
        assertEquals(onlineTest.getTestType(), TestType.Practice);
    }
    @Test
    public void onlineTestObjectShouldHaveTestTypeAttributeOnlineTest() {
        OnlineTest onlineTest = new OnlineTest(1);
        assertEquals(onlineTest.getTestType(), TestType.OnlineTest);
    }

    private void mockQuestion(int numberOfQuestion, ObjectId category) {
        IntStream.range(0, numberOfQuestion).forEach(index -> new Question("desc" + index, "adv" + index, category, false, false).save());
    }

    private void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> new Question("desc" + index, "adv" + index, scrum.getId(), false, false).save());
    }

}
