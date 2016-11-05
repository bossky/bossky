package org.bossky.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 平常的工具类
 * 
 * @author bo
 *
 */
public class Misc {
	/** 记录日志 */
	final static Logger _Logger = LoggerFactory.getLogger(Misc.class);
	/** 随机数生成器 */
	static final Random _Random = new Random();
	/** 定时器 */
	static final Timer _Timer = new Timer(Misc.class.getSimpleName() + "_Timer", true);

	static {
		_Timer.schedule(new TimerTask() {

			@Override
			public void run() {
				_Random.setSeed(System.currentTimeMillis());// 每隔一段时间重置一下随机数
			}
		}, 30 * 60 * 1000, 30 * 60 * 1000);
	}

	/**
	 * 将字串转换成short，转换失败返回0
	 * 
	 * @param var
	 * @return
	 */
	public static short toShort(String var) {
		return toShort(var, (short) 0);
	}

	/**
	 * 将字串转换成short，转换失败返回i
	 * 
	 * @param var
	 * @param i
	 * @return
	 */
	public static short toShort(String var, short i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			return Short.parseShort(var);
		} catch (Exception e) {
			_Logger.warn("将[" + var + "]转换成short时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static int toInt(String var) {
		return toInt(var, 0);
	}

	/**
	 * 将字符串转换成整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static int toInt(String var, int i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			return Integer.parseInt(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成int时出错", e);
		}
		return i;
	}

	/**
	 * 将16进制的字符串转换成整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static int toInt16(String var) {
		return toInt16(var, 0);
	}

	/**
	 * 将16进制的字符串转换成整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static int toInt16(String var, int i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			char c1 = var.charAt(0);
			char c2 = var.charAt(1);
			if (c1 == '0' && (c2 == 'x' || c2 == 'X')) {
				var = var.substring(2);
			}
			return Integer.parseInt(var, 16);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成int时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成长整数,转换失败返回0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static long toLong(String var) {
		return toLong(var, 0);
	}

	/**
	 * 将字符串转换成长整数
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static long toLong(String var, long i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			return Long.parseLong(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成long时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成float,转换失败返回的0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static float toFloat(String var) {
		return toFloat(var, 0);
	}

	/**
	 * 将字符串转换成float,转换失败返回的i
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static float toFloat(String var, float i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			return Float.parseFloat(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成float时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成double,转换失败返回的0
	 * 
	 * @param var
	 *            要转换的字符串
	 * @return
	 */
	public static double toDouble(String var) {
		return toDouble(var, 0);
	}

	/**
	 * 将字符串转换成double,转换失败返回的i
	 * 
	 * @param var
	 *            要转换的字符串
	 * @param i
	 *            转换失败返回的默认值
	 * @return
	 */
	public static double toDouble(String var, double i) {
		if (isEmpty(var)) {
			return i;
		}
		try {
			return Double.parseDouble(var);
		} catch (Throwable e) {
			_Logger.warn("将[" + var + "]转换成double时出错", e);
		}
		return i;
	}

	/**
	 * 将字符串转换成布尔值
	 * 
	 * @param val
	 * @return
	 */
	public static boolean toBoolean(String val) {
		return toBoolean(val, false);
	}

	/**
	 * 将字符串转换成布尔值
	 * 
	 * @param var
	 * @param defaultVal
	 * @return
	 */
	public static boolean toBoolean(String var, boolean defaultVal) {
		if (null == var) {
			return defaultVal;
		}
		var = var.trim();
		if ("true".equalsIgnoreCase(var)) {
			return true;
		}
		if ("false".equalsIgnoreCase(var)) {
			return false;
		}
		return defaultVal;
	}

	/**
	 * 将对象转换成字符串,对象为null时返回""
	 * 
	 * @param var
	 * @return
	 */
	public static String toString(Object var) {
		return toString(var, "");
	}

	/**
	 * 将对象转换成字符串,对象为null时返回defaultvar
	 * 
	 * @param var
	 * @param defaultvar
	 * @return
	 */
	public static String toString(Object var, String defaultvar) {
		return var == null ? defaultvar : var.toString().trim();
	}

	/**
	 * 是字符是否为空串，全空格字串符也算空串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isEmpty(String var) {
		return var == null ? true : var.trim().length() == 0 ? true : false;
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param arr
	 * @return
	 */
	public static <E> boolean isEmpty(E[] arr) {
		return null == arr ? true : arr.length == 0 ? true : false;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <E> boolean isEmpty(Collection<E> collection) {
		return null == collection ? true : collection.size() == 0 ? true : false;

	}

	/**
	 * 是否为数字组成的字符串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isNumber(String var) {
		// 0-9对应的码 48 49 50 51 52 53 54 55 56 57
		if (Misc.isEmpty(var)) {
			return false;
		}
		for (int i = 0; i < var.length(); i++) {
			int v = var.charAt(i);
			if (v < 48 || v > 57) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为字母组成的字符串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isCase(String var) {
		if (Misc.isEmpty(var)) {
			return false;
		}
		for (int i = 0; i < var.length(); i++) {
			char v = var.charAt(i);
			if ((v < 'a' || v > 'z') && (v < 'A' || v > 'Z')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为小写字母组成的字符串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isLowerCase(String var) {
		if (Misc.isEmpty(var)) {
			return false;
		}
		for (int i = 0; i < var.length(); i++) {
			char v = var.charAt(i);
			if (v < 'a' || v > 'z') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为大写字母组成的字符串
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isUpperCase(String var) {
		if (Misc.isEmpty(var)) {
			return false;
		}
		for (int i = 0; i < var.length(); i++) {
			char v = var.charAt(i);
			if (v < 'A' || v > 'Z') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为手机号
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isMobile(String var) {
		if (null == var) {
			return false;
		}
		if (var.length() != 11) {
			return false;
		}
		char c = var.charAt(0);
		if (c != '1') {
			return false;
		}
		for (int i = 1; i < var.length(); i++) {
			char v = var.charAt(i);
			if (v < '0' || v > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为邮箱
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isEmail(String var) {
		if (null == var) {
			return false;
		}
		int atIndex = -1;
		int i;
		for (i = 0; i < var.length(); i++) {
			char v = var.charAt(i);
			if (v == '@') {
				atIndex = i;
				break;
			}
			// 邮箱的前部分只能用 数字、字母、下划线组成
			if ((v != '_') && (v < '0' || v > '9') && (v < 'a' || v > 'z') && (v < 'A' || v > 'Z')) {
				return false;
			}
		}
		if (atIndex == -1 || atIndex == var.length()) {// 没有@符号或者@在最后
			return false;
		}
		for (i=i+1; i < var.length(); i++) {
			char v = var.charAt(i);
			// 邮箱的前部分只能用 数字、字母、下划线组成
			if ((v != '.') && (v != '_') && (v < '0' || v > '9') && (v < 'a' || v > 'z') && (v < 'A' || v > 'Z')) {
				return false;
			}
		}
		return true;
	}

	/** MD5字典 */
	static MessageDigest MD5;

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// 不可能发生
			_Logger.error("无法实例化MD5", e);
		}
	}

	/**
	 * md5
	 * 
	 * @param var
	 * @return
	 */
	public static String MD5(String var) {
		byte[] b;
		synchronized (MD5) {
			MD5.update(var.getBytes());
			b = MD5.digest();
		}
		int i;
		StringBuilder buf = new StringBuilder("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append('0');
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * 转换成16进制的
	 * 
	 * @param i
	 * @return
	 */
	public static String toHex(int i) {
		return Integer.toHexString(i);
	}

	/**
	 * 转换成16进制的
	 * 
	 * @param i
	 * @return
	 */
	public static String toHex(long i) {
		return Long.toHexString(i);
	}

	/**
	 * 比较两个字符串是否相等
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eq(String var, String otherval) {
		if (var == null) {
			return otherval == null;
		}
		return var.equals(otherval);
	}

	/**
	 * 比较两个字符串是否相等,忽略大小写
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eqIgnoreCase(String var, String otherval) {
		if (var == null) {
			if (otherval == null) {
				return true;
			} else {
				return false;
			}
		}
		if (otherval == null) {
			return false;
		}
		return var.equalsIgnoreCase(otherval);
	}

	/**
	 * 比较两个字符串是否相等，忽略前后的空格
	 * 
	 * @param var
	 * @param otherval
	 * @return
	 */
	public static boolean eqIgnoreBlank(String var, String otherval) {
		if (var == null) {
			if (otherval == null) {
				return true;
			} else {
				return false;
			}
		}
		if (otherval == null) {
			return false;
		}
		int l1 = var.length();
		int l2 = otherval.length();
		int i1 = -1;
		int i2 = -1;
		char c1;
		char c2;
		do {
			i1++;
			c1 = var.charAt(i1);
		} while (c1 == ' ' && i1 < l1);
		do {
			i2++;
			c2 = otherval.charAt(i2);
		} while (c2 == ' ' && i2 < l2);

		while (i1 < l1 && i2 < l2) {
			if (i1 == l1) {
				while (i2 < l2) {
					c2 = otherval.charAt(i2);
					if (c2 != ' ') {
						return false;
					}
				}
			} else if (i2 == l2) {
				while (i1 < l1) {
					c1 = var.charAt(i1);
					if (c1 != ' ') {
						return false;
					}
				}
			} else {
				c1 = var.charAt(i1++);
				c2 = otherval.charAt(i2++);
				if (c1 != c2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param obj
	 * @param otherObj
	 * @return
	 */
	public static boolean eq(Object[] obj, Object[] otherObj) {
		if (null == obj) {
			return null == otherObj;
		}
		if (null == otherObj) {
			return false;
		}
		if (obj.length != otherObj.length) {
			return false;
		}
		for (int i = 0; i < obj.length; i++) {
			if (!Misc.eq(obj[i], otherObj[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param obj
	 * @param otherObj
	 * @return
	 */
	public static boolean eq(Object obj, Object otherObj) {
		if (null == obj) {
			return null == otherObj;
		}
		return null == otherObj ? false : obj.equals(otherObj);
	}

	/**
	 * 空数组
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] emptyArray(Class<E> clazz) {
		return (E[]) Array.newInstance(clazz, 0);
	}

	/**
	 * 关闭对象
	 * 
	 * @param obj
	 */
	public static void close(Object obj) {
		if (obj instanceof Cloneable) {
			Closeable able = (Closeable) obj;
			try {
				able.close();
			} catch (IOException e) {
				if (_Logger.isTraceEnabled()) {
					_Logger.trace("关闭" + able + "出错", e);
				}
			}
		}
		if (obj instanceof AutoCloseable) {
			AutoCloseable able = (AutoCloseable) obj;
			try {
				able.close();
			} catch (Exception e) {
				if (_Logger.isTraceEnabled()) {
					_Logger.trace("关闭" + able + "出错", e);
				}
			}
		}
	}

	/** 数字表 */
	private static String NUMBER_TABLES = "0123456789";

	/**
	 * 生成指定长度的随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String randomNumber(int length) {
		// 随机由table中取得字符
		char[] chars = new char[length];
		// Random random = new Random();
		for (int i = 0; i < length; i++) {
			chars[i] = NUMBER_TABLES.charAt(_Random.nextInt(NUMBER_TABLES.length()));
		}
		return new String(chars);
	}

	/**
	 * 生成指定长度的随机数
	 * 
	 * @param length
	 *            随机数的长度
	 * @param table
	 *            要生成随机数的表
	 * @return
	 */
	public static String random(int length, String table) {
		// 随机由table中取得字符
		char[] chars = new char[length];
		// Random random = new Random();
		for (int i = 0; i < length; i++) {
			chars[i] = table.charAt(_Random.nextInt(table.length()));
		}
		return new String(chars);
	}

	public static void main(String[] args) {
		System.out.println("az-Z".toLowerCase());
	}
}