package org.bossky.mapper;

import java.util.List;

/**
 * 映射器
 * 
 * @author bo
 *
 */
public interface Mapper<E> {
	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 元属性
	 * 
	 * @return
	 */
	public List<Meta> getMetas();

	/**
	 * 构造一个对象
	 * 
	 * @return
	 */
	public E newInstance();

}
