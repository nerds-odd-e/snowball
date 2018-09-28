package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionOption;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(TestWithDB.class)
public class OnlineTestServiceTest {

    @Before
    public void before() {
        Question.deleteAll();
        QuestionOption.deleteAll();
        IntStream.rangeClosed(0, 100).forEach(i -> {
            newQuestion("body"+i, "advice"+i, "Scrum");
        });
        newQuestion("body101", "advice101", "Technical practice");
        newQuestion("body102", "advice102", "Organization");
        newQuestion("body103", "advice103", "Scaling");
    }

    private void newQuestion(String body, String advice, String category) {
        Question.create("body", body, "advice", advice, "category", category).saveIt();
    }

    @Test
    public void generatedTestShouldHasRandomQuestions() {
        OnlineTest generate1 = new OnlineTestService().generate();
        OnlineTest generate2 = new OnlineTestService().generate();
        assertNotEquals(generate1, generate2);
        Assertions.assertNotEquals(
                generate1.getQuestions().toString(),
                generate2.getQuestions().toString()
        );
    }

    @Test
    public void generatedTestShouldHasFourCategories() {
        OnlineTest generate1 = new OnlineTestService().generate();
        Set<String> categories = generate1.getQuestions().stream()
                .map(Question::getCategory)
                .collect(Collectors.toSet());
        assertEquals(4, categories.size());
    }
}