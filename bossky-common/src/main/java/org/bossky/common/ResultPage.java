package org.bossky.common;

/**
 * 分页结果集
 * 
 * @author daibo
 *
 */
public interface ResultPage<E> extends Iterable<E> {
	/** 默认的分页大小 */
	public static int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 跳转到指定页面
	 * 
	 * @param page
	 * @return
	 */
	public boolean gotoPage(int page);

	/**
	 * 当前页面数 范围 1-getPageSum();
	 * 
	 * @return
	 */
	public int getPage();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getPageSum();

	/**
	 * 设置分页大小
	 * 
	 * @param size
	 */
	public void setPageSize(int size);

	/**
	 * 一页大小
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 总数
	 * 
	 * @return
	 */
	public int getCount();

}
