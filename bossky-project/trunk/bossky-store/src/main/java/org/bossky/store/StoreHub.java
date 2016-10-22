package org.bossky.store;

/**
 * 存储集中器
 * 
 * @author bo
 *
 */
public interface StoreHub {
	/**
	 * 存储器的服务id
	 * 
	 * @return
	 */
	public int getServerId();

	/**
	 * 打开一个存储器
	 * 
	 * @param clazz
	 *            类
	 * @param initargs
	 *            初始化的构造参数,没有则为null
	 * @return
	 */
	public <T extends Storeble> Store<T> openStore(Class<T> clazz,
			Object... initargs);

	/**
	 * 获取一个存储器
	 * 
	 * @param clazz
	 *            类
	 * @param initargs
	 *            初始化的构造参数,没有则为null
	 * @return
	 */
	public <T extends Storeble> Store<T> getStore(Class<T> clazz,
			Object... initargs);

}
