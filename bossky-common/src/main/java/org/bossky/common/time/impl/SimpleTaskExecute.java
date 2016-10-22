package org.bossky.common.time.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bossky.common.time.Task;
import org.bossky.common.time.TaskExecute;
import org.bossky.common.util.Misc;
import org.bossky.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的任务执行器实现
 * 
 * @author daibo
 *
 */
public class SimpleTaskExecute implements TaskExecute, Runnable {
	/** 日志 */
	static final Logger _Logger = LoggerFactory.getLogger(SimpleTaskExecute.class);
	/** 队列 */
	protected ConcurrentLinkedQueue<SimpleTask> queue;
	/** 是否运行中 */
	protected boolean isRunning;
	/** 执行器名称 */
	protected String name;

	public SimpleTaskExecute() {
		this(Misc.toHex(System.currentTimeMillis()));
	}

	public SimpleTaskExecute(String name) {
		this.name = name;
		queue = new ConcurrentLinkedQueue<SimpleTask>();
		start();
	}

	/**
	 * 启动执行器
	 */
	public void start() {
		isRunning = true;
		Thread thread = new Thread(this, "SimpleTaskExecute-" + name);
		thread.start();
	}

	public void stop() {
		synchronized (this) {
			isRunning = false;
			notify();
		}
	}

	@Override
	public Task schedule(Runnable runnable) {
		return schedule(runnable, 0);
	}

	@Override
	public Task schedule(Runnable runnable, long delay) {
		return schedule(runnable, delay, 0);
	}

	@Override
	public Task schedule(Runnable runnable, long delay, long interval) {
		SimpleTask task = new SimpleTask(runnable, delay, interval);
		queue.offer(task);
		synchronized (this) {
			notify();
		}
		return task;
	}

	@Override
	public void run() {
		while (isRunning) {
			// 下次醒来的时间
			long nextWakeUp = Integer.MAX_VALUE;
			Iterator<SimpleTask> it = queue.iterator();
			while (it.hasNext()) {
				SimpleTask task = it.next();
				if (task.ready()) {
					if (_Logger.isDebugEnabled()) {
						_Logger.debug("开始执行" + task);
					}
					try {
						task.exectute();
					} catch (Throwable e) {
						_Logger.error("执行" + task + "出错", e);
					}
					if (_Logger.isDebugEnabled()) {
						_Logger.debug("执行结束" + task);
					}
				}
				if (task.hasNext()) {
					long nexttime = task.getNextExecuteTime();
					if (nextWakeUp > nexttime) {
						nextWakeUp = nexttime;
					}
				} else {
					it.remove();
				}
			}
			synchronized (this) {
				try {
					this.wait(nextWakeUp);
				} catch (InterruptedException e) {
					// 正常现象
				}
			}

		}
	}

	public static void main(String[] args) {
		SimpleTaskExecute exe = new SimpleTaskExecute();

		exe.schedule(new Runnable() {

			@Override
			public void run() {
				System.out.println("我延迟5秒1秒执行,时间:" + TimeUtil.formatCompleteTime(new Date()));
			}
		}, 5 * 1000, 1 * 1000);
	}

}
