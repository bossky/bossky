package org.bossky.common.time.impl;

import org.bossky.common.time.Task;

/**
 * 简单的任务实现
 * 
 * @author daibo
 *
 */
public class SimpleTask implements Task {
	/** 要执行的任务 */
	protected Runnable run;
	/** 延迟 */
	protected long delay;
	/** 间隔 */
	protected long interval;
	/** 最后一次执行时间 */
	protected long lastExecuteTime;
	/** 下一次执行的时间 */
	protected long nextExecuteTime;
	/** 是否已结束 */
	protected boolean isFinish = false;

	protected SimpleTask(Runnable run, long delay, long interval) {
		this.run = run;
		this.delay = delay < 0 ? 0 : delay;
		this.interval = interval < 0 ? 0 : interval;
		this.nextExecuteTime = System.currentTimeMillis() + delay;
		this.lastExecuteTime = 0;
	}

	@Override
	public void exectute() {
		run.run();
		lastExecuteTime = System.currentTimeMillis();
		nextExecuteTime = lastExecuteTime + interval;
	}

	/**
	 * 还有下次?
	 * 
	 * @return
	 */
	public boolean hasNext() {
		if (isFinish) {
			return false;
		}
		if (interval > 0) {
			return true;
		}
		return lastExecuteTime == 0;
	}

	/**
	 * 下一次执行时间
	 * 
	 * @return
	 */
	public long getNextExecuteTime() {
		if (isFinish) {
			return Integer.MAX_VALUE;
		}
		if (interval > 0) {
			if (lastExecuteTime == 0) {
				return delay;
			} else {
				long time = (lastExecuteTime + interval) - System.currentTimeMillis();
				return time > 0 ? time : 0;
			}
		} else {
			if (lastExecuteTime == 0) {
				return delay;
			} else {
				return Integer.MAX_VALUE;
			}
		}
	}

	/**
	 * 准备好了
	 * 
	 * @return
	 */
	public boolean ready() {
		if (isFinish) {
			return false;
		}
		return nextExecuteTime <= System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "[ run = \"" + run + "\" delay = \"" + delay + "\" , interval = \"" + interval + "\" ]";
	}

	@Override
	public void finish() {
		isFinish = true;
	}
}
