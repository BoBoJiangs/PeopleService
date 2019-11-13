package cn.sts.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具
 * Created by weilin on 17/2/3.
 */

public class DateUtil {

    private static final SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static final SimpleDateFormat ymdhmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat hmsFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat hmFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static final SimpleDateFormat mdChinaFormat = new SimpleDateFormat("MM月dd日", Locale.CHINA);
    private static final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);

    /**
     * 字符串转Date
     *
     * @param dateString yyyy-MM-dd
     * @return Date
     */
    public static Date strYMDToDate(String dateString) {
        Date date = null;
        try {
            date = ymdFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 字符串转Date
     *
     * @param dateString yyyy-MM-dd HH:mm
     * @return Date
     */
    public static Date strYMDHMToDate(String dateString) {

        synchronized (ymdhmFormat) {
            try {
                return ymdhmFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * 字符串转Date
     *
     * @param dateString yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date strYMDHMSToDate(String dateString) {
        synchronized (ymdhmsFormat) {
            try {
                return ymdhmsFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd
     */
    public static String dateToYMDStr(Date date) {
        synchronized (ymdFormat) {
            return ymdFormat.format(date);
        }
    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return HH:mm:ss
     */
    public static String dateToHMSStr(Date date) {
        synchronized (hmsFormat) {
            return hmsFormat.format(date);
        }
    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd HH:mm
     */
    public static String dateToYMDHMStr(Date date) {
        synchronized (ymdhmFormat) {
            return ymdhmFormat.format(date);
        }
    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateToYMDHMSStr(Date date) {
        synchronized (ymdhmsFormat) {
            return ymdhmsFormat.format(date);
        }
    }

    /**
     * Date转字符串
     *
     * @param date 时间
     * @return ["yyyy-MM-dd","HH:mm:ss]
     */
    public static String[] dateToYMDAndHMSArray(Date date) {
        synchronized (ymdhmsFormat) {
            return ymdhmsFormat.format(date).split(" ");
        }
    }

    /**
     * 获取当前日期时间字符串
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentYMDHMSStr() {

        synchronized (ymdhmsFormat) {
            return ymdhmsFormat.format(new Date());
        }
    }

    /**
     * 获取当前日期时间字符串
     *
     * @return yyyyMMddHHmmss
     */
    public static String getCurrentDatetimeStr() {
        synchronized (sdFormat) {
            return sdFormat.format(new Date());
        }
    }

    /**
     * Date转字符串
     *
     * @param ymdhms 时间yyyy-MM-dd HH:mm:ss
     * @return HH:mm
     */
    public static String dateToHMStr(String ymdhms) {
        synchronized (hmFormat) {
            try {
                return hmFormat.format(ymdhmsFormat.parse(ymdhms));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    /**
     * Date转字符串
     *
     * @param ymdhms 时间yyyy-MM-dd HH:mm:ss
     * @return MM月dd日
     */
    public static String dateToMDStr(String ymdhms) {
        synchronized (mdChinaFormat) {
            try {
                return mdChinaFormat.format(ymdhmsFormat.parse(ymdhms));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    /**
     * 获取当前时区
     *
     * @return 时区
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    private static String createGmtOffsetString(boolean includeGmt, boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 毫秒转成时分秒
     *
     * @param millisecond 毫秒
     * @return 00:00:00
     */
    public static String millisecondToHMSStr(long millisecond) {
        millisecond = millisecond / 1000;
        String hh = String.valueOf(millisecond / 60 / 60 % 60);
        if (hh.length() < 2) {
            hh = "0" + hh;
        }
        String mm = String.valueOf(millisecond / 60 % 60);
        if (mm.length() < 2) {
            mm = "0" + mm;
        }
        String ss = String.valueOf(millisecond % 60);
        if (ss.length() < 2) {
            ss = "0" + ss;
        }
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：负的往前推, 正的往后推
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getOtherDateTimeSStr(int diff) {
        synchronized (ymdhmsFormat) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DATE, diff);
            return ymdhmsFormat.format(mCalendar.getTime());
        }
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：负的往前推, 正的往后推
     * @return yyyy-MM-dd HH:mm
     */
    public static String getOtherDateTimeStr(int diff) {
        synchronized (ymdhmFormat) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DATE, diff);
            return ymdhmFormat.format(mCalendar.getTime());
        }
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：负的往前推, 正的往后推
     * @return yyyy-MM-dd
     */
    public static String getOtherDateStr(int diff) {
        synchronized (ymdFormat) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DATE, diff);
            return ymdFormat.format(mCalendar.getTime());
        }
    }

    /**
     * 获取今天的开始和结束的时间戳，例：今天2018-08-14，开始2018-08-14 00:00:00，结束2018-08-15 00:00:00
     *
     * @return 0开始，1结束，毫秒时间戳
     */
    public static Long[] getTodayStartAndEndTimestamp() {
        long now = System.currentTimeMillis() / 1000L;
        long daySecond = 60 * 60 * 24;
        long startTime = now - (now + 60 * 60 * 8) % daySecond;
        Long[] today = new Long[2];
        today[0] = startTime * 1000L;
        today[1] = (startTime + daySecond) * 1000L;
        return today;
    }

    /**
     * 将秒转换成时分秒
     *
     * @param totalSeconds 秒
     * @return 0小时0分钟0秒
     */
    public static String getHourMinuteSeconds(long totalSeconds) {
        long hour = totalSeconds / 3600;
        long min = totalSeconds % 3600 / 60;
        long seconds = totalSeconds % 3600 % 60;
        String durationStr = "";
        if (hour > 0) {
            durationStr += hour + "小时";
        }
        if (min > 0) {
            durationStr += min + "分";
        }
        durationStr += seconds + "秒";
        return durationStr;
    }
}
