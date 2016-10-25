package org.bossky.store.sqlite;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bossky.common.support.AbstractResultPage;
import org.bossky.store.Storeble;

/**
 * 基于sqlite的结果集
 * 
 * @author bo
 *
 * @param <T>
 */
public class SqliteResultPage<T extends Storeble> extends AbstractResultPage<T> {
	SqliteStore<T> store;
	String start;
	String end;
	int count = -1;
	/** 当前结果集 */
	protected List<T> currentResult = Collections.emptyList();
	/** 当前结果位置 */
	protected int index = 0;

	protected SqliteResultPage(SqliteStore<T> store, String start, String end) {
		this.store = store;
		this.start = start;
		this.end = end;
	}

	@Override
	public void setPageSize(int size) {
		super.setPageSize(size);
		gotoPage(getPage());
	}

	@Override
	public boolean gotoPage(int page) {
		int startIndex = (getPage() - 1) * getPageSize();
		int endIndex = startIndex + getPageSize();
		currentResult = store.search(start, end, startIndex, endIndex);
		return !currentResult.isEmpty();
	}

	@Override
	public int getCount() {
		if (count == -1) {
			this.count = doGetCount();
		}
		return count;
	}

	/**
	 * 获取结果数
	 * 
	 * @return
	 */
	protected int doGetCount() {
		return store.count(start, end);
	}

	@Override
	public T next() {
		return currentResult.get(index++);
	}

	@Override
	public boolean hasNext() {
		return index < currentResult.size();
	}

	@Override
	public Iterator<T> iterator() {
		return currentResult.iterator();
	}

}
