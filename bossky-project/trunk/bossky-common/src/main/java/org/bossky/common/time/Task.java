package org.bossky.common.time;

/**
 * 任务
 * 
 * @author daibo
 *
 */
public interface Task {
	/**
	 * 立刻执行任务
	 */
	public void exectute();

	/**
	 * 结束任务
	 */
	public void finish();
}
