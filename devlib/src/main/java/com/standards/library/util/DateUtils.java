package com.standards.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;


import com.standards.library.app.AppContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.standards.library.R;
public class DateUtils {

    private final static String TAG = "DateUtils";

    public static String formatTimeForEvent(String time, Context context) {
        String time_show = "";
        try {
            String current_day = formatDateString("yyyy-MM-dd", System.currentTimeMillis());
            String time_day = formatDateString("yyyy-MM-dd", Long.parseLong(time) * 1000);
            long dur_day = getDaysFromTwoDate(time_day, current_day);
            if (dur_day == 0) {
                time_show = context.getResources().getString(R.string.today);
            } else if (dur_day == 1) {
                time_show = context.getResources().getString(R.string.yesterday);
            } else {
                String current_year = current_day.substring(0, 3);
                String time_year = time_day.substring(0, 3);
                LogUtil.d(TAG, "current_year= " + current_year + "  time_year = " + time_year);
                if (current_year.equals(time_year)) {
                    time_show = formatDateString("MM-dd", Long.parseLong(time) * 1000);
                    String[] time_show_spit = time_show.split("-");
                    time_show = time_show_spit[1] + "\n" + changeMonthToString(context, time_show_spit[0]);
                } else {
                    time_show = formatDateString("yyyy-MM-dd", Long.parseLong(time) * 1000);
                }
            }
            LogUtil.d(TAG, "time_show = " + time_show);
        } catch (NumberFormatException e) {

        }

        return time_show;
    }

    private static String changeMonthToString(Context context, String month) {
        String[] months = context.getResources().getStringArray(R.array.months);
        int month_int = Integer.parseInt(month);
        LogUtil.d(TAG, "month_int = " + month_int);
        return months[month_int - 1];
    }

    /**
     * 取现在的月日时间串
     *
     * @return
     */
    public static String getTodayString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取传入格式的显示时间
     *
     * @param format
     * @param time
     * @return
     */
    public static String formatDateString(String format, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    /**
     * @param time_unix
     * @return
     * @param格式化 yyyy-MM-dd
     */
    public static String formatToYMD(String time_unix) {
        try {
            long time = (Long.parseLong(time_unix.trim()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 根据时间（yyyy/MM） 来获取linux时间戳
     *
     * @param year
     * @param month
     * @return
     */
    public static String getTimeByString(String year, String month) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        Date date = new Date();
        try {
            date = simpleDate.parse(year + "/" + month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime() / 1000) + "";
    }

    /**
     * 根据时间（yyyy/MM） 来获取linux时间戳
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getTimeByString(String year, String month, String day) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try {
            date = simpleDate.parse(year + "/" + month + "/" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime() / 1000) + "";
    }

    //根据格式化的时间转换格式化
    public static String getTimestampByFormat(String beforeFormat, String afterFormat, String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(beforeFormat);
        try {
            Date date = simpleDateFormat.parse(time);
            return formatDateString(afterFormat, date.getTime());
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 格式化传入时间戳到年
     *
     * @param time
     * @return
     */
    public static String formatTimeYear(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date(time));
    }

    /**
     * 格式化传入时间戳到日
     *
     * @param time
     * @return
     */

    public static String formatDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = "";
        try {
            day = sdf.format(new Date(Long.valueOf(time)));
        } catch (Exception e) {
        }
        return day;
    }

    /**
     * 格式化传入时间戳到月
     *
     * @param time
     * @return
     */
    public static String formatMonth(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String cMonth = "";
        try {
            String month = sdf.format(new Date(Long.valueOf(time)));
            LogUtil.d(DateUtils.class.getSimpleName(), month + "");
            if (month.equals("01")) {
                cMonth = AppContext.getContext().getString(R.string.January);
            } else if (month.equals("02")) {
                cMonth = AppContext.getContext().getString(R.string.February);
            } else if (month.equals("03")) {
                cMonth = AppContext.getContext().getString(R.string.March);
            } else if (month.equals("04")) {
                cMonth = AppContext.getContext().getString(R.string.April);
            } else if (month.equals("05")) {
                cMonth = AppContext.getContext().getString(R.string.May);
            } else if (month.equals("06")) {
                cMonth = AppContext.getContext().getString(R.string.June);
            } else if (month.equals("07")) {
                cMonth = AppContext.getContext().getString(R.string.July);
            } else if (month.equals("08")) {
                cMonth = AppContext.getContext().getString(R.string.August);
            } else if (month.equals("09")) {
                cMonth = AppContext.getContext().getString(R.string.September);
            } else if (month.equals("10")) {
                cMonth = AppContext.getContext().getString(R.string.October);
            } else if (month.equals("11")) {
                cMonth = AppContext.getContext().getString(R.string.November);
            } else {
                cMonth = AppContext.getContext().getString(R.string.December);
            }

        } catch (Exception e) {
        }

        return cMonth;
    }


    public static boolean isMuchHour(String now, String before) {
        try {
            long nowTime = Long.valueOf(now);
            long beforeTime = Long.valueOf(before);

            long dur = nowTime - beforeTime;
            long hour = dur / (60 * 60 * 1000);
            if (hour >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }

    }

    /**
     * 获取某年某月天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayNumByYearMonth(String year, String month) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(simpleDate.parse(year + "/" + month));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数
    }

    public static boolean dateBeforeAndDateAfter(String[] beforeAfter, String compareDate) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date dateAfter = df.parse(beforeAfter[0]);
            Date dateBefor = df.parse(beforeAfter[1]);
            Date dateCompare = df.parse(compareDate);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            Calendar c3 = Calendar.getInstance();
            c1.setTime(dateAfter);
            c2.setTime(dateBefor);
            c3.setTime(dateCompare);
            if (c3.before(c2) && c3.after(c1)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 格式化传入时间戳到月
     *
     * @param time
     * @return
     */
    public static String formatTimeMMSS(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(Long.valueOf(time)));
    }

    /**
     * 格式化传入时间戳到月
     *
     * @param time
     * @return
     */
    public static String formatTimeMonth(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(new Date(time));
    }

    /**
     * 格式化传入时间戳到日
     *
     * @param time
     * @return
     */
    public static String formatTimeDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(new Date(time));
    }

    /**
     * 是否超过四分之一天
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static boolean hasDuringQuarterDay(long start_time, long end_time) {
        long during = end_time - start_time;
        long day = (during / (60 * 60 * 6 * 1000));
        if (day > 1) {
            return true;
        }
        return false;
    }

    /**
     * 是否超过一天
     *
     * @param start_time 毫秒
     * @param end_time   毫秒
     * @return
     */
    public static boolean hasDuringOneDay(long start_time, long end_time) {
        long during = end_time - start_time;
        long day = (during / (60 * 60 * 24 * 1000));
        if (day > 1) {
            return true;
        }
        return false;
    }

    /**
     * 格式化服务器时间 2011-11-11 11:11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeYMD(long time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
        Date date = new Date(time * 1000);
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 格式化服务器时间 2011-11-11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeYMDOnly(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 格式化当前手机获取时间 2011-11-11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateLocalTimeYMDOnly(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim())));
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 格式化当前手机获取时间 2011-11-11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateLocalTimeYMDOnly(long time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
        Date date = new Date(time);
        return dateFm.format(date);
    }

    /**
     * 格式化当前手机获取时间 2011-11-11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateLocalTimeYMDOnly(Date time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
        return dateFm.format(time);
    }

    /**
     * 格式化服务器时间 11-11 11:11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeMD(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd HH:mm"); // 格式化当前系统日期
        String dateTime = "";
        try {
            Date date = new Date((Long.parseLong(time.trim())));
            dateTime = dateFm.format(date);
        } catch (Exception e) {

        }


        return dateTime;
    }

    /**
     * 格式化服务器时间 11-11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeMDOnly(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 格式化服务器时间 11月11日
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeMDOnly1(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM月dd日"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 获取当前时间几点
     *
     * @return
     */
    public static int getNowDay() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;

    }

    /**
     * 取现在的月日时间串
     *
     * @return
     */
    public static String formateTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = sdf.format(date);
        return dateTime;
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param current_time
     * @param time
     * @return
     */
    public static boolean isSameDay(long current_time, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date_current = new Date(current_time);
        String current_string = sdf.format(date_current);
        Date date_time = new Date(time);
        String time_string = sdf.format(date_time);
        LogUtil.d(TAG, "current_string = " + current_string + " time_string = " + time_string);
        return current_string.equals(time_string);
    }

    /**
     * 取昨日的月日时间串
     *
     * @return
     */
    public static String getYesterdayString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(System.currentTimeMillis()
                - (60 * 1000 * 60 * 24)));
    }

    /**
     * 获取时间差
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static long getDuringTimeLong(String start_time, String end_time) {
        long start = Long.parseLong(start_time);
        long end = Long.parseLong(end_time);
        long during = end - start;
        return during;
    }

    /**
     * 计算时间差
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static String getDuringTime(String start_time, String end_time) {
        long during = getDuringTimeLong(start_time, end_time);
        long day = (during / (60 * 60 * 24));
        long hour = (during - (60 * 60 * 24 * day)) / (60 * 60);
        String during_ = day + "天" + hour + "小时";
        return during_;
    }

    /**
     * 计算时间差
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static String getDuringTimeHHMM(String start_time, String end_time) {
        long during = getDuringTimeLong(start_time, end_time) * 1000;
        long day = during / (24 * 60 * 60 * 1000);
        long hour = (during / (60 * 60 * 1000) - day * 24);
        long min = ((during / (60 * 1000)) - day * 24 * 60 - hour * 60);
        String during_ = hour + "时" + min + "分";
        return during_;
    }

    /**
     * 计算时间相差天数
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static long getDuringDay(long start_time, long end_time) {
        long dur_day = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date_start = sdf.parse(formatDateString("yyyyMMdd", start_time));
            Date date_end = sdf.parse(formatDateString("yyyyMMdd", end_time));
            dur_day = (date_end.getTime() - date_start.getTime()) / (1000 * 60 * 60 * 24);
            LogUtil.d(TAG, "getDuringDay end_time = " + date_end.getTime() + " start_time= " + date_start.getTime());
            LogUtil.d(TAG, "getDuringDay end_time = " + formatDateString("yyyy-MM-dd", date_end.getTime()) + " start_time= " + formatDateString("yyyy-MM-dd", date_start.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dur_day;
    }

    /**
     * 计算时间相差天数(通过日期日期计算)
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static long getDuringDayFromDate(long start_time, long end_time) {
        long start = start_time / (1000 * 60 * 60 * 24);
        long end = (end_time + 1000000) / (1000 * 60 * 60 * 24);
        LogUtil.d(TAG, "getDuringDayFromDate start_time= " + start_time + " end_time = " + end_time);
        LogUtil.d(TAG, "getDuringDayFromDate end_time = " + end + " " + formatDateString("yyyy-MM-dd", end_time) + " start_time= " + start + " " + formatDateString("yyyy-MM-dd", start_time));
        long dur_day = (end_time - start_time) / (1000 * 60 * 60 * 24);
        return dur_day;
    }

    /**
     * 计算前几日的日期
     *
     * @param time
     * @param n
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateBefore(String time, int n) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - n);
        String dateTime = dateFm.format(now.getTime());
        return dateTime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isToday(String sdate) {
        boolean b = false;
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        if (sdate != null) {
            String nowDate = dateFm.format(today);
            String timeDate = dateFm.format(sdate);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    public static String nextDay() throws ParseException {
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        String nowDate = sf.format(date);
        //通过日历获取下一天日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(sf.parse(nowDate));
        cal.add(Calendar.DAY_OF_YEAR, +1);
        return sf.format(cal.getTime());
    }

    public static String mmDDFormat(Date date) throws ParseException {
        //获取当前日期
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        return sf.format(date);
    }

    /**
     * 格式化服务器时间 11-11 11:11:11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeMDS(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd HH:mm:ss"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formateTimeToString(String time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re_time;
    }

    public static String formateTimeHM(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("HH:mm"); // 格式化当前系统日期
        Date date = new Date((Long.parseLong(time.trim()) * 1000));
        String dateTime = dateFm.format(date);
        return dateTime;
    }


    /**
     * 获取时间为上午 09:20或者下午或者晚上
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMoAoN(Context context, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        String time_s = sdf.format(date);
        int hour = Integer.parseInt(time_s.substring(0, 2));
        StringBuffer re = new StringBuffer();
        if (hour < 6) {
            re.append(context.getString(R.string.emoring));
        } else if (hour < 12) {
            re.append(context.getString(R.string.moring));
        } else if (hour < 13) {
            re.append(context.getString(R.string.midday));
        } else if (hour < 18) {
            re.append(context.getString(R.string.afteroon));
        } else {
            re.append(context.getString(R.string.night));
        }
        re.append(time_s);
        return re.toString();
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    @SuppressLint("SimpleDateFormat")
    public static int dayForWeek(long pTime) throws Exception {
        Date date = new Date(pTime);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return dayForWeek;
    }

    /**
     * 根据星期代码返回中文：周一...
     *
     * @param context
     * @param day
     * @return
     */
    public static String dayForWeekCN(Context context, int day) {
        String[] weekdays = context.getResources().getStringArray(
                R.array.weekdays);
        return weekdays[day - 1];
    }

    // 计算两个日期间的间隔天数
    public static long getDaysFromTwoDate(String time, String current_day) {
        if (time == null || time.equals("")) {
            return 0;
        }
        if (current_day == null || current_day.equals("")) {
            return 0;
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long days = 0;
        try {
            Date date1 = sDateFormat.parse(time);
            Date date2 = sDateFormat.parse(current_day);
            days = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);     // 通过getTime()方法，把时间Date转换成毫秒格式Long类型，进行计算
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return days;
    }

    /**
     * 机票时间格式化
     *
     * @param time
     * @return
     */
    public static String formatAirTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(Long.valueOf(time) * 1000);
        }
        return "";
    }

    /**
     * 获取当前事件天的描述：昨天/星期* *月*号
     *
     * @param context
     * @param time
     * @param current_time
     * @return
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDay(Context context, long time, long current_time)
            throws Exception {
        String current_day = formatDateString("yyyy-MM-dd", current_time);
        String time_day = formatDateString("yyyy-MM-dd", time);
        long dur_day = getDaysFromTwoDate(time_day, current_day);
        LogUtil.d(TAG, " dur_day = " + dur_day + " dur_year = ");
        StringBuffer day = new StringBuffer();
        int current_weekday;
        int time_weekday;
        if (dur_day > 0) {
            if (dur_day == 1) {
                day.append(context.getResources().getString(R.string.yesterday));
            } else {
                current_weekday = dayForWeek(current_time);
                time_weekday = dayForWeek(time);
                LogUtil.d(TAG, "current_weekday = " + current_weekday + " time_weekday ="
                        + time_weekday);
                if ((current_weekday > time_weekday)
                        && (dur_day == (current_weekday - time_weekday))) {
                    day.append(dayForWeekCN(context, time_weekday));
                } else {
                    String format_string;
                    int during_year;
                    try {
                        SimpleDateFormat format_year = new SimpleDateFormat(
                                context.getResources().getString(
                                        R.string.format_year));
                        int current_year = Integer.parseInt(format_year
                                .format(new Date(current_time)));
                        int time_year = Integer.parseInt(format_year
                                .format(new Date(time)));
                        during_year = current_year - time_year;
                        LogUtil.d(TAG, "during_year = " + during_year + " current_year= "
                                + current_year + " time_year= " + time_year);
                        if (during_year > 0) {
                            format_string = context.getResources().getString(
                                    R.string.format_date_year);
                        } else {
                            format_string = context.getResources().getString(
                                    R.string.format_date);
                        }
                    } catch (Exception e) {
                        LogUtil.d(TAG, "e = " + e);
                        format_string = context.getResources().getString(
                                R.string.format_date_year);
                    }
                    SimpleDateFormat format = new SimpleDateFormat(
                            format_string);
                    Date date = new Date(time);
                    day.append(format.format(date));
                }
            }
        }
        day.append(" ");
        LogUtil.d(TAG, "dur_day = " + dur_day + " day = " + day);
        return day.toString();
    }

    public static String getTimeChat(Context context, String time) {
        long time_data = Long.parseLong(time) * 1000l;
        long current_time = System.currentTimeMillis();
        StringBuffer rs = new StringBuffer();
        try {
            rs.append(getDay(context, time_data, current_time));
        } catch (Exception e) {
            LogUtil.d(TAG, " e = " + e.toString());
            e.printStackTrace();
        }

        rs.append(getMoAoN(context, time_data));

        return rs.toString();
    }

    /**
     * 获取与当前的时间差
     *
     * @param time data_time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDistanceTime(Context context, String time) {
        long day = 0;// 天数
        long hour = 0;// 小时
        long min = 0;// 分钟
        long sec = 0;// 秒
        long time_date = Long.parseLong(time) * 1000l;
        long current_time = System.currentTimeMillis();
        long diff = current_time - time_date;
        day = current_time / (24 * 60 * 60 * 1000) - time_date
                / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000));
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String rs = "";
        if (min == 0) {
            rs = sec + context.getResources().getString(R.string.seconds_ago);
            return rs;
        }
        if (hour == 0) {
            rs = min + context.getResources().getString(R.string.minute_ago);
            return rs;
        }
        if (day == 0) {
            if (hour <= 4) {
                rs = hour + context.getResources().getString(R.string.hour_ago);

                return rs;
            } else {
                DateFormat df2 = new SimpleDateFormat("HH:mm");
                rs = context.getResources().getString(R.string.today)
                        + df2.format(time_date);
                return rs;
            }
        } else if (day == 1) {
            DateFormat df2 = new SimpleDateFormat("HH:mm");
            rs = context.getResources().getString(R.string.yesterday)
                    + df2.format(time_date);
            return rs;
        } else {
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            rs = df2.format(time_date);
            return rs;
        }
    }

    /**
     * 获取周几
     *
     * @param context
     * @param time
     * @return
     */
    public static String getWeekDay(Context context, String time) {
        long time_data = Long.parseLong(time) * 1000l;
        String[] weekDays = context.getResources().getStringArray(R.array.week_day);
        Date date = new Date(time_data);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayofWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofWeek < 0)
            dayofWeek = 0;
        return weekDays[dayofWeek];
    }

    /**
     * 获取周几
     *
     * @param context
     * @param time
     * @return
     */
    public static String getWeekDay(Context context, long time) {
        String[] weekDays = context.getResources().getStringArray(R.array.week_day);
        Date date = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayofWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofWeek < 0)
            dayofWeek = 0;
        return weekDays[dayofWeek];
    }

    /**
     * 判断是否是今天或者明天，是的话返回对应参数
     *
     * @param context
     * @param time
     * @return
     */
    public static String isTodayOrTomorrow(Context context, long time) {
        String date_string;
        long day;// 天数
        long time_date = time;
        long current_time = System.currentTimeMillis();
        day = getDuringDay(current_time, time_date);
        if (day == 0) {
            date_string = context.getString(R.string.today);
        } else if (day == 1) {
            date_string = context.getString(R.string.tomorrow);
        } else {
            date_string = "";
        }

        return date_string;
    }

    /*
     * Date格式化输出string
     * @param Demo
     * @return
     */
    public static String dateFromString(Date date) {
        if (date == null) return "";
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    public static boolean dateCompareSystDateLater(long targerTime) {
        long sytemTime = System.currentTimeMillis();
        if (targerTime <= sytemTime) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * string输出Calendar
     *
     * @param str
     * @return
     */
    public static Calendar calendarFromString(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 格式化服务器时间 11月11日 11:11
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTimeMDHM(String time) {
        SimpleDateFormat dateFm = new SimpleDateFormat("MM月dd日 HH:mm"); // 格式化当前系统日期
        String dateTime = "";
        if (!TextUtils.isEmpty(time)) {
            Date date = new Date((Long.parseLong(time.trim()) * 1000));
            dateTime = dateFm.format(date);
        }
        return dateTime;
    }

    private static SimpleDateFormat mSdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat mSdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat mSdf3 = new SimpleDateFormat("yyyy年MM月dd日");

    public static Date strToDate(String strdate) {

        try {
            return mSdf2.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date will be change to format "yyyy年mm月dd日" str
     *
     * @param date
     * @return
     */
    public static String dateTostrDate(Date date) {
        String strDate = "";
        strDate = mSdf3.format(date);
        return strDate;
    }

    /**
     * "yyyy年mm月dd日" str will be change to date
     *
     * @param date
     * @return
     */
    public static Date strDateToDateCNYMD(String strDate) {

        Date date = null;
        try {
            date = mSdf3.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date will be change to format "yyyy-MM-dd" str
     *
     * @param date
     * @return
     */
    public static String dateTostrDateWithLine(Date date) {
        String strDate = "";
        strDate = mSdf2.format(date);
        return strDate;
    }

    /**
     * @param days 天数
     * @return 返回当前日期加天数的日期
     */
    public static String dateCalculate(Long timeStamp, long days) {
        String resultdate;
        resultdate = timeStampToYYDDMM(timeStamp + days * 24 * 3600 * 1000);
        return resultdate;
    }

    public static long dateCalculateTime(Long timeStamp, long days) {
        return (timeStamp + days * 24 * 3600 * 1000);
    }

    public static long dateToTimeStamp(String date) {
        Long unixTimestamp = Long.valueOf(0);
        try {
            unixTimestamp = mSdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTimestamp;
    }

    public static String dateCalculate(String date, int days) {
        return dateCalculate(dateToTimeStamp(date), days);
    }

    /**
     * 判断日期格式是否正确
     */
    public static boolean isDateFormat(String dataStr) {
        boolean state = false;
        if (!isValidDate(dataStr)) {
            return state;
        } else {
            try {
                java.text.SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
                dFormat.setLenient(false);
                java.util.Date d = dFormat.parse(dataStr);
                state = true;
            } catch (ParseException e) {
                state = false;
            }
            return state;
        }
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String timeStampToDate(Long millTime) {
        return mSdf2.format(millTime);
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * @param millTime
     * @return
     */
    public static String YY_DD_MM2YYDDMM(String millTime) {
        Date date = new Date(millTime);
        return mSdf.format(date);
    }

    public static String timeStampToYYDDMM(Long millTime) {
        return mSdf2.format(millTime);
    }

    /**
     * @param millTime
     * @return
     */
    public static String timeStamp2YYDDMM(Long millTime) {
        return mSdf.format(millTime);
    }
}
