package org.bossky.common.support;

import java.util.Iterator;

import org.bossky.common.ResultPage;

/**
 * 抽象分页结果
 * 
 * @author bo
 *
 */
public abstract class AbstractResultPage<E> implements ResultPage<E>, Iterator<E> {
	/** 当前页面 */
	protected int page = 0;
	/** 一页大小 */
	protected int pageSize = DEFAULT_PAGE_SIZE;

	public AbstractResultPage() {

	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int getPageSum() {
		return getCount() / getPageSize() + (getCount() % getPageSize() == 0 ? 0 : 1);
	}

	@Override
	public void setPageSize(int size) {
		pageSize = size;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "当前:" + getPage() + "／" + getPageSum() + "页" + ",每页" + getPageSize() + "个元素,共" + getCount() + "个元素";
	}
}
