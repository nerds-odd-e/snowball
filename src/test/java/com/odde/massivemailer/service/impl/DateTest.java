package com.odde.massivemailer.service.impl;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by csd11 on 16/11/16.
 */
public class DateTest {

    @Test
    public void checkDate() {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "2016-11-16 07:16:11";
        try {
            System.out.println(dateFormat.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
