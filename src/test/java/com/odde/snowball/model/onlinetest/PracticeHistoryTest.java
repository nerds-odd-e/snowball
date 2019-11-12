package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class PracticeHistoryTest {
    private Category scrum = Category.create("Scrum");
    private User user = new User("email@email.com");
    private Question question = new Question("desc", "adv", scrum.getId(), false, false, false, user).save();

    @Test
    public void shouldSave(){
       PracticeHistory practicehistory = new PracticeHistory(question,user);
       PracticeHistory result = practicehistory.save();
        assertSame(practicehistory, result);
    }
}
