package org.bossky.common.util;

/**
 * 断言
 * 
 * @author daibo
 *
 */
public class AssertUtil {

	private static String DEFAULT_ASSERT_NOT_NULL_TOP = "参数不能为空";
	private static String DEFAULT_ASSERT_NULL_TOP = "参数必须为空";
	private static String DEFAULT_ASSERT_EQ = "对象必须相等";
	private static String DEFAULT_ASSERT_NOT_EQ = "对象必须不相等";

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

	/**
	 * 断言对象是空
	 * 
	 * @param obj
	 */
	public static void assertNull(Object obj) {
		assertNull(obj, DEFAULT_ASSERT_NULL_TOP);
	}

	/**
	 * 断言对象是空
	 * 
	 * @param obj
	 */
	public static void assertNull(Object obj, String message) {
		if (null == obj) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言对象相等
	 * 
	 * @param obj
	 * @param other
	 */
	public static void assertEq(Object obj, Object other) {
		assertEq(obj, other, DEFAULT_ASSERT_EQ);
	}

	/**
	 * 断言对象相等
	 * 
	 * @param obj
	 * @param other
	 */
	public static void assertEq(Object obj, Object other, String message) {
		if (!Misc.eq(obj, other)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言对象不相等
	 * 
	 * @param obj
	 * @param other
	 */
	public static void assertNotEq(Object obj, Object other) {
		assertEq(obj, other, DEFAULT_ASSERT_NOT_EQ);
	}

	/**
	 * 断言对象不相等
	 * 
	 * @param obj
	 * @param other
	 */
	public static void assertNotEq(Object obj, Object other, String message) {
		if (!Misc.eq(obj, other)) {
			throw new IllegalArgumentException(message);
		}
	}

}
