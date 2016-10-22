package org.bossky.search;

import java.util.List;

/**
 * 搜索器
 * 
 * @author daibo
 *
 */
public interface Searcher {
	/** 选项-无 */
	public static long OPTION_NONE = 0;
	/** 选项-按评分正序排序 */
	public static long OPTION_SORT_BY_SCORE_ASC = 1 << 0;
	/** 选项-按评分倒序排序 */
	public static long OPTION_SORT_BY_SCORE_DESC = 1 << 1;

	/**
	 * 搜索器名
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 更新条目,建立索引 以entry为主键,entry相同会覆盖
	 * 
	 * @param entry
	 *            条目
	 * @param kewords
	 *            关键字列表
	 */
	public void updateEntry(IndexEntry entry, List<IndexKeyword> kewords);

	/**
	 * 移除条目
	 * 
	 * @param entry
	 */
	public void removeEntry(IndexEntry entry);

	/**
	 * 
	 * 按关键字获取对象,适用于一个或多个关键字唯一对应一个条目的情况
	 * 
	 * @param keywords
	 *            关键字
	 * @return
	 */
	public IndexResult get(String... keywords);

	/**
	 * 按关键字搜索
	 * 
	 * @param kewords
	 *            关键字
	 * @return
	 */
	public IndexResults search(String... kewords);

	/**
	 * 按关键字搜索
	 * 
	 * @param options
	 *            选项 {@link #OPTION_XXX}
	 * @param kewords
	 *            关键字
	 * @return
	 */
	public IndexResults search(long options, String... kewords);

	/**
	 * 按关键字搜索
	 * 
	 * @param options
	 *            选项 {@link #OPTION_XXX}
	 * @param keywords
	 *            关键字
	 * @return
	 */
	public IndexResults search(long options, List<QueryKeyword> keywords);

	/**
	 * 按关键字范围查询
	 * 
	 * @param begin
	 *            开始范围
	 * @param end
	 *            结束范围
	 * @return
	 */
	public IndexResults searchRange(String begin, String end);

	/**
	 * 按关键字范围查询
	 * 
	 * @param options
	 *            选项 {@link #OPTION_XXX}
	 * @param begin
	 *            开始范围
	 * @param end
	 *            结束范围
	 * @return
	 */
	public IndexResults searchRange(long options, String begin, String end);

	/**
	 * 按关键字范围查询
	 * 
	 * @param options
	 *            选项 {@link #OPTION_XXX}
	 * @param begin
	 *            开始范围
	 * @param end
	 *            结束范围
	 * @param keywords
	 *            关键字
	 * @return
	 */
	public IndexResults searchRange(long options, String begin, String end,
			List<QueryKeyword> keywords);
}
