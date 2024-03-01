package com.booking.utils;

import java.time.*;

public  class  TimeMapper {
    public static Instant convertLocalTimeToInstant(LocalTime localTime) {

        ZoneId tashkentZoneId = ZoneId.of("Asia/Tashkent");

        LocalTime adjustedTime = localTime.plusHours(5);

        LocalDate currentDate = LocalDate.now(tashkentZoneId);

        ZoneOffset currentOffsetForTashkent = tashkentZoneId.getRules().getOffset(Instant.now());
        return currentDate.atTime(adjustedTime).toInstant(currentOffsetForTashkent);
    }
}
