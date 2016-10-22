package org.bossky.common.util;

/**
 * 断言
 * 
 * @author daibo
 *
 */
public class Assert {

	public static String DEFAULT_ASSERT_NOT_NULL_TOP = "参数不能为空";

	/**
	 * 断言对象非空
	 * 
	 * @param obj
	 */
	public static void assertNotNull(Object obj) {
		assertNotNull(obj, DEFAULT_ASSERT_NOT_NULL_TOP);
	}

	/**
	 * 断言对象非空
	 * 
	 * @param obj
	 *            对象
	 * @param message
	 *            对象为空时的提示信息
	 */
	public static void assertNotNull(Object obj, String message) {
		if (null == obj) {
			throw new IllegalArgumentException(message);
		}
	}
}
