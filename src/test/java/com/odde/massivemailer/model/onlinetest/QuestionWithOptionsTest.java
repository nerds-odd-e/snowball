package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.Question;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionWithOptionsTest {

    private Question createWithOptions(List<AnswerOption> answerOptions) {
        Question question = Question.createIt("description", "des1", "advice", "adv1");
        answerOptions.forEach(option -> option.addToQuestion(question.getLongId()));
        return question;
    }

    @Test
    public void shouldCreateQuestionHavingDescriptionAndAdviceAndAnswerOptions() {
        List<AnswerOption> expectedAnswerOptions = IntStream.range(0, 4).mapToObj(index -> AnswerOption.create("option desc"+index, index%4==0)).collect(Collectors.toList());
        Question expected  = createWithOptions(expectedAnswerOptions);

        Question actual = Question.getById(expected.getLongId());
        assertEquals(expected, actual);

        Collection<AnswerOption> actualAnswerOptions = actual.getOptions();

        assertEquals(expectedAnswerOptions.size(), actualAnswerOptions.size());
        assertTrue(expectedAnswerOptions.containsAll(actualAnswerOptions));
    }
}
