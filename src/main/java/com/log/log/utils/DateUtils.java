/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.log.log.utils;

import cn.hutool.core.util.StrUtil;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理
 *
 * @author liujiawan
 * @email sunlightcs@gmail.com
 * @date 2018年12月6日 2016年12月21日 下午12:53:33
 */
public class DateUtils {

    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     *  时间格式(yyyy-MM-dd HH:mm)
     */
    public final static String DATE_TIME_NOWFEN_PATTERN="yyyy-MM-dd HH:mm";

    /**
     *  时间格式( HH:mm)
     */
    public final static String DATE_TIME_PATTERN_H_M="HH:mm";

    /**
     * 一天的毫秒数
     */
    public static final long DAY_MILL = 1000*60*60*24L;

    /**
     * cron date 转化
     */
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ?";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StrUtil.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * 处理时分秒  会有多线程问题
     */
    public final static SimpleDateFormat SDF_DISPOSE = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat SDFALL_DISPOSE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取指定时间
     * 上一月
     * @param date
     * @return
     */
    public static Date getLastMonth(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 根据指定时间
     * 获取 年
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);

        return year;
    }
    /**
     * 获取 月
     * @param date
     * @return
     */
    public static int getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH) + 1;

        return month;
    }

    /**
     * 获取 天
     * @param date
     * @return
     */
    public static int getDayByMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }


    /**
     * 获取指定年月的
     * 第一天
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayByYearAndMonth(int year, int month) {

        try {
            Calendar cal = Calendar.getInstance();
            //设置年份
            cal.set(Calendar.YEAR, year);
            //设置月份
            cal.set(Calendar.MONTH, month-1);

            //获取某月最小天数
            int firstDay = cal.getMinimum(Calendar.DATE);
            //设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);

            Date thisfirstDay = cal.getTime();
            thisfirstDay = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisfirstDay));

            return thisfirstDay;
        }catch (Exception e) {
            return null;
        }

    }
    /**
     * 获取指定年月的
     * 最后一天
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayByYearAndMonth(int year, int month) {
        try {
            Calendar cal = Calendar.getInstance();
            //设置年份
            cal.set(Calendar.YEAR, year);
            //设置月份
            cal.set(Calendar.MONTH, month-1);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            //设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);

            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);

            Date thislastDay = cal.getTime();

            return thislastDay;

        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定 日期 的前几天/后几天 的 开始 00:00:00
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getDayBegin(Date date, int dayNum) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, dayNum);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定 日期 的前几天/后几天 的 开始 23:59:59
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getDayEnd(Date date, int dayNum) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, dayNum + 1);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期的前一天 开始, 从 00:00:00 开始
     * @param date
     * @return
     */
    public static Date getYesterdayBegin(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            calendarToZeroAll(cal);
            return cal.getTime();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取指定日期的前一天 结束, 到 23:59:59
     * @param date
     * @return
     */
    public static Date getYesterdayEnd(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取指定日期的当天 开始, 从 00:00:00 开始
     * @param date
     * @return
     */
    public static Date getNewBegin(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }
    /**
     * 获取指定日期的当天 结束, 到 23:59:59
     * @param date
     * @return
     */
    public static Date getNewEnd(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            cal.setTime(thisWeekMonday);
            cal.add(Calendar.SECOND, -1);
            thisWeekMonday = cal.getTime();

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 本周一 日期
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        try {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            // 获得当前日期是一个星期的第几天
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);

            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

            Date thisWeekMonday = cal.getTime();
            thisWeekMonday = SDF_DISPOSE.parse(SDF_DISPOSE.format(thisWeekMonday));

            return thisWeekMonday;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据周一 日期
     * 获取周日 日期
     * @param thisWeekMonday
     * @return
     */
    public static Date getThisWeekSunday(Date thisWeekMonday) {
        // 星期日
        Calendar cal = Calendar.getInstance();
        cal.setTime(thisWeekMonday);
        cal.add(Calendar.DATE, 7);
        cal.add(Calendar.SECOND, -1);
        Date thisWeekSunday = cal.getTime();
        return thisWeekSunday;
    }


    /**
     * 两个时间之间相差距离多少天
     * @param one 时间参数 1：
     * @param two 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(Date one, Date two){
        long days = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (diff / (1000 * 60 * 60 * 24));
        return days;
    }

    /**
     * 判断before是否end+hour的时间是否在之后，如果在返回true,否则返回false
     * @param before
     * @param end
     * @param hour
     * @return
     */
    public static boolean getTimeBjAfterHour(Date before,Date end,int hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        cal.add(Calendar.HOUR, hour);//
        end=cal.getTime();
        return before.after(end);
    }
    /**
     * 获取指定日期的前七天, 不包含时分秒
     * @param date
     * @return
     */
    public static List<String> getDistanceDays(Date date){
        List<String> list = new ArrayList<>();
        try {
            String yesterday01 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(date));
            String yesterday02 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday01)));
            String yesterday03 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday02)));
            String yesterday04 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday03)));
            String yesterday05 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday04)));
            String yesterday06 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday05)));
            String yesterday07 = DateUtils.SDF_DISPOSE.format(DateUtils.getYesterdayBegin(DateUtils.SDF_DISPOSE.parse(yesterday06)));

            list.add(yesterday01);
            list.add(yesterday02);
            list.add(yesterday03);
            list.add(yesterday04);
            list.add(yesterday05);
            list.add(yesterday06);
            list.add(yesterday07);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Date 得到 时间戳
     * @param date
     * @return
     */
    public static long dateToUnixTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateStr = sdf.format(date);
        long time = (sdf.parse(startDateStr, new ParsePosition(0))).getTime() / 1000;
        return time;
    }

    /**
     * String  得到 时间戳
     * @param dateStr
     * @return
     */
    public static long StrToUnixTime(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = (sdf.parse(dateStr, new ParsePosition(0))).getTime() / 1000;
        return time;
    }

    /**
     * 时间戳转为指定格式的时间字符串
     * @param timestamp 时间戳字符串
     * @return
     */
    public static String timestampToDateStr(Long timestamp, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        if(StringUtils.isNotEmpty(pattern)){
            dateFormat =new SimpleDateFormat(pattern);
        }
        if(timestamp.compareTo(2000000000L)==-1){
            //转成毫秒
            timestamp= timestamp*1000;
        }
        Date date = new Date(timestamp);
        String format =  format(date,DATE_TIME_PATTERN);
        return format;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }


    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    public static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // yyyy-MM-dd HH:mm:ss
        try {
            // 测试星期
            Date date = new Date();

            Date thisWeekMonday = getThisWeekMonday(date);
            Date thisWeekSunday = getThisWeekSunday(thisWeekMonday);

            //System.out.println("本周一" + SDFALL_DISPOSE.format(thisWeekMonday));
            //System.out.println("本周日" + SDFALL_DISPOSE.format(thisWeekSunday));

            // 测试月份
            Date firstDayMonth = getFirstDayByYearAndMonth(getYearByDate(date), getMonthByDate(date));
            //System.out.println("本月开始" + SDFALL_DISPOSE.format(firstDayMonth));
            Date lastDayMonth = getLastDayByYearAndMonth(getYearByDate(date), getMonthByDate(date));
            //System.out.println("本月结束" + SDFALL_DISPOSE.format(lastDayMonth));

            //System.out.println("------------------------------------------------------------");
            Date dayBegin = getDayBegin(new Date(), -1);
            //System.out.println(dayBegin);
            //System.out.println(SDFALL_DISPOSE.format(dayBegin));
            Date dayEnd = getDayEnd(new Date(), -1);
            //System.out.println(dayEnd);
            //System.out.println(SDFALL_DISPOSE.format(dayEnd));

            Map<String, Object> allDataMap = new HashMap<>(16);
            allDataMap.put("beginTime", dayBegin);

            //System.out.println(allDataMap.get("beginTime"));
            //System.out.println(SDF_DISPOSE.format(allDataMap.get("beginTime")));
            //System.out.println("2019-07-20".length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得某天的最后时刻格式天数的最后一秒
     * @param date
     * @return
     */
    public static Date getLastSecondDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendarToZeroAll(calendar);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MILLISECOND,-1);
        return calendar.getTime();
    }

    /**
     * 获取前几天的日期
     * @param date 指定时间
     * @param days 几天前
     * @return
     */
    public static Date getDateBeforeDay(Date date, int days) {
        long time = date.getTime();
        long daysTime = days*DAY_MILL;
        long before = time - daysTime;
        return new Date(before);
    }

    /**
     * 获取前几周的 周最后一天的时间
     * @param i 几周
     * @param date 指定时间
     * @return
     */
    public static Date getDatesOfLastWeek(int i, Date date) {
        int days = i*7;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH,-days+7);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return calendar.getTime();
    }

    /**
     * 获取上个月这天的开始时间
     * @param date
     * @return
     */
    public static Date getLastMonthEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarToZeroAll(calendar);
        return calendar.getTime();
    }

    /**
     * 获取时间在当月的是第几天  如12号 是当月12天
     * @param time
     * @return
     */
    public static int getDayByDate(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是否都是相同星期  比如都是星期一
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int week1 = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(date2);
        int week2 = calendar.get(Calendar.DAY_OF_WEEK);
        return week1 == week2;
    }

    /**
     * 获取几天前的一天开始和结束时间 （开始时间开合，结束时间闭合）
     * @param now
     * @param billTimeDay
     * @return
     */
    public static Date getDatesBeforeDays(Date now, int billTimeDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        calendarToZeroAll(calendar);
        calendar.add(Calendar.DAY_OF_MONTH,-billTimeDay);

        return calendar.getTime();
    }

    /**
     * 获取多少天前的30天的时间段
     * @param now
     * @param billTimeDay
     * @return
     */
    public static Date getThirtyDates(Date now, int billTimeDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendarToZeroAll(calendar);
        //获取结算数据的最后一天 包含当天
        calendar.add(Calendar.DAY_OF_MONTH, -billTimeDay);
        return calendar.getTime();
    }


    /**
     * 获取一天最后一毫秒结束时间
     * @param startTime
     * @return
     */
    public static Date getEndDayTime(Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendarToZeroAll(calendar);
        calendar.add(Calendar.MILLISECOND,-1);
        return calendar.getTime();
    }

    /**
     * 获取2个时间段之间天数 不足一天舍掉
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDaysBetweenTime(Date startTime, Date endTime) {
        if (startTime.compareTo(endTime) > 0){
            return 0;
        }
        long start = startTime.getTime();
        long end = endTime.getTime();
        long dif = end - start;
        return (int)(dif/DAY_MILL);
    }

    /**
     * 获取某天天的开始时间
     * @param date
     * @param i 天数 正代表往后  负数代表往前
     * @return
     */
    public static Date getDateBegin(Date date,int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,i);
        calendarToZeroAll(calendar);
        return calendar.getTime();
    }

    private static void calendarToZeroAll(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    /***
     *
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     *
     * @param cron Quartz cron的类型的日期
     * @return Date日期
     */
    public static Date getDate(final String cron) {


        if (cron == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }
}
