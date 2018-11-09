package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.javalite.activejdbc.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

@RunWith(TestWithDB.class)
public class QuestionTest {

    @Test
    public void shouldReturnAListOfIds() {
        Question.createIt("description", "desc1", "advice", "adv1");

        List<Object> allIds = Question.getNRandomIds(1).collect(Collectors.toList());

        assertThat(allIds, is(not(empty())));
        assertThat(allIds.size(), is(1));
    }

    @Test
    public void shouldReturnEmptyListIfNoQuestionsArePresent() {
        List<Object> allIds = Question.getNRandomIds(5).collect(Collectors.toList());
        assertThat(allIds, is(empty()));
    }

    @Test
    public void shouldGetQuestionById() {
        Question question1 = Question.createIt("description", "desc1", "advice", "adv1", "category", "scrum");
        Long id = question1.getLongId();
        Question actual = Question.getById(id);
        assertThat(actual, is(equalTo(question1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExxxceptionIfInvalidId() {
        Question question1 = Question.createIt("description", "desc1", "advice", "adv1");
        Long id = question1.getLongId();

        Question.getById(id + 10);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAllowEmptyDescription() {
        Question.createIt("description", null, "advice", "adv1");

    }

    @Test
    public void shouldAllowEmptyAdvice() {
        Question question1 = Question.createIt("description", "desc1", "advice", null);
        assertThat(question1.getLongId(), is(not(nullValue())));
        assertThat(question1.getAdvice(), isEmptyString());
    }

    @Test
    public void shouldFetchOptionsForQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", null);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        assertThat(question.getOptions(), is(not(empty())));
    }

    @Test
    public void shouldFetchOptionsForQuestionWithSameQuestionId() {
        Question question = Question.createIt("description", "desc1", "advice", null);
        Long expectedQuestionId = question.getLongId();
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);

        question.getOptions().forEach(option -> assertThat(option.getQuestionId(), is(equalTo(expectedQuestionId))));
    }

    @Test
    public void shouldIsMultipleChoiceQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        assertTrue(question.isMultipleAnswerOptions());
    }

    @Test
    public void shouldIsSingleChoiceQuestion() {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 1);
        AnswerOption.createIt("description", "desc", "question_id", question.getLongId(), "is_correct", 0);
        assertFalse(question.isMultipleAnswerOptions());
    }
}
