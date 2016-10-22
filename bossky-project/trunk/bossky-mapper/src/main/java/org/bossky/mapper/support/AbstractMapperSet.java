package org.bossky.mapper.support;

import org.bossky.mapper.Mapper;
import org.bossky.mapper.MapperSet;
import org.bossky.mapper.annotation.AnnotationMappers;

/**
 * 抽象映射器集合
 * 
 * @author bo
 *
 */
public abstract class AbstractMapperSet implements MapperSet {
	/**
	 * 注册映射表，默认使用映射器类名注册
	 * 
	 * @param mapper
	 */
	public void register(Mapper<?> mapper) {
		if (null == mapper) {
			throw new NullPointerException("映射器不能为空");
		}
		register(mapper.getName(), mapper);
	}

	/**
	 * 注册映射表,默认使用AnnotationMapper映射器
	 * 
	 * @param clazz
	 */
	public Mapper<?> register(Class<?> clazz) {
		if (null == clazz) {
			throw new IllegalArgumentException("类不能为空");
		}
		Mapper<?> mapper = AnnotationMappers.valueOf(clazz);
		register(getLongName(clazz), mapper);
		register(getSimpleName(clazz), mapper);
		return mapper;
	}

	@Override
	public void register(Class<?> clazz, Mapper<?> mapper) {
		if (null == clazz) {
			throw new IllegalArgumentException("类不能为空");
		}
		register(getLongName(clazz), mapper);
		register(mapper.getName(), mapper);
	}

	/**
	 * 注销
	 * 
	 * @param clazz
	 */
	public void unregister(Class<?> clazz) {
		unregister(clazz.getName());
	}

	@Override
	public Mapper<?> getMapper(Class<?> clazz) {
		if (null == clazz) {
			throw new IllegalArgumentException("类不能为空");
		}
		Mapper<?> m = getMapper(getLongName(clazz));
		if (null == m) {
			return getMapper(getSimpleName(clazz));
		}
		return m;
	}

	public static String getSimpleName(Class<?> clazz) {
		return clazz.getSimpleName();
	}

	public static String getLongName(Class<?> clazz) {
		return clazz.getName();
	}
}
