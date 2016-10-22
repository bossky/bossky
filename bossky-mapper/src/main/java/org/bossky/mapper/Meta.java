package org.bossky.mapper;

import org.bossky.mapper.exception.MapperException;

/**
 * 元信息
 * 
 * @author daibo
 *
 */
public interface Meta {
	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public MetaType getType();

	/**
	 * 类型
	 * 
	 * @return
	 */
	public Class<?> getTypeClass();

	/**
	 * 获取对象对应的元信息值
	 * 
	 * @param obj
	 * @return
	 */
	public Object getValue(Object obj) throws MapperException;

	/**
	 * 设置对应对象的元信息值
	 * 
	 * @param obj
	 * @param value
	 */
	public void setValue(Object obj, Object value) throws MapperException;

}
