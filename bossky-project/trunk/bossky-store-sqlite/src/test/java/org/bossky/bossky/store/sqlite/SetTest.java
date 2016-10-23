package org.bossky.bossky.store.sqlite;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.bossky.store.support.AbstractStoreble;

public class SetTest extends AbstractStoreble<AssistantImpl> {
	@Resource
	public Set<Integer> integers;
	@Resource
	public Set<BaseTest> objs;

	protected SetTest(AssistantImpl assistant) {
		super(assistant);
		genId();
	}

	public void init() {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(3);
		set.add(5);
		integers = set;
		Set<BaseTest> obj = new HashSet<BaseTest>();
		BaseTest e = new BaseTest();
		e.init();
		obj.add(e);
		objs = obj;
	}

	public void flush() {
		super.flush();
	}

}
