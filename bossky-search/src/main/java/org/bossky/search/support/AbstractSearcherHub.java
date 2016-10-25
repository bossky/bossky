package org.bossky.search.support;

import java.util.HashMap;
import java.util.Map;

import org.bossky.search.Searcher;
import org.bossky.search.SearcherHub;

/**
 * 搜索器集合抽象类
 * 
 * @author bo
 *
 */
public abstract class AbstractSearcherHub implements SearcherHub {

	protected Map<String, Searcher> map;

	public AbstractSearcherHub() {
		map = new HashMap<String, Searcher>();
	}

	@Override
	public Searcher openSearcher(String name) {
		Searcher s = map.get(name);
		if (null != s) {
			return s;
		}
		// 创建过程可能有点久
		s = createSearcher(name);

		synchronized (map) {
			Searcher other = map.get(name);
			if (null != other) {
				return other;// 被人抢先了,用别人的吧
			}
			// 哈哈,我的了
			map.put(name, s);
			return s;
		}

	}

	@Override
	public Searcher getSearcher(String name) {
		return map.get(name);
	}

	/**
	 * 创建一个搜索器
	 * 
	 * @param name
	 * @return
	 */
	protected abstract Searcher createSearcher(String name);

}
