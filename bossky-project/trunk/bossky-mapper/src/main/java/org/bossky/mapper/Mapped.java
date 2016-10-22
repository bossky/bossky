package org.bossky.mapper;

import java.util.Date;

/**
 * 映射
 * 
 * @author bo
 *
 */
public interface Mapped {
	/**
	 * 创建子映射
	 * 
	 * @return
	 */
	public Mapped createChildMapped();

	/**
	 * 创建子映射集
	 * 
	 * @return
	 */
	public Mappeds createChildMappeds();

	/**
	 * 是否存在值
	 * 
	 * @param key
	 * @return
	 */
	public boolean isNull(String key);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Boolean value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Short value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Integer value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Long value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Float value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Double value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Date value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Mapped value);

	/**
	 * 存入值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Mappeds value);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Boolean getBoolean(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Short getShort(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Integer getInteger(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Long getLong(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Float getFloat(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Double getDouble(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public String getString(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 */
	public Date getDate(String key);

	/**
	 * 获取映射
	 * 
	 * @param key
	 */
	public Mapped getMapped(String key);

	/**
	 * 获取映射集
	 * 
	 * @param key
	 */
	public Mappeds getMappeds(String key);
}
