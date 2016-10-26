package org.bossky.common.support;

import java.util.Iterator;

import org.bossky.common.ResultPage;

/**
 * 将v转换成e的列表
 * 
 * @author daibo
 *
 * @param <E>
 * @param <V>
 */
public abstract class TranformResultPage<E, V> implements ResultPage<E> {

	protected ResultPage<V> original;

	public TranformResultPage(ResultPage<V> original) {
		this.original = original;
	}

	@Override
	public Iterator<E> iterator() {
		return new TranformIterator<E, V>(original.iterator()) {

			@Override
			public E tranform(V v) {
				return TranformResultPage.this.tranform(v);
			}

		};
	}

	/**
	 * 将v转换成e
	 * 
	 * @param v
	 * @return
	 */
	public abstract E tranform(V v);

	@Override
	public boolean gotoPage(int page) {
		return original.gotoPage(page);
	}

	@Override
	public int getPage() {
		return original.getPage();
	}

	@Override
	public int getPageSum() {
		return original.getPageSum();
	}

	@Override
	public void setPageSize(int size) {
		original.setPageSize(size);
	}

	@Override
	public int getPageSize() {
		return original.getPageSize();
	}

	@Override
	public int getCount() {
		return original.getCount();
	}

	/**
	 * 将v转换成e
	 * 
	 * @param v
	 * @return
	 */
	// public abstract E tranform(V v);

}
