package org.bossky.store;

import org.bossky.common.ResultPage;

/**
 * 存储器
 * 
 * @author bo
 *
 */
public interface Store<T extends Storeble> {
	/**
	 * 通过Id获取对象
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id);

	/**
	 * 通过Id获取对象
	 * 
	 * @param id
	 * @return
	 */
	public T get(StoreId id);

	/**
	 * 保存对象
	 * 
	 * @param storeble
	 */
	public void save(T storeble);

	/**
	 * 移除对象
	 * 
	 * @param id
	 */
	public T remove(String id);

	/**
	 * 移除对象
	 * 
	 * @param id
	 */
	public T remove(StoreId id);

	/**
	 * 以指定id开关的对象集合
	 * 
	 * @param prefix
	 * @return
	 */
	public ResultPage<T> startWith(String prefix);

	/**
	 * 搜索id指定范围段内的对象集合
	 * 
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 * @return
	 */
	public ResultPage<T> search(String start, String end);

}
