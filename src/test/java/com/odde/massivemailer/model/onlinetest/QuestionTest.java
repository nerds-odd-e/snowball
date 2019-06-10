package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.base.ValidationException;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;
import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class QuestionTest {
    private final ObjectId categoryId = new ObjectId();

    @Test
    public void shouldGetQuestionById() {
        Question question1 = new Question("desc1", "adv1", categoryId, false, false).save();
        ObjectId id = question1.getId();
        Question actual = repo(Question.class).findById(id);
        assertThat(actual, is(equalTo(question1)));
    }

    @Test
    public void getCorrectOption_正解のIDの一覧を返す() {
        Question question = new Question("desc1", "adv1", categoryId, false, false).save();
        QuestionOption correct1 = QuestionOption.createIt(question.getId(), "desc", true);
        QuestionOption correct2 = QuestionOption.createIt(question.getId(), "desc", true);
        QuestionOption.createIt(question.getId(), "desc", false);

        final ArrayList<ObjectId> actual = question.correctOptions();
        Assertions.assertThat(actual).hasSize(2).contains(correct1.getId()).contains(correct2.getId());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAllowEmptyDescription() {
        new Question(null, "adv1", categoryId, false, false).save();

    }

    @Test
    public void shouldAllowEmptyAdvice() {
        Question question1 = new Question("desc1", null, categoryId, false, false).save();
        assertThat(question1.getId(), is(not(nullValue())));
    }

    @Test
    public void shouldFetchOptionsForQuestion() {
        Question question = new Question("desc1", null, categoryId, false, false).save();
        QuestionOption.createIt(question.getId(), "desc", false);
        assertThat(question.options(), is(not(empty())));
    }

    @Test
    public void shouldFetchOptionsForQuestionWithSameQuestionId() {
        Question question = new Question("desc1", null, categoryId, false, false).save();
        ObjectId expectedQuestionId = question.getId();
        QuestionOption.createIt(question.getId(), "desc", false);
        QuestionOption.createIt(question.getId(), "desc", false);
        QuestionOption.createIt(question.getId(), "desc", false);

        question.options().forEach(option -> assertThat(option.getQuestionId(), is(equalTo(expectedQuestionId))));
    }

    @Test
    public void TypeがsingleのときにgetIsMultiQuestionが0を返す() {
        final Question question = new Question("description", "advice", categoryId, false, false);
        final boolean actual = question.isMultiQuestion();
        assertFalse(actual);
    }

    @Test
    public void TypeがMultiのときにgetIsMultiQuestionが1を返す() {
        final Question question = new Question("description", "advice", categoryId, true, false);
        final boolean actual = question.isMultiQuestion();
        assertTrue(actual);
    }

    @Test
    public void shouldReturnEmptyListByGetAll() {
        List<Question> questions = repo(Question.class).findAll();

        assertEquals(questions.size(), 0);
    }

    @Test
    public void shouldReturnTwoElementByGetAll() {
        new Question("desc", "adv", categoryId, false, false).save();
        new Question("desc", "adv", categoryId, false, false).save();
        List<Question> questions = repo(Question.class).findAll();
        assertEquals(questions.size(), 2);
    }
}
