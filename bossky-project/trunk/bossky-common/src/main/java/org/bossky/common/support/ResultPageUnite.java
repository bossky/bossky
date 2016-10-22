package org.bossky.common.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bossky.common.ResultPage;

/**
 * 联合结果集
 * 
 * @author bo
 *
 */
public class ResultPageUnite<E> extends AbstractResultPage<E> {
	List<ResultPage<E>> resultPages;
	int current;
	int count;
	int start;
	int end;
	int cursor;

	public ResultPageUnite() {
		resultPages = new ArrayList<ResultPage<E>>();

	}

	public static <E> ResultPageUnite<E> getInstance() {
		ResultPageUnite<E> unite = new ResultPageUnite<E>();
		return unite;
	}

	public void add(ResultPage<E> rp) {
		if (rp.getCount() > 0) {
			rp.setPageSize(getPageSize());
			resultPages.add(rp);
			count += rp.getCount();
		}
	}

	@Override
	public void setPageSize(int size) {
		super.setPageSize(size);
		for (ResultPage<E> rp : resultPages) {
			rp.setPageSize(size);
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public boolean gotoPage(int page) {
		int index = (page - 1) * getPageSize();
		int count = 0;
		for (int i = 0; i < resultPages.size(); i++) {
			ResultPage<E> rp = resultPages.get(i);
			current = i;
			if (count + rp.getCount() > index) {
				int offsetindex = index - count;
				int offsetpage = (offsetindex / pageSize) + 1;
				int offset = offsetindex % pageSize;
				if (rp.gotoPage(offsetpage)) {
					for (int j = 0; j < offset; j++) {
						rp.next();
					}
				}
				start = index;
				cursor = start;
				end = start + getPageSize();
				end = end < getCount() ? end : getCount();
				this.page = page;
				return true;
			} else {
				count += rp.getCount();
			}
		}
		return false;
	}

	@Override
	public E next() {
		if (hasNext()) {
			cursor++;
			ResultPage<E> rp = resultPages.get(current);
			if (rp.hasNext()) {
				return rp.next();
			}
			if (rp.hasNextPage()) {
				rp.nextPage();
				return rp.next();
			}
			if (current + 1 < resultPages.size()) {
				current++;
				rp = resultPages.get(current);
				rp.gotoPage(1);
				return rp.next();
			}
		}
		return null;
	}

	@Override
	public boolean hasNext() {
		return cursor < end;
	}

	@Override
	public E pre() {
		if (hasPre()) {
			cursor--;
			ResultPage<E> rp = resultPages.get(current);
			if (rp.hasPre()) {
				return rp.pre();
			}
			if (rp.hasPre()) {
				rp.prePage();
				return rp.pre();
			}
			if (current > 0) {
				current--;
				rp = resultPages.get(current);
				rp.lastPage();
				while (rp.hasNext()) {
					rp.next();
				}
				return rp.pre();
			}
		}
		return null;
	}

	@Override
	public boolean hasPre() {
		return cursor > start;
	}

	@Override
	public Iterator<E> iterator() {
		return this;
	}

}
