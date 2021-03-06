package com.github.graschenko.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static final String DATE_TIME_PATTERN = "HH:mm";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private LocalTime timeLimit = LocalTime.of(11, 0);

    public static LocalTime getTimeLimit() {
        return timeLimit;
    }

    public static void setTimeLimit(LocalTime timeLimit) {
        DateTimeUtil.timeLimit = timeLimit;
    }

    public static String toString(LocalTime localTime) {
        return localTime == null ? "null" : localTime.format(DATE_TIME_FORMATTER);
    }
}
