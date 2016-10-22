package org.bossky.common.time;

/**
 * 任务执行器
 * 
 * @author daibo
 *
 */
public interface TaskExecute {
	/**
	 * 执行任务
	 * 
	 * @param runnable
	 *            要运行的任务
	 * @return
	 */
	public Task schedule(Runnable runnable);

	/**
	 * 执行任务
	 * 
	 * @param runnable
	 *            要运行的任务
	 * @param delay
	 *            延迟多久执行
	 * @return
	 */
	public Task schedule(Runnable runnable, long delay);

	/**
	 * 执行任务
	 * 
	 * @param runnable
	 *            要运行的任务
	 * @param delay
	 *            要延迟多久执行
	 * @param interval
	 *            间隔
	 * @return
	 */
	public Task schedule(Runnable runnable, long delay, long interval);

}
