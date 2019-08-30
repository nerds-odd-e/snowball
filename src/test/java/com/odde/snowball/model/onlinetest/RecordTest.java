package com.odde.snowball.model.onlinetest;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTest {

    @Test
    public void 同日に解答した問題は次回出題日が更新されないこと(){
        LocalDate expectedDate = LocalDate.now().plusDays(1);
        int expectedCycleState = 1;
        Record record = createAnsweredRecord(expectedDate, expectedCycleState);
        record.update(LocalDate.now());
        assertEquals(expectedCycleState, record.getCycleState());
        assertEquals(expectedDate, record.getNextShowDate());
    }

    private Record createAnsweredRecord(LocalDate nextShowDate, int cycleState) {
        Record record = new Record();
        record.setLastUpdated(LocalDate.now());
        record.setNextShowDate(nextShowDate);
        record.setCycleState(cycleState);
        return record;
    }
}
