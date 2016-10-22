package org.bossky.store;

/**
 * 可存储对象
 * 
 * @author bo
 *
 */
public interface Storeble {
	/**
	 * 存储id,唯一
	 * 
	 * @return
	 */
	public StoreId getStoreId();

	/**
	 * 初始化方法
	 * 
	 * @param id
	 * @param store
	 */
	public void init(StoreId id, Store<? extends Storeble> store);
}
