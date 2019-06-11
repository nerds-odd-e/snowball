package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionWithOptionsTest {

    private Question createWithOptions(List<QuestionOption> questionOptions) {
        Question question = new Question("des1", "adv1", new ObjectId(), false, false).save();
        questionOptions.forEach(option -> option.addToQuestion(question.getId()));
        return question;
    }

    @Test
    public void shouldCreateQuestionHavingDescriptionAndAdviceAndAnswerOptions() {
        List<QuestionOption> expectedQuestionOptions = IntStream.range(0, 4).mapToObj(index -> new QuestionOption("option desc"+index, index%4==0, null)).collect(Collectors.toList());
        Question expected  = createWithOptions(expectedQuestionOptions);

        Question actual = repo(Question.class).findById(expected.getId());
        assertEquals(expected, actual);

        Collection<QuestionOption> actualQuestionOptions = actual.options();

        assertEquals(expectedQuestionOptions.size(), actualQuestionOptions.size());
        assertTrue(expectedQuestionOptions.containsAll(actualQuestionOptions));
    }
}
