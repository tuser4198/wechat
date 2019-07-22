package com.jack.wechat.utils;

import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Desciption:
 * @author: Chai jin qiu
 * @date: 2019/5/13
 */
public class LocalDateTimeUtil {

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(ZoneUtil.LOCAL_ZONE_ID);
    }

    /**
     * 默认时间格式转 LocalDateTime
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime parseTime(String timeStr) {
        Assert.notNull(timeStr, "时间字符串不能为null");
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * String 转时间
     *
     * @param timeStr
     * @param format  时间格式
     * @return
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat format) {
        Assert.notNull(timeStr, "时间字符串不能为null");
        Assert.notNull(format, "格式化模版不能为null");
        return LocalDateTime.parse(timeStr, format.formatter);
    }

    /**
     * 时间转 String
     *
     * @param time
     * @return
     */
    public static String parseTime(LocalDateTime time) {
        Assert.notNull(time, "参数不能为null");
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }

    /**
     * 时间转 String
     *
     * @param time
     * @param format 时间格式
     * @return
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        Assert.notNull(time, "时间不能为null");
        Assert.notNull(format, "格式化模版不能为null");
        return format.formatter.format(time);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String parseCurrentDatetime() {
        return DEFAULT_DATETIME_FORMATTER.format(getCurrentDateTime());
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式
     * @return
     */
    public static String parseCurrentDatetime(TimeFormat format) {
        Assert.notNull(format, "参数不能为null");
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * 获取某天的00:00:00
     *
     * @param dateTime
     * @return
     */
    public static String getDayStart(LocalDateTime dateTime) {
        Assert.notNull(dateTime, "参数不能为null");
        return DEFAULT_DATETIME_FORMATTER.format(dateTime.with(LocalTime.MIN));
    }

    /**
     * 获取今天的00:00:00
     *
     * @return
     */
    public static String getDayStart() {
        return getDayStart(getCurrentDateTime());
    }

    /**
     * 获取今天的23:59:59
     *
     * @return
     */
    public static String getDayEnd() {
        return getDayEnd(getCurrentDateTime());
    }

    /**
     * 获取某天的23：59：59
     *
     * @param dateTime
     * @return
     */
    public static String getDayEnd(LocalDateTime dateTime) {
        Assert.notNull(dateTime, "参数不能为null");
        return DEFAULT_DATETIME_FORMATTER.format(dateTime.with(LocalTime.MAX));
    }

    /**
     * @param dateTime
     * @return
     * @see LocalDateTime convert to timestamp
     */
    public static long getTimestamp(LocalDateTime dateTime) {
        Assert.notNull(dateTime, "参数不能为null");
        return Timestamp.valueOf(dateTime)
            .getTime();
    }

    /**
     * @param start
     * @param end
     * @return
     */
    public static long secondsBetween(LocalDateTime start, LocalDateTime end) {
        Assert.notNull(start, "开始时间不能为null");
        Assert.notNull(end, "结束时间不能为null");
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }

    /**
     * 自定义字符串格式转时间戳
     *
     * @param dateTime
     * @param timeFormat
     * @return
     */
    public static long getTimestamp(String dateTime, TimeFormat timeFormat) {
        Assert.notNull(dateTime, "时间不能为null");
        Assert.notNull(timeFormat, "格式化模版不能为null");
        return Timestamp.valueOf(parseTime(dateTime, timeFormat))
            .getTime();
    }

    /**
     * 默认格式字符串格式转时间戳
     *
     * @param dateTime
     * @return
     */
    public static long getTimestamp(String dateTime) {
        Assert.notNull(dateTime, "参数不能为null");
        return Timestamp.valueOf(parseTime(dateTime))
            .getTime();
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeByTimestamp(long timestamp) {
        Assert.notNull(timestamp, "参数不能为null");
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneUtil.LOCAL_ZONE_ID);
    }

    /**
     * 时间格式
     */
    public enum TimeFormat {

        /**
         * 短时间格式
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

        private transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}
