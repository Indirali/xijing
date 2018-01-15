package com.showtime.xijing.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 16:57
 **/
@Slf4j
public class DateUtil {

    /**
     * 将时间字符串转换为Date类型
     *
     * @param dateStr
     * @return Date
     */
    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 按照提供的格式将字符串转换成Date类型
     *
     * @param dateStr
     * @param formaterString
     * @return
     */
    public static Date toDate(String dateStr, String formaterString) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(formaterString);
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date类型时间转换为字符串
     *
     * @param date
     * @return
     */
    public static String toString(Date date) {
        String time;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy年MM月dd日 hh:mm:ss");
        time = formater.format(date);
        return time;
    }

    /**
     * 按照参数提供的格式将Date类型时间转换为字符串
     *
     * @param date
     * @param formaterString
     * @return
     */
    public static String toString(Date date, String formaterString) {
        String time;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(formaterString);
        time = formater.format(date);
        return time;
    }

    /**
     * method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     *
     * @param dateString 需要转换为timestamp的字符串
     * @return dataTime timestamp
     */
    public final static java.sql.Timestamp string2Time(String dateString)
            throws java.text.ParseException {
        DateFormat dateFormat;
//      dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS",
//              Locale.ENGLISH);// 设定格式
        dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);// util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }

    /**
     * method 将字符串类型的日期按照转换为一个timestamp（时间戳记java.sql.Timestamp）
     *
     * @param dateString     需要转换为timestamp的字符串
     * @param formaterString dateString字符串的解析格式
     * @return
     * @throws java.text.ParseException
     */
    public final static java.sql.Timestamp string2Time(String dateString,
                                                       String formaterString) throws java.text.ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat(formaterString);// 设定格式
        // dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);// util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }

    public static Date StartTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date EndTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

}
