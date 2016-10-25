package org.bossky.common.support;

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

	/**
	 * 将结果集转换成列表
	 * 
	 * @param rp
	 * @return
	 */
	public static <E> List<E> toList(ResultPage<E> rp) {
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

	/** 空的分页结果 */
	protected final static ResultPage<Object> EMPTY_RESULTPAGE =new  AbstractResultPage<Object>(){

		@Override
		public Iterator<Object> iterator() {
			return this;
		}

		@Override
		public boolean gotoPage(int page) {
			return false;
		}

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object next() {
			return null;
		}

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public String toString() {
			return "[]";
		}

	};
}
