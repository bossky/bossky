package org.bossky.search;

/**
 * 查询关键字
 * 
 * @author bo
 *
 */
public interface QueryKeyword {
	/** 条目的前缀匹配 */
	public static int TYPE_ENTRY_PREFIX = 1 << 0;
	/** 关键字完全匹配 */
	public static int TYPE_KEYWORD_ALL = 1 << 1;

	/**
	 * 类型
	 * 
	 * @return
	 */
	public int getType();

	/**
	 * 值
	 * 
	 * @return
	 */
	public String getValue();
}
