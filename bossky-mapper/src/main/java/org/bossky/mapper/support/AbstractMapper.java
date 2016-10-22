package org.bossky.mapper.support;

import org.bossky.mapper.Mapper;

/**
 * 抽象的映射器
 * 
 * @author bo
 *
 * @param <E>
 */
public abstract class AbstractMapper<E> implements Mapper<E> {
	/** 名称 */
	protected String name;

	public AbstractMapper(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Mapper) {
			Mapper<?> m = (Mapper<?>) obj;
			return null == name ? null == m.getName() : name
					.equals(m.getName());
		}
		return false;
	}
	
	
}
