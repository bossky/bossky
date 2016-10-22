package org.bossky.mapper.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.bossky.common.util.Misc;
import org.bossky.mapper.Mapper;
import org.bossky.mapper.Meta;
import org.bossky.mapper.exception.MapperException;
import org.bossky.mapper.support.AbstractMapper;

/**
 * 基于注释的映射器
 * 
 * @author bo
 *
 * @param <E>
 */
public class AnnotationMapper<E> extends AbstractMapper<E> implements Mapper<E> {
	/** 类 */
	protected Class<E> clazz;
	/** 构造函数 */
	protected Constructor<E> constructor;
	/** 构造参数 */
	protected Object[] initArgs;
	/** 元信息 */
	protected List<Meta> metas;

	protected AnnotationMapper(String name, Class<E> clazz, Object[] initArgs,
			List<Meta> metas) {
		super(name);
		this.clazz = clazz;
		this.constructor = getConstructor(clazz, initArgs);
		this.constructor.setAccessible(true);
		this.initArgs = initArgs;
		this.metas = metas;

	}

	/**
	 * 获取构造
	 * 
	 * @param clazz
	 * @param initargs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <E> Constructor<E> getConstructor(Class<E> clazz,
			Object... initargs) {
		if (Misc.isEmpty(initargs)) {
			try {
				return clazz.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				throw new MapperException(clazz + "没有无参构造函数", e);
			} catch (SecurityException e) {
				throw new MapperException(clazz + "安全异常", e);
			}
		}
		Class<?> parameterTypes[] = new Class<?>[initargs.length];
		for (int i = 0; i < initargs.length; i++) {
			if (null == initargs[i].getClass()) {
				throw new NullPointerException("初始化参数不能为null");
			}
			parameterTypes[i] = initargs[i].getClass();
		}
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		out: for (Constructor<?> c : constructors) {
			Class<?>[] classes = c.getParameterTypes();
			if (classes.length != parameterTypes.length) {
				continue;
			}
			for (int i = 0; i < classes.length; i++) {
				if (!classes[i].isAssignableFrom(parameterTypes[i])) {
					break out;
				}
			}
			return (Constructor<E>) c;
		}
		throw new MapperException(clazz + "没有对应的"
				+ Arrays.toString(parameterTypes) + "构造函数");
	}

	@Override
	public List<Meta> getMetas() {
		return metas;
	}

	@Override
	public E newInstance() {
		try {
			return constructor.newInstance(initArgs);
		} catch (InstantiationException e) {
			throw new MapperException("实例化异常", e);
		} catch (IllegalAccessException e) {
			throw new MapperException("访问异常", e);
		} catch (IllegalArgumentException e) {
			throw new MapperException("参数异常", e);
		} catch (InvocationTargetException e) {
			throw new MapperException("调整目标异常", e);
		}
	}

}
