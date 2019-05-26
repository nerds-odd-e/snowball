package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
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

    private Question createWithOptions(List<QuestionOption> questionOptions) {
        Question question = Question.createIt("description", "des1", "advice", "adv1");
        questionOptions.forEach(option -> option.addToQuestion(question.getStringId()));
        return question;
    }

    @Test
    public void shouldCreateQuestionHavingDescriptionAndAdviceAndAnswerOptions() {
        List<QuestionOption> expectedQuestionOptions = IntStream.range(0, 4).mapToObj(index -> QuestionOption.create("option desc"+index, index%4==0)).collect(Collectors.toList());
        Question expected  = createWithOptions(expectedQuestionOptions);

        Question actual = Question.getById(expected.getLongId());
        assertEquals(expected, actual);

        Collection<QuestionOption> actualQuestionOptions = actual.getOptions();

        assertEquals(expectedQuestionOptions.size(), actualQuestionOptions.size());
        assertTrue(expectedQuestionOptions.containsAll(actualQuestionOptions));
    }
}
