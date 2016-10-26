package org.bossky.common.support;

import java.util.Iterator;

/**
 * 将v转换成e的列表
 * 
 * @author daibo
 *
 * @param <E>
 * @param <V>
 */
public abstract class TranformIterator<E, V> implements Iterator<E> {
	/** 原列表 */
	protected Iterator<V> original;

	public TranformIterator(Iterator<V> original) {
		this.original = original;
	}

	@Override
	public boolean hasNext() {
		return original.hasNext();
	}

	@Override
	public E next() {
		return tranform(original.next());
	}

	/**
	 * 将v转换成e
	 * 
	 * @param v
	 * @return
	 */
	public abstract E tranform(V v);

}
