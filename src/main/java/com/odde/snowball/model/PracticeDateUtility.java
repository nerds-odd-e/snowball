package com.odde.snowball.model;

import java.util.Calendar;
import java.util.Date;

public class PracticeDateUtility {

    public Date getNextShowDateOnCollected(Date today) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date nextShowDate = calendar.getTime();

        return nextShowDate;
    }
}
