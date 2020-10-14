package com.odde.snowball.model;

import java.util.Calendar;
import java.util.Date;

public class PracticeDateUtility {

    public Date getNextShowDateOnCollected(Date today) {

        if(today == null){
            throw new IllegalArgumentException("null is not allowed for the argument 'today'.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date nextShowDate = calendar.getTime();

        return nextShowDate;
    }
}
