package com.yan.missyou.utils;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Argus
 * @className CommonUtils
 * @description: TODO
 * @date 2020/3/30 14:19
 * @Version V1.0
 */
public class CommonUtils {

    public static boolean isTimeIn(@NotNull Date now,@NotNull Date start,@NotNull Date end) {
        //是不包含的 > < 没有等于
        boolean before = start.before(now);
        boolean after = end.after(now);
        return before && after;
    }


    public static Calendar addSomeSeconds(Calendar calendar,int seconds) {
        calendar.add(Calendar.SECOND,seconds);
        return calendar;
    }
}