package org.bossky.mapper.support;

import org.bossky.mapper.Meta;
import org.bossky.mapper.MetaType;

/**
 * 抽象元信息实现
 * 
 * @author bo
 *
 */
public abstract class AbstractMeta implements Meta {
	/** 名称 */
	protected String name;
	/** 类型 */
	protected MetaType type;

	public AbstractMeta(String name) {
		this.name = name;
		this.type = MetaType.UNKNOWN;
	}

	public AbstractMeta(String name, MetaType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public MetaType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		if (null == name) {
			return 0;
		}
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Meta) {
			Meta m = (Meta) obj;
			return null == name ? null == m.getName() : name
					.equals(m.getName());
		}
		return false;
	}
}
