package org.bossky.mapper.annotation;

import java.lang.reflect.Field;

import org.bossky.mapper.MetaType;

/**
 * 基于注释的元信息
 * 
 * @author bo
 *
 */
public class AnnotationMetas {

	private AnnotationMetas() {
	}

	/**
	 * 构造元信息
	 * 
	 * @param name
	 * @param field
	 * @return
	 */
	public static AnnotationMeta valueOf(Field field) {
		String name = field.getName();
		Class<?> clazz = field.getType();
		MetaType type = MetaType.valueOf(clazz);
		return new AnnotationMeta(name, type, field);
	}
}
