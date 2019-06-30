package com.odde.snowball.util;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    @Test
    public void shouldConvertToUtilDate() {
        Date date = DateUtil.asDate(LocalDate.of(2018, 6, 20));

        assertThat(date)
                .hasYear(2018)
                .hasMonth(6)
                .hasDayOfMonth(20);

    }

    @Test
    public void shouldConvertToLocalDate() {
        LocalDate localDate = LocalDate.of(2018, 6, 20);
        Date date = DateUtil.asDate(localDate);

        assertThat(DateUtil.asLocalDate(date)).isEqualTo(localDate);

    }
}