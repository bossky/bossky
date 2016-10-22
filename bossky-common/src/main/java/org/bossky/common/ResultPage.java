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
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean hasPrePage();

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public boolean prePage();

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean hasNextPage();

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public boolean nextPage();

	/**
	 * 最前页
	 * 
	 * @return
	 */
	public boolean firstPage();

	/**
	 * 最后页
	 * 
	 * @return
	 */
	public boolean lastPage();

	/**
	 * 跳转到指定页面
	 * 
	 * @param page
	 * @return
	 */
	public boolean gotoPage(int page);

	/**
	 * 当前页面数 范围　1-getPageSum();
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

	/**
	 * 获取下一个对象，相对于当前页
	 * 
	 * @return
	 */
	public E next();

	/**
	 * 是否有下一个，相对于当前页
	 * 
	 * @return
	 */
	public boolean hasNext();

	/**
	 * 获取上一个对象，相对于当前页
	 * 
	 * @return
	 */
	public E pre();

	/**
	 * 是否有前一个，相对于当前页
	 * 
	 * @return
	 */
	public boolean hasPre();
}
