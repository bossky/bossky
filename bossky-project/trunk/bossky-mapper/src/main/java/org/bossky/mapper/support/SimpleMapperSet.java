package org.bossky.mapper.support;

import java.util.HashMap;
import java.util.Map;

import org.bossky.mapper.Mapper;

/**
 * 简单的映射表实现
 * 
 * @author daibo
 *
 */
public class SimpleMapperSet extends AbstractMapperSet {
	/** 映射表 */
	protected Map<String, Mapper<?>> map;

	public SimpleMapperSet() {
		map = new HashMap<String, Mapper<?>>();
	}

	@Override
	public void register(String name, Mapper<?> mapper) {
		map.put(name, mapper);// 新的会覆盖旧的
	}

	@Override
	public void unregister(String name) {
		map.remove(name);
	}

	@Override
	public Mapper<?> getMapper(String name) {
		return map.get(name);
	}

}
