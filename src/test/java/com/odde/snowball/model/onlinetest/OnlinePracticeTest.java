package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OnlinePracticeTest {

    //@Test
    public void 次回出題日が現在日である問題が存在する場合_その問題が取得できること() {
        User user = new User();
        List<Question> questions =
                OnlinePractice.findSpaceBasedRepetations(1, user, new Date());
        assertFalse(questions.isEmpty());
    }

}