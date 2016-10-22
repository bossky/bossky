package org.bossky.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类
 * 
 * @author bo
 *
 */
public class TimeUtil {
	/** 记录日志 */
	final static Logger _Logger = LoggerFactory.getLogger(Misc.class);
	/** 一分钟的毫秒数 */
	public final static long ONE_MINUTE_MILLISECOND = 60000;
	/** 一小时的毫秒数 */
	public final static long ONE_HOUR_MILLISECOND = 6 * ONE_MINUTE_MILLISECOND;
	/** 一天的毫秒数 */
	public final static long ONE_DAY_MILLISECOND = 24 * ONE_HOUR_MILLISECOND;

	/**
	 * ２个日期是否为同一天
	 * 
	 * @param date
	 * @param otherdate
	 * @return
	 */
	public static boolean isSameDay(Date date, Date otherdate) {
		return calDay(date, otherdate) == 0;
	}

	/**
	 * 计算２个日期相差的天数 如果 date比otherdate返回正数 date比otherdate大返回负数
	 * 
	 * @param date
	 * @param otherdate
	 * @return
	 */
	public static int calDay(Date date, Date otherdate) {
		long offset = otherdate.getTime() - date.getTime();
		return (int) (offset / ONE_DAY_MILLISECOND);
	}

	/** 格式化日期 yyyy-MM-dd HH:mm:ss.SSS */
	final static SimpleDateFormat COMPLETEFORMA = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 格式化日期为 yyyy-MM-dd HH:mm:ss.SSS格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatCompleteTime(Date date) {
		synchronized (COMPLETEFORMA) {
			return COMPLETEFORMA.format(date);
		}
	}

	/**
	 * 转换字符串yyyy-MM-dd HH:mm:ss.SSS格式,为日期
	 * 
	 * @param date
	 * @return 格式错误返回null
	 */
	public static Date parseCompleteTime(String date) {
		synchronized (COMPLETEFORMA) {
			try {
				return COMPLETEFORMA.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/** yyyy-MM-dd HH:mm:ss */
	static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将yyyy-MM-dd HH:mm:ss的字符串转换成日期对象
	 * 
	 * @param var
	 * @return
	 */
	public static Date parseDate(String var) {
		try {
			synchronized (FORMAT) {
				return FORMAT.parse(var);
			}
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成日期时出错", e);
		}
		return null;
	}

	/**
	 * 将format格式的日期的字符串var转换成日期对象
	 * 
	 * @param var
	 * @param format
	 * @return
	 */
	public static Date parseDate(String var, String format) {
		SimpleDateFormat p = null;
		try {
			p = new SimpleDateFormat(format);
			return p.parse(var);
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成日期时出错", e);
		} finally {
			if (null != p) {
				p = null;// gc
			}
		}
		return null;
	}

}
