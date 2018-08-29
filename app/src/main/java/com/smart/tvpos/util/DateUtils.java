package com.smart.tvpos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public int getSecondCountFrom(int startHour, String pattern, String dateStr){

        int result = 0;

        if(null == pattern){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format =   new SimpleDateFormat(pattern);
        Date date;
        try {
            date = format.parse(dateStr);
            c.setTime(date);
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            int s = c.get(Calendar.SECOND);

            if(startHour > 12){
                if(h > 12){
                    result = (h - startHour) * 3600 + m * 60 + s;
                }
                else {
                    result = (h + 24 -startHour) * 3600 + m * 60 + s;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDateBefore(int before, String combine){
        String date;

        Calendar c = Calendar.getInstance();
        int dayBefore = c.get(Calendar.DAY_OF_MONTH) - before + 1;
        c.set(Calendar.DAY_OF_MONTH, dayBefore);
        date = c.get(Calendar.YEAR) + combine + (c.get(Calendar.MONTH) + 1) + combine + c.get(Calendar.DAY_OF_MONTH);

        return date;
    }

    public ArrayList<String> getTimeListBetween(int begin, int end){
        ArrayList<String> times = new ArrayList<>();

        if(begin > 12){
            while (begin < 24){
                times.add(String.valueOf(begin++));
            }
            begin = 0;
            while (begin <= end){
                times.add(String.valueOf(begin++));
            }
        }
        return times;
    }

    public ArrayList<String> getDatesBefore(int before, String combine){
        ArrayList<String> dates = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int dayBefore = c.get(Calendar.DAY_OF_MONTH) - before + 1;
        c.set(Calendar.DAY_OF_MONTH, dayBefore);

        for(int i = 1; i < before + 1; i++){
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            String data = month + combine + day;
            dates.add(data);

            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
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
