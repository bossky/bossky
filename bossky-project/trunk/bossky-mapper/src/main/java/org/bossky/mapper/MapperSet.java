package org.bossky.mapper;

/**
 * 映射器集合
 * 
 * @author bo
 *
 */
public interface MapperSet {
	/**
	 * 注册映射器
	 * 
	 * @param clazz
	 * @param mapper
	 */
	public void register(Class<?> clazz, Mapper<?> mapper);

	/**
	 * 注册映射器
	 * 
	 * @param name
	 * @param mapper
	 */
	public void register(String name, Mapper<?> mapper);

	/**
	 * 注销
	 * 
	 * @param clazz
	 */
	public void unregister(Class<?> clazz);

	/**
	 * 注销
	 * 
	 * @param name
	 */
	public void unregister(String name);

	/**
	 * 获取映射器
	 * 
	 * @param clazz
	 * @return
	 */
	public Mapper<?> getMapper(Class<?> clazz);

	/**
	 * 获取映射器
	 * 
	 * @param name
	 * @return
	 */
	public Mapper<?> getMapper(String name);

}
