package org.bossky.search;

/**
 * 索引结果
 * 
 * @author daibo
 *
 */
public interface IndexResult {
	/**
	 * 主键
	 * 
	 * @return
	 */
	public String getKey();

	/**
	 * 评分
	 * 
	 * @return
	 */
	public long getScore();
}
