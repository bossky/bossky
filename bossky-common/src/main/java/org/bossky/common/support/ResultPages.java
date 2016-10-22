package org.bossky.common.support;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bossky.common.ResultPage;

/**
 * 可分页结果
 * 
 * @author bo
 *
 */
public class ResultPages {
	/** 空的分页结果 */
	protected final static ResultPage<Object> EMPTY_RESULTPAGE = new EmptyResultPage<Object>();

	/**
	 * 将集合转换成可分页结果
	 * 
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> ResultPage<E> toResultPage(Collection<E> collection) {
		return new ArrayResultPage<E>((E[]) collection.toArray());
	}

	/**
	 * 翻转结果集
	 * 
	 * @param rp
	 * @return
	 */
	public static <E> ResultPage<E> reverse(ResultPage<E> rp) {
		return new ReverseResultPage<E>(rp);
	}

	/**
	 * 合并结果集
	 * 
	 * @param arr
	 * @return
	 */
	@SafeVarargs
	public static <E> ResultPage<E> unite(ResultPage<E>... arr) {
		UniteResultPage<E> unite = UniteResultPage.getInstance();
		if (null != arr) {
			for (ResultPage<E> rp : arr) {
				unite.add(rp);
			}
		}
		return unite;
	}

	/**
	 * 将结果集转换成列表
	 * 
	 * @param rp
	 * @return
	 */
	public static <E> List<E> toList(ResultPage<E> rp) {
		if (rp instanceof ArrayResultPage) {
			ArrayResultPage<E> arp = (ArrayResultPage<E>) rp;
			return Arrays.asList(arp.toArray());
		}
		return new ResultPageList<E>(rp);
	}

	/**
	 * 返回空的结果集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> ResultPage<E> empty() {
		return (ResultPage<E>) EMPTY_RESULTPAGE;
	}

	static class EmptyResultPage<E> implements ResultPage<E>, Iterator<E> {

		@Override
		public Iterator<E> iterator() {
			return this;
		}

		@Override
		public boolean hasPrePage() {
			return false;
		}

		@Override
		public boolean prePage() {
			return false;
		}

		@Override
		public boolean hasNextPage() {
			return false;
		}

		@Override
		public boolean nextPage() {
			return false;
		}

		@Override
		public boolean firstPage() {
			return false;
		}

		@Override
		public boolean lastPage() {
			return false;
		}

		@Override
		public boolean gotoPage(int page) {
			return false;
		}

		@Override
		public int getPage() {
			return 1;
		}

		@Override
		public int getPageSum() {
			return 0;
		}

		@Override
		public void setPageSize(int size) {

		}

		@Override
		public int getPageSize() {
			return DEFAULT_PAGE_SIZE;
		}

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public E next() {
			return null;
		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E pre() {
			return null;
		}

		@Override
		public boolean hasPre() {
			return false;
		}

		@Override
		public void remove() {

		}

		@Override
		public String toString() {
			return "[]";
		}
	}
}
