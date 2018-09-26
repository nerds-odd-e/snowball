package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(TestWithDB.class)
class Question2Test {

    @Test
    public void xxx() {
        Question2 q = new Question2();
        q.saveIt();
    }

}