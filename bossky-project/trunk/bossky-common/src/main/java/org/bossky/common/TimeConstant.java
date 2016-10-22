package org.bossky.common;

/**
 * 时间常量
 * 
 * @author daibo
 *
 */
public interface TimeConstant {
	/** 一秒的毫秒数 */
	public static int ONE_SECOND_MILLISECONDE = 1000;
	/** 一分钟的毫秒数 */
	public static int ONE_MINUTE_MILLISECONDE = 60 * ONE_SECOND_MILLISECONDE;
	/** 一小时的毫秒数 */
	public static int ONE_HOUR_MILLISECONDE = 60 * ONE_MINUTE_MILLISECONDE;
	/** 一天的毫秒数 */
	public static int ONE_DAY_MILLISECONDE = 24 * ONE_HOUR_MILLISECONDE;
}
