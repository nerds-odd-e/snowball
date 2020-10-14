package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.PracticeDateUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class PracticeDateUtilityTest {

    private PracticeDateUtility utility;
    private Date today;
    private Calendar calendar;

    @Before
    public void init(){
        utility = new PracticeDateUtility();

        this.calendar = Calendar.getInstance();
        this.calendar.set(2020, Calendar.DECEMBER, 1);
        this.today = this.calendar.getTime();
    }

    @Test
    public void getNextShowDateOnCollected_日付を渡すと5日後の日付を返す() {

        Date nextShowDate = this.utility.getNextShowDateOnCollected(this.today);

        // 5日後の日付を求める
        this.calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date fiveDateAfter = this.calendar.getTime();
        // テスト
        assertEquals(fiveDateAfter, nextShowDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextShowDateOnCollected_日付にnullを渡すと例外を返す() {
        // 関数
        this.utility.getNextShowDateOnCollected(null);
    }
}
