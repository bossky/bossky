package org.bossky.common;

import org.bossky.common.util.Misc;

/**
 * 时间戳生成类
 * 
 * @author bo
 *
 */
public class Timestamp {

	private static Object decimalLock = new Object();
	private static Object hexLock = new Object();

	/**
	 * 生成10进制的时间戳
	 * 
	 * @return
	 */
	public static String next() {
		synchronized (decimalLock) {
			return System.currentTimeMillis() + "";
		}
	}

	/**
	 * 生成16进制的时间戳
	 * 
	 * @return
	 */
	public static String nextHex() {
		synchronized (hexLock) {
			return Misc.toHex(System.currentTimeMillis());
		}
	}
}
