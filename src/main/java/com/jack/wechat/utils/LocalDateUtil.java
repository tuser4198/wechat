package com.jack.wechat.utils;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Period;

/**
 * @Desciption:
 * @author: Chai jin qiu
 * @date: 2019/5/13
 */
public class LocalDateUtil {

    public static LocalDate getCurrentDate() {
        return LocalDate.now(ZoneUtil.LOCAL_ZONE_ID);
    }

    /**
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(LocalDate start, LocalDate end) {
        Assert.notNull(start, "开始日期不能为null");
        Assert.notNull(end, "结束日期不能为null");
        Period period = Period.between(start, end);
        return period.getDays();
    }
}
