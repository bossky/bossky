package org.bossky.bossky.store.sqlite;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.bossky.store.support.AbstractStoreble;

public class ListTest extends AbstractStoreble<AssistantImpl> {
	@Resource
	public List<List<Integer>> integers;

	protected ListTest(AssistantImpl assistant) {
		super(assistant);
		genId();
	}

	public void init() {
		integers = Arrays.asList(Arrays.asList(1, null, 0),
				Arrays.asList(1, 1, 0));
	}

	public void flush() {
		super.flush();
	}

}
