package org.bossky.bossky.store.sqlite;

import javax.annotation.Resource;

import org.bossky.store.support.AbstractStoreble;

public class ObjectTest extends AbstractStoreble<AssistantImpl> {
	@Resource
	protected BaseTest obj;
	@Resource
	protected String name;
	@Resource
	protected InternalObject internal;

	protected ObjectTest() {
		super(null);
	}

	protected ObjectTest(AssistantImpl assistant) {
		super(assistant);
		genId();
	}

	public void init() {
		name = "小天";
		BaseTest obj = new BaseTest();
		obj.init();
		this.obj = obj;
		InternalObject io = new InternalObject();
		io.name = "HaHa";
		this.internal = io;
	}

	public void flush() {
		super.flush();
	}

	static class InternalObject {
		@Resource
		public String name;
	}
}
