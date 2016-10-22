package org.bossky.common.support;

import java.util.AbstractList;

import org.bossky.common.ResultPage;

/**
 * 数组分页结果
 * 
 * @author bo
 *
 */
public class ResultPageList<E> extends AbstractList<E> {
	ResultPage<E> rp;
	Object[] arr;
	int[] convered;

	ResultPageList(ResultPage<E> rp) {
		this.rp = rp;
		arr = new Object[rp.getCount()];
		convered = new int[rp.getPageSum()];
		conver(1);// 转化第１页
	}

	public static <E> ResultPageList<E> wrap(ResultPage<E> rp) {
		return new ResultPageList<E>(rp);
	}

	protected void conver(int page) {
		if (rp.gotoPage(page)) {
			// 检查是否已经转换过了
			if (convered[page - 1] != 1) {
				convered[page - 1] = 1;
			} else {
				return;
			}
			int index = (page - 1) * rp.getPageSize();
			for (E e : rp) {
				arr[index++] = e;
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size());
		}
		Object result = arr[index];
		if (null == result) {
			int num = index + 1;
			int page = num / rp.getPageSize()
					+ (num % rp.getPageSize() == 0 ? 0 : 1);
			conver(page);
			result = arr[index];
		}
		return (E) result;
	}

	@Override
	public int size() {
		return arr.length;
	}
}
