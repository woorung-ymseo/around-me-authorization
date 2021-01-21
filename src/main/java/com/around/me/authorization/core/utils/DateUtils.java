package com.around.me.authorization.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class DateUtils {
    public DateUtils() {
    }

    public static Date getDate(String currentTimeMillis) {
        return new Date();
    }

    /**
     * 현재 날짜시간 구하기
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentFormatDateTime("yyyyMMddHHmmss");
    }

    /**
     * 현재 날짜 구하기
     *
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentFormatDateTime("yyyyMMdd");
    }

    /**
     * 현재 시간 구하기
     *
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentFormatDateTime("HHmmss");
    }

    /**
     * 현재 날짜 구하기 (원하는 Format으로)
     *
     * @param format
     * @return
     */
    private static String getCurrentFormatDateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}
