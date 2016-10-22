package org.bossky.common;

/**
 * 一对
 * 
 * @author bo
 *
 */
public class Pair<K, V> {
	/** 键 */
	protected K key;
	/** 值 */
	protected V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

}
