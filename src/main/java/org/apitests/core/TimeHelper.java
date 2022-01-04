package org.apitests.core;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_INSTANT;

    public static String getNextWorkingDay() {
        return getNextWorkingDayAfterDays(1);
    }

    public static String getNextWorkingDayAfterDays(int days) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DATE, 2);
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, 1);
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss z uuuu");
        ZonedDateTime zdt = ZonedDateTime.parse(cal.getTime().toString(), f);
        return dateFormatter.format(zdt);
    }

}
