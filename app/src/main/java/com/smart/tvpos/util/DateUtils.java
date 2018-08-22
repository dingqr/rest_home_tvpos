package com.smart.tvpos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static DateUtils dateUtils;

    public static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    int WEEKDAYS = 7;

    private DateUtils(){}

    public static DateUtils getInstance(){
        if (null == dateUtils){
            dateUtils = new DateUtils();
        }
        return dateUtils;
    }
    public String getMonthDay(String src, String pattern, String combine){

        String result = null;
        if(null == src){
            return result;
        }

        if(null == pattern){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat format =   new SimpleDateFormat(pattern);
        Date date;
        try {
            date = format.parse(src);
            SimpleDateFormat sdf1 = new SimpleDateFormat("M");//月份的
            SimpleDateFormat sdf2 = new SimpleDateFormat("d");//日的
            String month = sdf1.format(date);//取出特定日期d1的月份
            String day = sdf2.format(date);//取出特定日期d1的日
            result = month + combine + day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDateWeekBegin(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String dateBegin = dateFormat.format(c.getTime());

        return dateBegin;
    }

    public String getDateWeekEnd() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String dateEnd = dateFormat.format(c.getTime());
        return dateEnd;
    }

    public String toWeekDay(String src, String pattern){
        String result = null;
        if(null == src){
            return result;
        }

        if(null == pattern){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format =   new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        result = WEEK[dayIndex - 1];
        return result;
    }

    public int toWeekDayIndex(String src, String pattern){
        if(null == src){
            return -1;
        }

        if(null == pattern){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format =   new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return -1;
        }

        return dayIndex;
    }

    public static long toTimeStamp(String time, String pattern) {
        long dTime = 0;
        if (time != null) {
            if (pattern == null)
                pattern = "yyyy-MM-dd HH:mm:ss";
            Date tDate;
            try {
                tDate = new SimpleDateFormat(pattern).parse(time);
                if (tDate != null) {
                    dTime = tDate.getTime() / 1000;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dTime;
    }

}
