package com.sky.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * 处理工具
 * 
 * @author sky
 * 
 */
public class DateUtil {
    /**
     * 将时间格式化成中国式的字符串(xxxx年xx月xx日)
     * 
     * @param time
     * @return
     */
    public static String formatChineseDate(long time) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(time);
	return String.format("%d年%d月%d日", calendar.get(Calendar.YEAR),
		calendar.get(Calendar.MONTH) - Calendar.JANUARY + 1,
		calendar.get(Calendar.DATE));
    }

    /**
     * 根据年月日获取时间
     * 
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static long getTime(int year, int month, int date) {
	return getTime(year, month, date, 0, 0, 0);
    }

    /**
     * 根据年月日时分秒获取时间
     * 
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long getTime(int year, int month, int date, int hour,
	    int minute, int second) {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month - 1 + Calendar.JANUARY);
	calendar.set(Calendar.DATE, date);
	calendar.set(Calendar.HOUR, hour);
	calendar.set(Calendar.MINUTE, minute);
	calendar.set(Calendar.SECOND, second);
	return calendar.getTimeInMillis();
    }

    /**
     * 获取年份
     * 
     * @param time
     * @return
     */
    public static int getYear(long time) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(time);
	return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * 
     * @param time
     * @return
     */
    public static int getMonth(long time) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(time);
	return calendar.get(Calendar.MONTH) - Calendar.JANUARY + 1;
    }

    /**
     * 获取天
     * 
     * @param time
     * @return
     */
    public static int getDate(long time) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(time);
	return calendar.get(Calendar.DATE);
    }

    /**
     * 获取一年的天数
     * 
     * @param year
     * @return
     */
    public static int getDateCount(int year) {
	// 闰年的计算方式：是100倍数的年份并且是400的倍数，则为闰年；是100倍数的年份不是400倍数的则为平年;
	// 非100倍数的年份并且是4的倍数，则为闰年；否则为平年。
	if (year % 100 == 0 && year % 400 == 0) {
	    return 366;
	} else if (year % 100 == 0 && year % 400 != 0) {
	    return 365;
	} else if (year % 4 == 0) {
	    return 366;
	} else {
	    return 365;
	}
    }

    /**
     * 获取指定月的天数
     * 
     * @param year
     *            年份，如果month为2必须要传，其他月份可传0
     * @param month
     * @return
     */
    public static int getDateCount(int year, int month) {
	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12: {
	    return 31;
	}

	case 4:
	case 6:
	case 9:
	case 11: {
	    return 30;
	}

	case 2: {
	    return getDateCount(year) == 366 ? 29 : 28;
	}

	default: {
	    return 0;
	}
	}
    }

    /**
     * 获取一年的起始时间
     * 
     * @param year
     * @return
     */
    public static long getBeginTime(int year) {
	return getTime(year, 1, 1);
    }

    /**
     * 获取一年的结束时间
     * 
     * @param year
     * @return
     */
    public static long getEndTime(int year) {
	return getTime(year, 12, 31, 23, 59, 59);
    }

    /**
     * 获取一月的开始时间
     * 
     * @param year
     * @param month
     * @return
     */
    public static long getBeginTime(int year, int month) {
	return getTime(year, month, 1);
    }

    /**
     * 获取一月的结束时间
     * 
     * @param year
     * @param month
     * @return
     */
    public static long getEndTime(int year, int month) {
	return getTime(year, month, getDateCount(year, month), 23, 59, 59);
    }

    /**
     * 是否同一天
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDate(long time1, long time2) {
	return formatChineseDate(time1).equals(formatChineseDate(time2));
    }

    /** 解析时间：得到的格式为 2013-03-20 08：00：00 --- yyyy-MM-dd hh-mm*ss */
    public static String parseDate(Date date, String pattern) {
	return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 解析时间，时间格式为(yyyy-MM-dd)
     * 
     * @param date
     * @return
     */
    public static long parseDate(String date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date data = null;
	try {
	    data = format.parse(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (data != null) {
	    return data.getTime();
	}
	return 0;
    }

    public static long parseDate(String pattern, String date) {
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	Date data = null;
	try {
	    data = format.parse(date);
	} catch (Exception e) {
	    return 0;
	}
	if (data != null) {
	    return data.getTime();
	}
	return 0;
    }

    /**
     * 解析时间，时间格式为(yyyy-MM-dd HH:mm:ss)
     * 
     * @param date
     * @return
     */
    public static long parseDateAndTime(String date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date data = null;
	try {
	    data = format.parse(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (data != null) {
	    return data.getTime();
	}
	return 0;
    }

    /**
     * 获取当天0点开始的时间
     * 
     * @Returns the time of this The Same Day Start from 0 point in
     *          milliseconds.
     */
    public static long getTheSameDayStart() {
	Calendar currentDate = new GregorianCalendar();
	currentDate.set(Calendar.HOUR_OF_DAY, 0);
	currentDate.set(Calendar.MINUTE, 0);
	currentDate.set(Calendar.SECOND, 0);
	return currentDate.getTimeInMillis();
    }

    /** 获取当周0点开始的时间 */
    public static long getTheSameWeek() {
	Calendar currentDate = new GregorianCalendar();
	currentDate.setFirstDayOfWeek(Calendar.MONDAY);
	currentDate.set(Calendar.HOUR_OF_DAY, 0);
	currentDate.set(Calendar.MINUTE, 0);
	currentDate.set(Calendar.SECOND, 0);
	currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	return currentDate.getTimeInMillis();
    }

    /**
     * 与当前的时间想对比，获取相对的时间。
     * 
     * @return type 0为当天时间内，-1为昨天时间内 ， -2 为前天时间内 ， -3 为更早的时间外
     */
    public synchronized static final int getRelativeTime(long time) {
	int type = 0;
	long theSameDayZero = getTheSameDayStart();// 今天零点的时间
	long theYesterdayZero = theSameDayZero - 86400000;// 昨天零点的时间
	long theDayBeforeYesterday = theYesterdayZero - 86400000;// 前天零点的时间
	// 昨天 86400000=24*60*60*1000 一天
	if (time > theSameDayZero) {// 时间点 在 今天 内
	    // 返回时分的时间字符串数据
	    type = 0;
	} else if (time < theSameDayZero && time >= theYesterdayZero) {// 时间点 在
								       // 昨天范围内
	    // 返回以周为单位的字符串数据
	    type = -1;
	} else if (time < theYesterdayZero && time >= theDayBeforeYesterday) {// 时间点已经前天范围内了
	    // 返回
	    type = -2;
	} else {// 时间点 在更早之前的时间内
	    type = -3;
	}
	return type;
    }

    /**
     * 将毫秒转换为时间格式化后的格式
     * 
     * @param milliseconds
     * @return 2012-10-23 12：42：23
     */
    public static String formatSecondsReturnTime(long milliseconds) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	return dateFormat.format(new Date(milliseconds));
    }

    /**
     * 将毫秒转换为时间格式化后的格式
     * 
     * @param milliseconds
     * @return 2012-10-23
     */
    public static String formatSecondsReturnDate(long milliseconds) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	return dateFormat.format(new Date(milliseconds));
    }

    /**
     * 将毫秒转换为时间格式化后的格式
     * 
     * @param milliseconds
     * @return 12-10-23
     */
    public static String formatSecondsReturnDateLessFirstTwo(long milliseconds) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
	return dateFormat.format(new Date(milliseconds));
    }

    /**
     * 
     * @param pattern
     * @return
     */
    public static String formatCurrentTime(String pattern) {
	SimpleDateFormat format = new SimpleDateFormat(pattern);// 获取当前时间，进一步转化为字符串
	return format.format(new Date());
    }

    /**
     * 解析时间，时间格式为(yyyy-MM-dd)
     * 
     * @param date
     * @return
     */
    public static Date parseDate1(String date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
	try {
	    return format.parse(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }


    public static String formatDate(String pattern, long time) {
	SimpleDateFormat format = new SimpleDateFormat(pattern);// 获取当前时间，进一步转化为字符串
	return format.format(new Date(time));
    }

    /**
     * 时间格式转换
     * 
     * @param milliseconds
     * @return
     */
    public static String formatSecondsReturnDateLessFirstTwo(long milliseconds,
	    String format) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	return dateFormat.format(new Date(milliseconds));
    }

    /**
     * 日期比较大小
     */
    public static int matchDate(String t1, String t2) {
	java.text.DateFormat df = new SimpleDateFormat("HH:mm");
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();
	try {
	    c1.setTime(df.parse(t1));
	    c2.setTime(df.parse(t2));
	} catch (Exception e) {
	    System.err.println("格式不正确");
	    return -2;
	}
	return c1.compareTo(c2);
    }

    /**
     * 获取当天时间
     */
    public static String CurDateTime() {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	return sdf.format(new Date());
    }

    /**
     * 计算当天是星期几
     */
    // @SuppressLint("SimpleDateFormat")
    public static int CurDateToWeek() {
	Calendar cal = Calendar.getInstance();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// Date nowDate;
	try {
	    // nowDate = sdf.parse(date);
	    cal.setTime(new Date());// cal设置为当天的
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}// 获取当前date
	return cal.get(Calendar.DAY_OF_WEEK) - 1;
	// String weekstr="";
	// if(week==0){
	// weekstr="周日";
	// }else if(week==1){
	// weekstr="周一";
	// }else if(week==2){
	// weekstr="周二";
	// }else if(week==3){
	// weekstr="周三";
	// }else if(week==4){
	// weekstr="周四";
	// }else if(week==5){
	// weekstr="周五";
	// }else if(week==6){
	// weekstr="周六";
	// }
	// return weekstr;
    }

    /**
     * 获取当前月往前推forwordMonth月后得到的日历
     * 
     * @param nowCal
     *            20130205
     * @param forwordMonth
     * @return
     */
    public static String getForwordMonth(String nowCal, int forwordMonth) {
	if (nowCal != null && !"".equals(nowCal) && nowCal.length() == 8) {
	    StringBuilder sb = new StringBuilder();
	    int year = Integer.parseInt(nowCal.subSequence(0, 4).toString());
	    int month = Integer.parseInt(nowCal.subSequence(4, 6).toString());
	    if (month > forwordMonth) {
		return sb.append(year)
			.append(getUionDate(month - forwordMonth)).append("01")
			.toString();
	    } else if (month == forwordMonth) {
		return sb.append(year - 1).append(12).append("01").toString();
	    } else {
		int divide = forwordMonth / 12;
		if (divide == 0) {
		    return sb.append(year - 1)
			    .append(12 - (forwordMonth - month)).append("01")
			    .toString();
		} else {
		    if ((forwordMonth - divide * 12) > month) {
			return sb.append(year - divide)
				.append(forwordMonth - month).append("01")
				.toString();
		    } else {
			return sb.append(year - divide - 1)
				.append(12 - (forwordMonth - month))
				.append("01").toString();
		    }
		}
	    }
	}
	return nowCal;
    }

    public static String getUionDate(int date) {
	DecimalFormat df = new DecimalFormat("00");
	if (date > 0) {
	    return df.format(date);
	} else {
	    return df.format(0);
	}
    }

    /**
     * 返回时间 eg: 20130120
     * 
     * @param cal
     * @return
     */
    public static String getDateStr(Calendar cal) {
	StringBuilder sb = new StringBuilder();
	sb.append(cal.get(Calendar.YEAR))
		.append(DateUtil.getUionDate(cal.get(Calendar.MONTH) + 1))
		.append(DateUtil.getUionDate(cal.get(Calendar.DATE)));
	return sb.toString();
    }

    /**
     * 比较两个日期是否是同一天
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean equalsDate(Date date1, Date date2) {

		return date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate();
    }

    /**
     * 比较日期大小
     * 
     * @param dateNow
     * @param dateAfter
     * @return
     */
    public static boolean afterDate(Date dateNow, Date dateAfter) {

	if (dateAfter.getYear() > dateNow.getYear()) {
	    return true;
	}
	if (dateAfter.getYear() == dateNow.getYear()) {
	    if (dateAfter.getMonth() > dateNow.getMonth()) {
		return true;
	    }
	    if (dateAfter.getMonth() == dateNow.getMonth()) {
		if (dateAfter.getDate() > dateNow.getDate()) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * 当前时间是否比指定时间大
     * 
     * @param curDate
     * @param SpecialDate
     * @return
     */
    public static boolean dateAfterSpecialDate(String curDate,
	    String specialDate) {
	if (curDate != null && curDate.length() == 8 && specialDate != null
		&& specialDate.length() == 8) {
	    int curYear = Integer
		    .parseInt(curDate.subSequence(0, 4).toString());
	    int curMonth = Integer.parseInt(curDate.subSequence(4, 6)
		    .toString());
	    int curDay = Integer.parseInt(curDate.subSequence(6, 8).toString());
	    int specialYear = Integer.parseInt(curDate.subSequence(0, 4)
		    .toString());
	    int specialMonth = Integer.parseInt(curDate.subSequence(4, 6)
		    .toString());
	    int specialDay = Integer.parseInt(curDate.subSequence(6, 8)
		    .toString());

	    if (curYear == specialYear) {
		if (curMonth == specialMonth) {
			return curDay > specialDay;
		} else if (curMonth > specialMonth) {
		    return true;
		}
	    } else if (curYear > specialYear) {
		return true;
	    }
	}
	return false;
    }
}
