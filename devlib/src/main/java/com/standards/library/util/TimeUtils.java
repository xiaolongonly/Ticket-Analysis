package com.standards.library.util;

import android.content.Context;

import com.standards.library.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * <p>
     * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
     * <p>
     * 格式的意义如下： 日期和时间模式 <br>
     * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * <p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table>
     * <tr>  <td>字母</td>  <td>日期或时间元素</td>  <td>表示</td>  <td>示例</td>  <td> </td></tr>
     * <tr>  <td>G</td>  <td>Era</td>  <td>标志符</td>  <td>Text</td>  <td>AD</td>  <td> </td></tr>
     * <tr> <td>y</td>  <td>年</td>  <td>Year</td>  <td>1996;</td>  <td>96</td>  <td> </td></tr>
     * <tr>  <td>M</td>  <td>年中的月份</td>  <td>Month</td>  <td>July;</td>  <td>Jul;</td>  <td>07 </td></tr>
     * <tr>  <td>w</td>  <td>年中的周数</td>  <td>Number</td>  <td>27</td>  <td> </td></tr>
     * <tr>  <td>W</td>  <td>月份中的周数</td>  <td>Number</td>  <td>2</td>  <td> </td></tr>
     * <tr>  <td>D</td>  <td>年中的天数</td>  <td>Number</td>  <td>189</td>  <td> </td></tr>
     * <tr>  <td>d</td>  <td>月份中的天数</td>  <td>Number</td>  <td>10</td>  <td> </td></tr>
     * <tr>  <td>F</td>  <td>月份中的星期</td>  <td>Number</td>  <td>2</td>  <td> </td></tr>
     * <tr>  <td>E</td>  <td>星期中的天数</td>  <td>Text</td>  <td>Tuesday;</td>  <td>Tue </td></tr>
     * <tr>  <td>a</td>  <td>Am/pm</td>  <td>标记</td>  <td>Text</td>  <td>PM</td>  <td> </td></tr>
     * <tr>  <td>H</td>  <td>一天中的小时数（0-23）</td>  <td>Number</td>  <td>0 </td></tr>
     * <tr>  <td>k</td>  <td>一天中的小时数（1-24）</td>  <td>Number</td>  <td>24</td>  <td> </td></tr>
     * <tr>  <td>K</td>  <td>am/pm</td>  <td>中的小时数（0-11）</td>  <td>Number</td>  <td>0</td>  <td> </td></tr>
     * <tr>  <td>h</td>  <td>am/pm</td>  <td>中的小时数（1-12）</td>  <td>Number</td>  <td>12</td>  <td> </td></tr>
     * <tr>  <td>m</td>  <td>小时中的分钟数</td>  <td>Number</td>  <td>30</td>  <td> </td></tr>
     * <tr>  <td>s</td>  <td>分钟中的秒数</td>  <td>Number</td>  <td>55</td>  <td> </td></tr>
     * <tr>  <td>S</td>  <td>毫秒数</td>  <td>Number</td>  <td>978</td>  <td> </td></tr>
     * <tr>  <td>z</td>  <td>时区</td>  <td>General</td>  <td>time</td>  <td>zone</td>  <td>Pacific</td>  <td>Standard</td> <td>Time;</td>  <td>PST;</td>  <td>GMT-08:00 </td></tr>
     * <tr>  <td>Z</td>  <td>时区</td>  <td>RFC</td>  <td>822</td>  <td>time</td>  <td>zone</td>  <td>-0800</td>  <td> </td></tr>
     * </table>
     * <p>
     * <p>
     * <pre>
     *                     yyyy-MM-dd 1969-12-31
     *                     yyyy-MM-dd 1970-01-01
     *               yyyy-MM-dd HH:mm 1969-12-31 16:00
     *               yyyy-MM-dd HH:mm 1970-01-01 00:00
     *              yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
     *              yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
     * </pre>
     */
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 各时间单位与毫秒的倍数
     */
    public static final int UNIT_MSEC = 1;
    public static final int UNIT_SEC = 1000;
    public static final int UNIT_MIN = 60000;
    public static final int UNIT_HOUR = 3600000;
    public static final int UNIT_DAY = 86400000;

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date string2Date(String formatDate) {
        return string2Date(formatDate, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义
     */
    public static Date string2Date(String formatDate, SimpleDateFormat format) {
        return new Date(string2Milliseconds(formatDate, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义
     */
    public static String date2String(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    /**
     * 将Date类型转为时间戳
     */
    public static long date2Milliseconds(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * </pre>
     */
    private static long milliseconds2Unit(long milliseconds, int unit) {
        switch (unit) {
            case UNIT_MSEC:
            case UNIT_SEC:
            case UNIT_MIN:
            case UNIT_HOUR:
            case UNIT_DAY:
                return Math.abs(milliseconds) / unit;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * </pre>
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss
     */
    public static long getIntervalTime(String time1, String time2, int unit) {
        return getIntervalTime(time1, time2, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * </pre>
     * <p>time1和time2格式都为format
     */
    public static long getIntervalTime(String time1, String time2, int unit, SimpleDateFormat format) {
        return milliseconds2Unit(string2Milliseconds(time1, format)
                - string2Milliseconds(time2, format), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * </pre>
     * <p>time1和time2都为Date
     */
    public static long getIntervalTime(Date time1, Date time2, int unit) {
        return milliseconds2Unit(date2Milliseconds(time2) - date2Milliseconds(time1), unit);
    }

    /**
     * 获取当前时间
     * <p>单位（毫秒）
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurTimeString() {
        return milliseconds2String(getCurTimeMills());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return milliseconds2String(getCurTimeMills(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss
     */
    public static long getIntervalByNow(String time, int unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * <p>time1和time2格式都为format
     */
    public static long getIntervalByNow(String time, int unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <pre>
     * UNIT_MSEC:毫秒
     * UNIT_SEC :秒
     * UNIT_MIN :分
     * UNIT_HOUR:小时
     * UNIT_DAY :天
     * <p>time1和time2格式都为format
     */
    public static long getIntervalByNow(Date time, int unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }


    /**
     * @param milliseconds
     * @param type         0->新鲜事列表通用格式，1->它的新鲜事列表格式，2->新鲜事详情格式
     * @return
     */
    public static String formatNewThingDetailTime(Context context, long milliseconds, int type) {
        Date getDate = null;
        getDate = milliseconds2Date(milliseconds);
        final long getTime = getDate.getTime();

        final long currTime = System.currentTimeMillis();
        final Date formatSysDate = new Date(currTime);

        // 判断当前总天数
        final int sysMonth = formatSysDate.getMonth() + 1;
        // 计算时间年需要加1900
        final int sysYear = formatSysDate.getYear()/* + 1900*/;

        // 计算服务器返回时间与当前时间差值
        final long seconds = (currTime - getTime) / 1000;
        final long minute = seconds / 60;
        final long hours = minute / 60;
        final long day = hours / 24;
        final long month = day / calculationDaysOfMonth(sysYear, sysMonth);
        final long year = month / 12;


        if (sysYear != getDate.getYear()) {
            //跨年显示 2015年01月01日  23：30
            String formatStr = "";
            if (type == 2) {
                formatStr = "yyyy年MM月dd日 HH:mm";
            } else if (type == 1) {
                formatStr = "yy/MM/dd HH:mm";
            } else {
                formatStr = "yy-MM-dd";
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.format(getDate);
        } else if (month > 0 || day > 1) {
            //超过两天显示日期 05月08日 15：30
            String formatStr = "";
            if (type == 2) {
                formatStr = "MM月dd日 HH:mm";
            } else if (type == 1) {
                formatStr = "MM/dd HH:mm";
            } else {
                formatStr = "MM-dd";
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.format(getDate);
        } else if (day == 1) {
            //第二天显示 昨天 15：12
            if (type == 2) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        " HH:mm");
                return context.getString(R.string.str_yesterday) + simpleDateFormat.format(getDate);
            } else if (type == 1) {
                return context.getString(R.string.str_yesterday);
            } else {
                return context.getString(R.string.str_yesterday);
            }
        } else if (hours > 0) {
            //1天内显示x'x小时前
            return hours + context.getString(R.string.str_hoursago);
        } else if (minute > 0) {
            //1小时内，显示 x'x分钟前
            return minute + context.getString(R.string.str_minsago);
        } else if (seconds > 0) {
            //按秒显示
            return seconds + context.getString(R.string.str_secondago);
        } else {
            //一分钟（默认）
            return "1" + context.getString(R.string.str_minsago); //都换成分钟前
        }
    }

    /**
     * 计算月数
     *
     * @return
     */
    private static int calculationDaysOfMonth(int year, int month) {
        int day = 0;
        switch (month) {
            // 31天
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            // 30天
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            // 计算2月天数
            case 2:
                day = year % 100 == 0 ? year % 400 == 0 ? 29 : 28
                        : year % 4 == 0 ? 29 : 28;
                break;
        }

        return day;
    }
}
