package org.bossky.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 元信息类型
 * 
 * @author bo
 *
 */
public enum MetaType {
	BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, CHARACTER, STRING, DATE, OBJECT, ARRAY, SET, MAP, LIST, UNKNOWN;

	/**
	 * 获取类对应的数据类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static MetaType valueOf(Class<?> clazz) {
		if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
			return BYTE;
		} else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
			return SHORT;
		}
		if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
			return INTEGER;
		}
		if (clazz.equals(Long.class) || clazz.equals(long.class)) {
			return LONG;
		}
		if (clazz.equals(Float.class) || clazz.equals(float.class)) {
			return FLOAT;
		}
		if (clazz.equals(Double.class) || clazz.equals(double.class)) {
			return DOUBLE;
		}
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
			return BOOLEAN;
		}
		if (clazz.equals(Character.class) || clazz.equals(char.class)) {
			return CHARACTER;
		}
		if (clazz.equals(String.class) || clazz.equals(CharSequence.class)) {
			return STRING;
		}
		if (Date.class.isAssignableFrom(clazz)) {
			return DATE;
		}
		// 多元类型
		if (clazz.isArray()) {
			return ARRAY;
		}
		if (Set.class.isAssignableFrom(clazz)) {
			return SET;
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return MAP;
		}
		if (List.class.isAssignableFrom(clazz)) {
			return LIST;
		}
		return OBJECT;
	}
}
