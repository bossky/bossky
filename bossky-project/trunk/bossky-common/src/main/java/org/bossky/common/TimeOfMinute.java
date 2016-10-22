package org.bossky.common;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

/**
 * 分钟，秒
 * 
 * @author bo
 *
 */
public class TimeOfMinute {
	/** 分钟 */
	@Resource
	protected int minute;
	/** 秒 */
	@Resource
	protected int second;

	protected TimeOfMinute() {

	}

	public TimeOfMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	}

	public TimeOfMinute(int minute, int second) {
		if (minute > 59) {
			throw new IllegalArgumentException("分钟不能大于59");
		}
		if (second > 59) {
			throw new IllegalArgumentException("秒不能大于59");
		}
		this.minute = minute;
		this.second = second;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	/**
	 * 转换成秒
	 * 
	 * @return
	 */
	public int toSecond() {
		return minute * 60 + second;
	}

	@Override
	public int hashCode() {
		return minute * 13 + second;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof TimeOfMinute) {
			TimeOfMinute hd = (TimeOfMinute) obj;
			return hd.minute == minute && hd.second == second;
		}
		return false;
	}

	@Override
	public String toString() {
		return minute + "分" + second + "秒";
	}
}
