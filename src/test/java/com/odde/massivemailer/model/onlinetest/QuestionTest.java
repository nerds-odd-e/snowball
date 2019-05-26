package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.base.ValidationException;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

@RunWith(TestWithDB.class)
public class QuestionTest {
    private final ObjectId categoryId = new ObjectId();

    @Test
    public void shouldGetQuestionById() {
        Question question1 = new Question("desc1", "adv1", categoryId, false, false).saveIt();
        ObjectId id = question1.getId();
        Question actual = Question.getById(id);
        assertThat(actual, is(equalTo(question1)));
    }

    @Test
    public void getCorrectOption_正解のIDの一覧を返す() {
        Question question = new Question("desc1", "adv1", categoryId, false, false).saveIt();
        QuestionOption correct1 = QuestionOption.createIt(question.getId(), "desc", true);
        QuestionOption correct2 = QuestionOption.createIt(question.getId(), "desc", true);
        QuestionOption.createIt(question.getId(), "desc", false);

        final ArrayList<ObjectId> actual = question.getCorrectOption();
        Assertions.assertThat(actual).hasSize(2).contains(correct1.getId()).contains(correct2.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExxxceptionIfInvalidId() {
        Question.getById(new ObjectId());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAllowEmptyDescription() {
        new Question(null, "adv1", categoryId, false, false).saveIt();

    }

    @Test
    public void shouldAllowEmptyAdvice() {
        Question question1 = new Question("desc1", null, categoryId, false, false).saveIt();
        assertThat(question1.getId(), is(not(nullValue())));
    }

    @Test
    public void shouldFetchOptionsForQuestion() {
        Question question = new Question("desc1", null, categoryId, false, false).saveIt();
        QuestionOption.createIt(question.getId(), "desc", false);
        assertThat(question.getOptions(), is(not(empty())));
    }

    @Test
    public void shouldFetchOptionsForQuestionWithSameQuestionId() {
        Question question = new Question("desc1", null, categoryId, false, false).saveIt();
        ObjectId expectedQuestionId = question.getId();
        QuestionOption.createIt(question.getId(), "desc", false);
        QuestionOption.createIt(question.getId(), "desc", false);
        QuestionOption.createIt(question.getId(), "desc", false);

        question.getOptions().forEach(option -> assertThat(option.getQuestionId(), is(equalTo(expectedQuestionId))));
    }

    @Test
    public void TypeがsingleのときにgetIsMultiQuestionが0を返す() {
        final Question question = new Question("description", "advice", categoryId, false, false);
        final boolean actual = question.isMultiQuestion();
        assertEquals(actual, false);
    }

    @Test
    public void TypeがMultiのときにgetIsMultiQuestionが1を返す() {
        final Question question = new Question("description", "advice", categoryId, true, false);
        final boolean actual = question.isMultiQuestion();
        assertEquals(actual, true);
    }

    @Test
    public void shouldReturnEmptyListByGetAll() {
        List<Question> questions = Question.repository().findAll();

        assertEquals(questions.size(), 0);
    }

    @Test
    public void shouldReturnTwoElementByGetAll() {
        new Question("desc", "adv", categoryId, false, false).saveIt();
        new Question("desc", "adv", categoryId, false, false).saveIt();
        List<Question> questions = Question.repository().findAll();
        assertEquals(questions.size(), 2);
    }
}
