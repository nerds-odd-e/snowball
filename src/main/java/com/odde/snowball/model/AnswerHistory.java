package com.odde.snowball.model;

import com.odde.snowball.model.onlinetest.AnswerInfo;

import java.util.Calendar;
import java.util.Date;

public class AnswerHistory {

    public void recordAnsweredQuestion(User user, String questionId, Date lasAnsweredDate){
        Calendar calendar = Calendar.getInstance();

        // 次回表示予定日をセット
        calendar.setTime(lasAnsweredDate);
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date nextShowDate = calendar.getTime();

        user.addAnswerInfo(new AnswerInfo(questionId, lasAnsweredDate, 1, nextShowDate));
    }
}
