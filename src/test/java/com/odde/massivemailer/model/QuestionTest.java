package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.javalite.activejdbc.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;

@RunWith(TestWithDB.class)
public class QuestionTest {

    @Test
    public void shouldReturnAListOfIds() {
        //given
        Question.createIt("description","desc1","is_multi_question", "0", "advice","adv1");
        //when
        List<Object> allIds = Question.getAllIds().collect(Collectors.toList());
        //then
        assertThat(allIds, is(not(empty())));
        assertThat(allIds.size(), is(1));
    }

    @Test
    public void shouldReturnEmptyListIfNoQuestionsArePresent() {
        //when
        List<Object> allIds = Question.getAllIds().collect(Collectors.toList());
        //then
        assertThat(allIds, is(empty()));
    }

    @Test
    public void shouldGetQuestionById() {
        Question question1 = Question.createIt("description","desc1","is_multi_question", "0", "advice","adv1");
        Long id = question1.getLongId();

        Optional<Question> question = Question.getById(id);

        Assert.assertTrue("Saved question is not retrieved from DB by id.", question.isPresent());
        Question actual = question.get();
        assertThat(actual, is(equalTo(question1)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfInvalidId() {
        Question question1 = Question.createIt("description","desc1","is_multi_question", "0", "advice","adv1");
        Long id = question1.getLongId();

        Optional<Question> question = Question.getById(id+10);

        Assert.assertFalse(question.isPresent());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAllowEmptyDescription() {
        Question.createIt("description", null, "is_multi_question", "0", "advice","adv1");

    }

    @Test
    public void shouldAllowEmptyAdvice() {
        Question question1 = Question.createIt("description","desc1","is_multi_question", "0", "advice", null);
        assertThat(question1.getLongId(), is(not(nullValue())));
        assertThat(question1.getAdvice(), isEmptyString());
    }

    @Test
    public void shouldFetchOptionsForQuestion() {
        Question question = Question.createIt("description","desc1","is_multi_question", "0", "advice", null);
        assertThat(question.getOptions(), is(not(empty())));
    }

    @Test
    public void shouldFetchOptionsForQuestionWithSameQuestionId() {
        Question question = Question.createIt("description","desc1","is_multi_question", "0", "advice", null);
        Long expectedQuestionId = question.getLongId();
        //TODO persist options
        question.getOptions().forEach(option -> assertThat(option.getQuestionId(), is(equalTo(expectedQuestionId))));
    }
}
