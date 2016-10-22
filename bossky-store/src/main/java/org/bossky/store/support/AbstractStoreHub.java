package org.bossky.store.support;

import java.util.HashMap;
import java.util.Map;

import org.bossky.common.util.Misc;
import org.bossky.store.Store;
import org.bossky.store.StoreHub;
import org.bossky.store.Storeble;

/**
 * 抽象的存储集中器
 * 
 * @author bo
 *
 */
public abstract class AbstractStoreHub implements StoreHub {
	/** 存储器集合 */
	private Map<Key, Store<?>> stores = new HashMap<Key, Store<?>>();

	protected int serverId;

	public AbstractStoreHub() {
	}

	public void setServerId(int id) {
		serverId = id;
	}

	@Override
	public int getServerId() {
		return serverId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Storeble> Store<T> getStore(Class<T> clazz,
			Object... initargs) {
		Key key = new Key(clazz, initargs);
		Store<T> store = (Store<T>) stores.get(key);// 有风险,万一泛型不对
		return store;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Storeble> Store<T> openStore(Class<T> clazz,
			Object... initargs) {
		Key key = new Key(clazz, initargs);
		Store<T> store = (Store<T>) stores.get(key);// 有风险,万一泛型不对
		if (null != store) {
			return store;
		}
		Store<T> newStore = createStore(clazz, initargs);
		synchronized (stores) {
			store = (Store<T>) stores.get(key);
			if (null != store) {
				return (Store<T>) store;// 被人抢先了,用别人的吧
			}
			store = newStore;
			stores.put(key, newStore);
		}
		return store;
	}

	/**
	 * 创建存储器
	 * 
	 * @param clazz
	 * @param initargs
	 */
	protected abstract <T extends Storeble> Store<T> createStore(
			Class<T> clazz, Object[] initargs);

	/**
	 * 键,用于做相同参数的唯一标识
	 * 
	 * @author bo
	 *
	 */
	class Key {
		protected Class<?> clazz;
		protected Object[] initargs;

		public Key(Class<?> clazz, Object[] initargs) {
			this.clazz = clazz;
			this.initargs = initargs;
		}

		@Override
		public int hashCode() {
			if (null == initargs) {
				return clazz.hashCode();
			}
			int hashcode = clazz.hashCode();
			for (Object obj : initargs) {
				hashcode += (obj.hashCode() * 31);
			}
			return hashcode;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Key) {
				Key other = (Key) obj;
				return Misc.eq(clazz, other.clazz)
						&& Misc.eq(initargs, other.initargs);
			}
			return false;
		}
	}

}
