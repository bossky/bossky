package org.bossky.common.time.impl;

import java.util.Calendar;

import org.bossky.common.TimeOfHour;
import org.bossky.common.TimeOfMinute;
import org.bossky.common.time.Task;
import org.bossky.common.time.TaskExecute;
import org.bossky.common.time.TimeConstant;
import org.bossky.common.time.Timer;

/**
 * 简单的定时器
 * 
 * @author daibo
 *
 */
public class SimpleTimer implements Timer {
	/** 任务执行器 */
	private TaskExecute exe;

	public SimpleTimer() {
		this.exe = new SimpleTaskExecute();
	}

	public SimpleTimer(TaskExecute exe) {
		this.exe = exe;
	}

	@Override
	public Task execute(Runnable able, TimeOfHour hour) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour.getHour());
		cal.set(Calendar.MINUTE, hour.getMinute());
		cal.set(Calendar.SECOND, hour.getSecond());
		cal.set(Calendar.MILLISECOND, 0);
		long delay = cal.getTimeInMillis() - System.currentTimeMillis();
		if (delay < 0) {
			cal.add(Calendar.DATE, 1);
			delay = cal.getTimeInMillis() - System.currentTimeMillis();
		}
		return exe.schedule(able, delay, TimeConstant.ONE_DAY_MILLISECONDE);
	}

	@Override
	public Task execute(Runnable able, TimeOfMinute minute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, minute.getMinute());
		cal.set(Calendar.SECOND, minute.getSecond());
		cal.set(Calendar.MILLISECOND, 0);
		long delay = cal.getTimeInMillis() - System.currentTimeMillis();
		if (delay < 0) {
			cal.add(Calendar.HOUR_OF_DAY, 1);
			delay = cal.getTimeInMillis() - System.currentTimeMillis();
		}
		return exe.schedule(able, delay, TimeConstant.ONE_HOUR_MILLISECONDE);
	}

}
