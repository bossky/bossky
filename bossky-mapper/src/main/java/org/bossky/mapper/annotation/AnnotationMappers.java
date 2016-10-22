package org.bossky.mapper.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bossky.common.util.Misc;
import org.bossky.mapper.Mapper;
import org.bossky.mapper.Meta;

/**
 * 基于注释的映射器
 * 
 * @author bo
 *
 * @param <E>
 */
public class AnnotationMappers {

	private AnnotationMappers() {

	}

	/**
	 * 构造一个映射器
	 * 
	 * @param clazz
	 *            指定类
	 * @param initargs
	 *            构造函数的参数,不能为null
	 * @return
	 */
	public static <E> Mapper<E> valueOf(Class<E> clazz, Object... initargs) {
		// 映射
		MapperClass mapper = clazz.getAnnotation(MapperClass.class);
		String name = clazz.getSimpleName();// 名称
		boolean isSubclass = null != clazz.getAnnotation(Subclass.class);// 是否子类
		if (null != mapper) {
			if (!Misc.isEmpty(mapper.name())) {
				name = mapper.name();
			}
			isSubclass = mapper.subclass();
		}
		List<Meta> metas;
		if (isSubclass) {
			metas = getLoopMeta(clazz);
		} else {
			metas = getMeta(clazz);
		}
		return new AnnotationMapper<E>(name, clazz, initargs, metas);
	}

	/**
	 * 循环获取多个类的信息,直到类非子类为止
	 * 
	 * @param clazz
	 * @return
	 */
	private static <E> List<Meta> getLoopMeta(Class<E> clazz) {
		List<Meta> metas = new ArrayList<Meta>();
		Class<?> loop = clazz;
		boolean isSubclass;
		do {
			metas.addAll(getMeta(clazz));
			// 当前类是不是子类
			isSubclass = null != loop.getAnnotation(Subclass.class);
			// 优先使用mapperclass注释
			MapperClass mapper = loop.getAnnotation(MapperClass.class);
			if (null != mapper) {
				isSubclass = mapper.subclass();
			}
			loop = clazz.getSuperclass();
		} while (isSubclass);
		return metas;
	}

	/**
	 * 获取当前类的元信息
	 * 
	 * @param clazz
	 * @return
	 */
	private static <E> List<Meta> getMeta(Class<E> clazz) {
		List<Meta> metas = new ArrayList<Meta>();
		for (Field f : clazz.getDeclaredFields()) {
			Resource resource = f.getAnnotation(Resource.class);
			MetaField metaField = f.getAnnotation(MetaField.class);
			if (null == resource && null == metaField) {
				continue;
			}
			metas.add(AnnotationMetas.valueOf(f));
		}
		return metas;
	}

}
