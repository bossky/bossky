package org.bossky.common.support;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

/**
 * 将v转换成e的列表
 * 
 * @author daibo
 *
 * @param <E>
 * @param <V>
 */
public abstract class TranformList<E, V> extends AbstractList<E> {
	/** 原列表 */
	protected List<V> original;
	/** 缓存 */
	protected Object[] cache;
	/** 空对象-占位用的 */
	protected static Object EMPTY = new Object();

	public TranformList(List<V> original) {
		this(original, true);
	}

	public TranformList(List<V> original, boolean isUserCache) {
		if (null == original) {
			this.original = Collections.emptyList();
		} else {
			this.original = original;
			if (isUserCache) {
				cache = new Object[original.size()];
			} else {
				cache = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (null != cache) {
			Object obj = cache[index];
			if (null == obj) {
				E e = tranform(original.get(index));
				if (null == e) {
					cache[index] = EMPTY;
				} else {
					cache[index] = e;
				}
				return e;
			} else {
				return obj == EMPTY ? null : (E) obj;
			}
		}
		return tranform(original.get(index));
	}

	/**
	 * 将v转换成e
	 * 
	 * @param v
	 * @return
	 */
	public abstract E tranform(V v);

	@Override
	public int size() {
		return original.size();
	}

}
