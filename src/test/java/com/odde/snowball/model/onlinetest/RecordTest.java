package com.odde.snowball.model.onlinetest;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTest {

    @Test
    public void 次回出題日以前に解答した問題は次回出題日が更新されないこと(){
        Record record = createAnsweredRecord();
        int expectedCycleState = record.getCycleState();
        LocalDate expectedDate = record.getNextShowDate();

        record.update(LocalDate.now());
        assertEquals(expectedCycleState, record.getCycleState());
        assertEquals(expectedDate, record.getNextShowDate());
    }

    private Record createAnsweredRecord() {
        Record record = new Record();
        record.update(LocalDate.now());
        return record;
    }

}
