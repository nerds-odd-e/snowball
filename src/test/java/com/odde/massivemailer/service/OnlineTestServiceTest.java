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
        Question.create("body", "body1", "advice", "advice1").saveIt();
        Question.create("body", "body2", "advice", "advice2").saveIt();
        Question.create("body", "body3", "advice", "advice3").saveIt();
        Question.create("body", "body4", "advice", "advice4").saveIt();
        Question.create("body", "body5", "advice", "advice5").saveIt();
        Question.create("body", "body6", "advice", "advice6").saveIt();
        Question.create("body", "body7", "advice", "advice7").saveIt();
        Question.create("body", "body8", "advice", "advice8").saveIt();
        Question.create("body", "body9", "advice", "advice9").saveIt();
        Question.create("body", "body10", "advice", "advice10").saveIt();

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