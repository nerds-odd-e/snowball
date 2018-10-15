package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class QuizTest {

    @Test
    public void shouldNotGetANewQuizWithNQuestionIdsIfEnoughQuestionsInDatabase() {
        Quiz newQuiz = new Quiz(5);
        assertEquals(0, newQuiz.getNumberOfQuestions());
    }

    @Test
    public void shouldGetNextQuestionWhenHaveMoreQuestionsAvailable() {
        mockQuestion(5);
        Quiz newQuiz = new Quiz(5);
        assertEquals(5, newQuiz.getNumberOfQuestions());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotGetNextQuestionWhenNoMoreQuestionsLeft() {
        Quiz newQuiz = new Quiz(5);
        newQuiz.getNextQuestion();
    }

    @Test
    public void shouldNotRepeatQuestions() {
        mockQuestion(6);
        Quiz newQuiz = new Quiz(5);
        Set<Question> questions = new HashSet<>();
        while(newQuiz.hasNextQuestion()) {
            questions.add(newQuiz.getNextQuestion());
        }
        assertEquals(5, questions.size());
    }

    private void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index));
    }
}
