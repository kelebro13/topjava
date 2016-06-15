package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenDate(LocalDate lt, LocalDate startDate, LocalDate endDate) {
        return lt.compareTo(startDate) >= 0 && lt.compareTo(endDate) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }

    public static LocalDateTime getDateTime(String date) {
        return LocalDateTime.parse(date);
    }

    public static LocalDate getDate(String date) {
        return LocalDate.parse(date);
    }

    public static LocalTime getTime(String date) {
        return LocalTime.parse(date);
    }
}
