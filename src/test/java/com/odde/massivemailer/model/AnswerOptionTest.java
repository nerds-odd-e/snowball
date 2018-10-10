package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.javalite.activejdbc.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class AnswerOptionTest {

    @Test
    public void shouldBeAbleToPersist() {
        AnswerOption answerOption = AnswerOption.create("desc", false);
        answerOption.addToQuestion(1L);

        Optional<AnswerOption> actual = AnswerOption.getById(answerOption.getLongId());

        assertTrue(actual.isPresent());
        assertThat(actual.get(), is(equalTo(answerOption)));
    }

    @Test
    public void shouldSaveAndReadIsCorrectProperly() {
        AnswerOption wrongOption = AnswerOption.create("desc1", false);
        AnswerOption rightOption = AnswerOption.create("desc2", true);
        wrongOption.addToQuestion(1L);
        rightOption.addToQuestion(1L);

        Optional<AnswerOption> wrongFromDb = AnswerOption.getById(wrongOption.getLongId());
        assertTrue(wrongFromDb.isPresent());
        AnswerOption wrong = wrongFromDb.get();
        assertThat(wrong.isCorrect(), is(false));

        Optional<AnswerOption> rightFromDb = AnswerOption.getById(rightOption.getLongId());
        assertTrue(rightFromDb.isPresent());
        AnswerOption right = rightFromDb.get();
        assertThat(right.isCorrect(), is(true));
    }

    @Test
    public void shouldReturnOptionsForQuestion() {
        IntStream.range(0, 3).forEach(i -> {
            AnswerOption answerOption = AnswerOption.create("desc", false);
            answerOption.addToQuestion(i%2 ==0 ? 1L : 2L);
        });

        Collection<AnswerOption> optionsForQuestion = AnswerOption.getForQuestion(1L);
        assertThat(optionsForQuestion.size(), is(2));
        optionsForQuestion.forEach(option -> assertEquals(option.getQuestionId(), Long.valueOf(1)));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWhenDescriptionIsEmpty() {
        AnswerOption.createIt("description","","question_id",1, "is_correct", 0);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWhenQuestionIdIsEmpty() {
        AnswerOption.createIt("description","desc","question_id", null, "is_correct", 0);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWhenIsCorrectIsNotDefined() {
        AnswerOption.createIt("description","desc","question_id",1, "is_correct", null);
    }
}
