package com.odde.snowball.util;


import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

class DateUtil {
    
    private static final ZoneOffset OFFSET = OffsetDateTime.now().getOffset();

    public static Date asDate(LocalDate localDate) {
        return new Date(localDate.atStartOfDay().toInstant(OFFSET).toEpochMilli());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(OFFSET).toLocalDate();
    }
}
