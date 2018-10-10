package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionLogicTest {

    @Test
    public void shouldCreateQuestionHavingDescriptionAndAdviceAndAnswerOptions() {
        List<AnswerOption> expectedAnswerOptions = IntStream.range(0, 4).mapToObj(index -> AnswerOption.newInstance("option desc"+index, index%4==0)).collect(Collectors.toList());
        Question expected  = Question.createWithOptions("des1", "adv1", expectedAnswerOptions);

        Optional<Question> actualOptional  = Question.getById(expected.getLongId());

        assertTrue(actualOptional.isPresent());
        Question actual = actualOptional.get();
        assertEquals(expected, actual);

        Collection<AnswerOption> actualAnswerOptions = actual.getOptions();

        assertEquals(expectedAnswerOptions.size(), actualAnswerOptions.size());
        assertTrue(expectedAnswerOptions.containsAll(actualAnswerOptions));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateQuestionWithLessThanTwoAnswerOptions() {
        List<AnswerOption> expectedAnswerOptions = IntStream.range(0, 1).mapToObj(index -> AnswerOption.newInstance("option desc"+index, index%4==0)).collect(Collectors.toList());
        Question.createWithOptions("des1", "adv1", expectedAnswerOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateQuestionWithMultipleCorrectOptions() {
        List<AnswerOption> expectedAnswerOptions = IntStream.range(0, 4).mapToObj(index -> AnswerOption.newInstance("option desc"+index, index%2==0)).collect(Collectors.toList());
        Question.createWithOptions("des1", "adv1", expectedAnswerOptions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateQuestionWithoutCorrectAnswerOptions() {
        List<AnswerOption> expectedAnswerOptions = IntStream.range(0, 4).mapToObj(index -> AnswerOption.newInstance("option desc"+index, false)).collect(Collectors.toList());
        Question.createWithOptions("des1", "adv1", expectedAnswerOptions);
    }
}
