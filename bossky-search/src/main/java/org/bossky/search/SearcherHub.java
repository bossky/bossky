package org.bossky.search;

/**
 * 搜索器集合
 * 
 * @author bo
 *
 */
public interface SearcherHub {
	/**
	 * 打开一个搜索器，没有会创建
	 * 
	 * @param name
	 *            搜索器名
	 * @return
	 */
	public Searcher openSearcher(String name);

	/**
	 * 获取一个搜索器
	 * 
	 * @param name
	 * @return
	 */
	public Searcher getSearcher(String name);
}
