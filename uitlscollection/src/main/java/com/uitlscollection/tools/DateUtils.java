package com.uitlscollection.tools;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author: palmer
 * time: 2017/11/1
 * email:lxlfpeng@163.com
 * desc:
 */

public class DateUtils {
    /**
     * 获取格林威治时间 即1970年至今
     * 的时间戳
     */
    public static long getCurrentTimeStamp() {
        int round = (int) (System.currentTimeMillis() / 1000);
        return round;
    }

    /**
     * 获取当前时间yyyy年MM月dd日  HH时mm分ss秒
     *
     * @param mcContext
     * @return
     */
    public static String getSystemCurrentTime(Context mcContext) {
        SimpleDateFormat sdf;
        if (DateFormat.is24HourFormat(mcContext)) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
        } else {
            sdf = new SimpleDateFormat("yyyy年MM月dd日  hh时mm分ss秒");
        }
        return sdf.format(new Date());
    }

    /**
     * 此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dateToTimeStamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public String dateToTimeStamp2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timeStampToDate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;
    }

    /**
     * 根据时间戳返回今天是周几
     *
     * @param timeStamp
     * @return
     */
    public static String getWeekFromTimeStamp(long timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = sdr.format(new Date(timeStamp * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 根据不同的类型获取当前时间的年月日时分秒
     */
    public static int getCurrentDateDetail(String value) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        switch (value) {
            case "year":
                return year;
            case "month":
                return month;
            case "day":
                return day;
            case "hour":
                return hour;
            case "minute":
                return minute;
            case "second":
                return second;
            default:
                return 0;
        }
    }

    /**
     * 输入毫秒的时间单位, 返回天, 小时, 分钟, 秒组成的数组形式.
     *
     * @param mss
     * @return
     */
    public static long[] formatMillisecond(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        long[] tiems = {days, hours, minutes, seconds};
        return tiems;
    }

    /**
     * 得到距离今天的某一天的日期(如传入-1则是昨天,传入1则是明天)
     *
     * @return
     */
    public static String getFromCurrentDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 将一个时间转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String getTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * @data 创建时间:2016/10/19
     * @author peng
     * @desc 传入两个2012-11-05 12:00:00类型的时间比较他们之间的时间长短
     * @version
     */
    public static long[] getTimeDifference(String currentTime, String startTime) {
        long[] arr = new long[]{};
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d2 = df.parse(currentTime); //前的时间
            Date d1 = df.parse(startTime);  //后的时间
            Long diff = d1.getTime() - d2.getTime();   //两时间差，精确到毫秒
            Long day = diff / (1000 * 60 * 60 * 24);          //以天数为单位取整
            Long hour = (diff / (60 * 60 * 1000) - day * 24);             //以小时为单位取整
            Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);    //以分钟为单位取整
            Long secone = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            arr = new long[]{day, hour, min, secone};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static String[] getDistanceTime(long startTime, long endTime) {
        startTime *= 1000;
        endTime *= 1000;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (startTime < endTime) {
            diff = endTime - startTime;
            flag = "前";
        } else {
            diff = startTime - endTime;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String[] strings = {flag, day + "", hour + "", min + "", sec + ""};
        return strings;
    }

}
