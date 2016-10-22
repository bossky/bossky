package org.bossky.common;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

/**
 * 小时，分钟，秒
 * 
 * @author bo
 *
 */
public class TimeOfHour {
	/** 小时 */
	@Resource
	protected int hour;
	/** 分钟 */
	@Resource
	protected int minute;
	/** 秒 */
	@Resource
	protected int second;

	protected TimeOfHour() {

	}

	public TimeOfHour(Date date) {
		Calendar cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	}

	public TimeOfHour(int hour, int minute, int second) {
		if (hour > 23 || hour < 0) {
			throw new IllegalArgumentException("小时必须在0~23之间");
		}
		if (minute > 59 || minute < 0) {
			throw new IllegalArgumentException("分钟必须在0~59之间");
		}
		if (second > 59 || second < 0) {
			throw new IllegalArgumentException("分钟必须在0~59之间");
		}
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public TimeOfHour(long millisecond) {
		hour = (int) (millisecond / TimeConstant.ONE_HOUR_MILLISECONDE);
		if (hour > 23 || hour < 0) {
			throw new IllegalArgumentException("小时必须在0~23之间");
		}
		millisecond %= TimeConstant.ONE_HOUR_MILLISECONDE;
		minute = (int) (millisecond / TimeConstant.ONE_MINUTE_MILLISECONDE);
		if (minute > 59 || minute < 0) {
			throw new IllegalArgumentException("分钟必须在0~59之间");
		}
		millisecond %= TimeConstant.ONE_MINUTE_MILLISECONDE;
		second = (int) (millisecond / TimeConstant.ONE_SECOND_MILLISECONDE);
		if (second > 59 || second < 0) {
			throw new IllegalArgumentException("分钟必须在0~59之间");
		}
	}

	/**
	 * 小时数
	 * 
	 * @return
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * 分钟数
	 * 
	 * @return
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * 秒数
	 * 
	 * @return
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * 转换成秒
	 * 
	 * @return
	 */
	public int toSecond() {
		return hour * 60 * 60 + minute * 60 + second;
	}

	/**
	 * 转换成毫秒
	 * 
	 * @return
	 */
	public int toMilliSecond() {
		return hour * TimeConstant.ONE_HOUR_MILLISECONDE + minute * TimeConstant.ONE_MINUTE_MILLISECONDE
				+ second * TimeConstant.ONE_SECOND_MILLISECONDE;
	}

	@Override
	public int hashCode() {
		return hour * 37 + minute * 13 + second;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof TimeOfHour) {
			TimeOfHour hd = (TimeOfHour) obj;
			return hd.hour == hour && hd.minute == minute && hd.second == second;
		}
		return false;
	}

	@Override
	public String toString() {
		return hour + "小时" + minute + "分" + second + "秒";
	}

	public static void main(String[] args) {
		TimeOfHour th = new TimeOfHour(10, 48, 10);
		System.out.println(th);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, th.getHour());
		cal.set(Calendar.MINUTE, th.getMinute());
		cal.set(Calendar.SECOND, th.getSecond());
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println(cal.getTimeInMillis() - System.currentTimeMillis());
	}
}
