package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.PracticeDateUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    // 関数の機能：正答時次回表示予定日を返す機能
    @Test
    public void 日付を渡すと5日後の日付を返す() {

        Date nextShowDate = this.utility.getNextShowDateOnCollected(this.today);

        // 5日後の日付を求める
        this.calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date fiveDateAfter = this.calendar.getTime();
        // テスト
        assertEquals(fiveDateAfter, nextShowDate);
    }

}
