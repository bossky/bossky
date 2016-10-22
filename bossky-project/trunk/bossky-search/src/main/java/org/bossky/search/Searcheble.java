package org.bossky.search;

import java.util.List;

/**
 * 可搜索类
 * 
 * @author daibo
 *
 */
public interface Searcheble {
	/**
	 * 关键字
	 * 
	 * @return
	 */
	public List<IndexKeyword> getIndexKeywords();

	/**
	 * 构建索引
	 */
	public void buildIndex();
}
