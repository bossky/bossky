package org.bossky.common.support;

import java.util.Iterator;

/**
 * 数组分页结果
 * 
 * @author bo
 *
 */
public class ResultPageArray<E> extends AbstractResultPage<E> {
	Object[] arr;
	int cursor;

	ResultPageArray(E[] arr) {
		this.arr = arr;
		cursor = 0;
	}

	@Override
	public int getCount() {
		return arr.length;
	}

	@Override
	public boolean gotoPage(int page) {
		if (page <= 0 || page > getPageSum()) {
			return false;
		}
		this.cursor = (page - 1) * getPageSize();
		this.page = page;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E next() {
		if (hasNext()) {
			return (E) arr[cursor++];
		} else {
			return null;
		}

	}

	@Override
	public boolean hasNext() {
		return cursor < (getPage() * getPageSize() > getCount() ? getCount()
				: getPage() * getPageSize());
	}

	@SuppressWarnings("unchecked")
	@Override
	public E pre() {
		if (hasPre()) {
			return (E) arr[--cursor];
		} else {
			return null;
		}
	}

	@Override
	public boolean hasPre() {
		return cursor > ((getPage() - 1) * getPageSize());
	}

	@Override
	public Iterator<E> iterator() {
		return this;
	}

	public E[] toArray() {
		return toArray(getCount());
	}

	@SuppressWarnings("unchecked")
	public E[] toArray(int limit) {
		limit = limit > getCount() ? getCount() : limit;
		Object[] dest = new Object[limit];
		System.arraycopy(arr, 0, dest, 0, limit);
		return (E[]) dest;
	}
}
