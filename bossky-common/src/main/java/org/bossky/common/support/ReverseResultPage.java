package org.bossky.common.support;

import java.util.Iterator;

import org.bossky.common.ResultPage;

/**
 * 翻转结果集
 * 
 * @author bo
 *
 * @param <E>
 */
public class ReverseResultPage<E> implements ResultPage<E>, Iterator<E> {

	ResultPage<E> rp;

	ReverseResultPage(ResultPage<E> rp) {
		this.rp = rp;
	}

	public static <E> ReverseResultPage<E> wrap(ResultPage<E> rp) {
		return new ReverseResultPage<E>(rp);
	}

	@Override
	public Iterator<E> iterator() {
		return this;
	}

	@Override
	public boolean hasPrePage() {
		return rp.hasNextPage();
	}

	@Override
	public boolean prePage() {
		return rp.nextPage();
	}

	@Override
	public boolean hasNextPage() {
		return rp.hasPrePage();
	}

	@Override
	public boolean nextPage() {
		return rp.prePage();
	}

	@Override
	public boolean firstPage() {
		return rp.gotoPage(rp.getPageSum());
	}

	@Override
	public boolean lastPage() {
		return rp.gotoPage(1);
	}

	@Override
	public boolean gotoPage(int page) {
		return rp.gotoPage(rp.getPageSum() - page);
	}

	@Override
	public int getPage() {
		return rp.getPageSum() - rp.getPage();
	}

	@Override
	public int getPageSum() {
		return rp.getPageSum();
	}

	@Override
	public void setPageSize(int size) {
		rp.setPageSize(size);
	}

	@Override
	public int getPageSize() {
		return rp.getPage();
	}

	@Override
	public int getCount() {
		return rp.getCount();
	}

	@Override
	public E next() {
		return rp.pre();
	}

	@Override
	public boolean hasNext() {
		return rp.hasPre();
	}

	@Override
	public E pre() {
		return rp.next();
	}

	@Override
	public boolean hasPre() {
		return rp.hasNext();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
