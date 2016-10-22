package org.bossky.mapper.annotation;

import java.lang.reflect.Field;

import org.bossky.mapper.Meta;
import org.bossky.mapper.MetaType;
import org.bossky.mapper.exception.MapperException;
import org.bossky.mapper.support.AbstractMeta;

/**
 * 基于注释的元信息
 * 
 * @author bo
 *
 */
public class AnnotationMeta extends AbstractMeta implements Meta {
	/** 属性 */
	protected Field field;

	protected AnnotationMeta(String name, MetaType type, Field field) {
		super(name, type);
		this.field = field;
		this.field.setAccessible(true);
	}

	public Class<?> getTypeClass() {
		return field.getType();
	}

	@Override
	public Object getValue(Object obj) throws MapperException {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new MapperException("参数异常", e);
		} catch (IllegalAccessException e) {
			throw new MapperException("参数访问", e);
		}
	}

	@Override
	public void setValue(Object obj, Object value) throws MapperException {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			throw new MapperException("参数异常", e);
		} catch (IllegalAccessException e) {
			throw new MapperException("访问异常", e);
		}
	}

}
