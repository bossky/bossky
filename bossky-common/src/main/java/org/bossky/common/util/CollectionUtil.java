package org.bossky.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集合工具类
 * 
 * @author daibo
 *
 */
public class CollectionUtil {
	/**
	 * 安全的添加列表
	 * 
	 * @param original
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> safeAdd(List<E> original, E e) {
		List<E> list = original;
		if (null == list || list.isEmpty()) {
			return Collections.singletonList(e);
		}
		ArrayList<Object> newList = new ArrayList<Object>();
		for (E v : list) {
			if (Misc.eq(v, e)) {
				continue;
			}
			newList.add(v);
		}
		newList.add(e);
		return (List<E>) newList;
	}

	/**
	 * 安全的移除列表
	 * 
	 * @param original
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> safeRemove(List<E> original, E e) {
		List<E> list = original;
		ArrayList<Object> newList = new ArrayList<Object>();
		for (E v : list) {
			if (Misc.eq(v, e)) {
				continue;
			}
			newList.add(v);
		}
		return (List<E>) newList;
	}
}
