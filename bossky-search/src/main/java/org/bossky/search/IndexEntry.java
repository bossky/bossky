package org.bossky.search;

/**
 * 索引条目
 * 
 * @author daibo
 *
 */
public interface IndexEntry {
	/**
	 * 主键
	 * 
	 * @return
	 */
	public String getKey();

	/**
	 * 评分,影响排序
	 * 
	 * @return
	 */
	public long getScore();
}
