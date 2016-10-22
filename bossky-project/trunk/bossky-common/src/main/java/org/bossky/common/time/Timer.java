package org.bossky.common.time;

import org.bossky.common.TimeOfHour;
import org.bossky.common.TimeOfMinute;

/**
 * 定时器
 * 
 * @author daibo
 *
 */
public interface Timer {
	/**
	 * 每天指定小时执任务
	 * 
	 * @param able
	 * @param hour
	 */
	public Task execute(Runnable able, TimeOfHour hour);

	/**
	 * 每小时指定分钟执行任务
	 * 
	 * @param able
	 * @param minute
	 */
	public Task execute(Runnable able, TimeOfMinute minute);
}
