package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(TestWithDB.class)
public class OnlineTestServiceTest {

    @Before
    public void before() {
        Question.deleteAll();
        newQuestion("body1", "advice1");
        newQuestion("body2", "advice2");
        newQuestion("body3", "advice3");
        newQuestion("body4", "advice4");
        newQuestion("body5", "advice5");
        newQuestion("body6", "advice6");
        newQuestion("body7", "advice7");
        newQuestion("body8", "advice8");
        newQuestion("body9", "advice9");
        newQuestion("body10", "advice10");

    }

    private void newQuestion(String body, String advice) {
        Question.create("body", body, "advice", advice).saveIt();
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

}