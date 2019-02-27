package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.javalite.activejdbc.Model;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0);
        Long id = (Long) question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = AnswerOption.<AnswerOption>createIt("description", "desc1", "question_id", id, "is_correct", 1).getLongId().toString();
        answeredOption[1] = AnswerOption.<AnswerOption>createIt("description", "desc2", "question_id", id, "is_correct", 1).getLongId().toString();

        OnlineTest onlineTest = new OnlineTest(1);
        boolean result = onlineTest.checkAnswer(answeredOption);
        assertTrue(result);
    }

    @Test
    public void shouldReturnOneIncorrectAndOneCorrectAnswer() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1", "is_multi_question", 0);
        Long id = (Long) question.getId();

        final String[] answeredOption = new String[2];
        answeredOption[0] = AnswerOption.<AnswerOption>createIt("description", "desc1", "question_id", id, "is_correct", 0).getLongId().toString();
        answeredOption[1] = AnswerOption.<AnswerOption>createIt("description", "desc2", "question_id", id, "is_correct", 1).getLongId().toString();
        AnswerOption.<AnswerOption>createIt("description", "desc3", "question_id", id, "is_correct", 1).getLongId().toString();

        OnlineTest onlineTest = new OnlineTest(1);
        boolean result = onlineTest.checkAnswer(answeredOption);
        assertFalse(result);
    }

    @Test
    public void shouldQuestionsNumberByEachCategory(){
        mockQuestion(1,"1");
        OnlineTest onlineTest = new OnlineTest(1);
        assertEquals(onlineTest.getNumberOfQuestions(),1);
        assertEquals(onlineTest.getNumberOfCategories(), 1);
    }

    @Test
    public void showNumberOfCategoriesInOneQuestion() {
        mockQuestion(1,"1");
        OnlineTest onlineTest = new OnlineTest(1);
        assertEquals(onlineTest.getNumberOfCategories(), 1);
    }

    @Test
    public void showNumberOfCategoriesInMultipleQuestions() {
        mockQuestion(3,"1");
        mockQuestion(3,"2");
        mockQuestion(4,"3");

        OnlineTest onlineTest = new OnlineTest(10);

        assertEquals(onlineTest.getNumberOfCategories(), 3);
    }

    @Test
    public void showNumberOfCategoriesInNoQuestion() {
        OnlineTest onlineTest = new OnlineTest(10);

        assertEquals(onlineTest.getNumberOfCategories(), 0);
    }

    private static void mockQuestion(int numberOfQuestion, String category) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", category));
    }

    private static void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index));
    }
}
