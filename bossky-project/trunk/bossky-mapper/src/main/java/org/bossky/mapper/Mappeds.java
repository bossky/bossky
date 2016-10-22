package org.bossky.mapper;

import java.util.Date;

public interface Mappeds {

	public void putNull();

	public void put(Boolean value);

	public void put(Short value);

	public void put(Integer value);

	public void put(Long value);

	public void put(Float value);

	public void put(Double value);

	public void put(String value);

	public void put(Date date);

	public void put(Mapped mapped);

	public void put(Mappeds mappeds);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Boolean getBoolean(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Short getShort(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Integer getInteger(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Long getLong(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Float getFloat(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Double getDouble(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public String getString(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Date getDate(int index);

	/**
	 * 获取值
	 * 
	 * @param index
	 */
	public Mapped getMapped(int index);

	/**
	 * 映射表大小
	 * 
	 * @return
	 */
	public int size();

}
