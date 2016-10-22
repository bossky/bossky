package org.bossky.bossky.store.sqlite;

import org.bossky.store.Assistant;
import org.bossky.store.StoreHub;

public class AssistantImpl implements Assistant {
	StoreHub hub;

	public AssistantImpl(StoreHub hub) {
		this.hub = hub;
	}

	@Override
	public StoreHub getStoreHub() {
		return hub;
	}

}
