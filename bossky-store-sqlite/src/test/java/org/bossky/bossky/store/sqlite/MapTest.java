package org.bossky.bossky.store.sqlite;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.bossky.store.support.AbstractStoreble;

public class MapTest extends AbstractStoreble<AssistantImpl> {
	@Resource
	public Map<String, Integer> integers;

	protected MapTest(AssistantImpl assistant) {
		super(assistant);
		genId();
	}

	public void init() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Hello", 110);
		map.put("World", 120);
		integers = map;
	}

	public void flush() {
		super.flush();
	}

}
