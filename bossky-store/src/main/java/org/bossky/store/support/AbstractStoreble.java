package org.bossky.store.support;

import org.bossky.common.Timestamp;
import org.bossky.common.util.Misc;
import org.bossky.store.Assistant;
import org.bossky.store.Store;
import org.bossky.store.StoreHub;
import org.bossky.store.StoreId;
import org.bossky.store.Storeble;
import org.bossky.store.exception.StoreException;

/**
 * 抽象的可存储对象
 * 
 * @author bo
 *
 */
public class AbstractStoreble<T extends Assistant> implements Storeble {
	/** 唯一标识id */
	protected StoreId id;
	/** 助手 */
	protected T assistant;

	/**
	 * 一定要保留这个构造，反射用的
	 * 
	 * @param assistant
	 */
	protected AbstractStoreble(T assistant) {
		this.assistant = assistant;
	}

	@Override
	public StoreId getStoreId() {
		return id;
	}

	@Override
	public void init(StoreId id, Store<? extends Storeble> store) {
		this.id = id;
	}

	public T getAssistant() {
		return assistant;
	}

	/**
	 * 生成一个id
	 */
	protected void genId() {
		genId(null);
	}

	/**
	 * 生成一个带指定前缀的id
	 * 
	 * @param prefix
	 */
	protected void genId(String prefix) {
		if (null == prefix) {
			this.id = new StoreId(getClass(),
					Timestamp.nextHex() + "-" + Misc.toHex(assistant.getStoreHub().getServerId()));
		} else {
			this.id = new StoreId(getClass(),
					prefix + Timestamp.nextHex() + "-" + Misc.toHex(assistant.getStoreHub().getServerId()));
		}
	}

	@SuppressWarnings("unchecked")
	protected void flush() {
		if (null == id) {
			throw new StoreException("未初始化id");
		}
		StoreHub hub = assistant.getStoreHub();
		Store<AbstractStoreble<?>> store = (Store<AbstractStoreble<?>>) hub.getStore(getClass(), assistant);
		store.save(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Storeble) {
			Storeble other = (Storeble) obj;
			return Misc.eq(id, other.getStoreId());
		}
		return false;
	}
}
