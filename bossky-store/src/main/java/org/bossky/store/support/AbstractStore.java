package org.bossky.store.support;

import org.bossky.common.ResultPage;
import org.bossky.store.Store;
import org.bossky.store.StoreId;
import org.bossky.store.Storeble;

/**
 * 抽象的存储器
 * 
 * @author bo
 *
 */
public abstract class AbstractStore<T extends Storeble> implements Store<T> {

	@Override
	public T get(String id) {
		return get(StoreId.valueOf(id));
	}

	@Override
	public T get(StoreId id) {
		if (null == id) {
			return null;
		}
		return doGet(id.getId());
	}

	@Override
	public void save(T storeble) {
		if (null == storeble) {
			throw new NullPointerException("对象不能为空");
		}
		doSave(storeble);
	}

	@Override
	public T remove(String id) {
		return remove(StoreId.valueOf(id));
	}

	@Override
	public T remove(StoreId id) {
		if (null == id) {
			return null;
		}
		return doRemove(id.getId());
	}

	@Override
	public ResultPage<T> startWith(String prefix) {
		return doStartWith(prefix);
	}

	@Override
	public ResultPage<T> search(String start, String end) {
		return doSearch(start, end);
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	protected abstract T doGet(String id);

	/**
	 * 保存对象
	 * 
	 * @param storeble
	 */
	protected abstract void doSave(T storeble);

	/**
	 * 执行删除
	 * 
	 * @param id
	 * @return
	 */
	protected abstract T doRemove(String id);

	/**
	 * 执行前缀搜索
	 * 
	 * @param prefix
	 * @return
	 */
	protected abstract ResultPage<T> doStartWith(String prefix);

	/**
	 * 执行范围搜索
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	protected abstract ResultPage<T> doSearch(String start, String end);
}
