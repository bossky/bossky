package org.bossky.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO工具类
 * 
 * @author daibo
 *
 */
public class IoUtil {
	/** 日志记录器 */
	final static Logger _Logger = LoggerFactory.getLogger(IoUtil.class);
	/** 用户目录 */
	protected static final File USER_DIR = new File(System.getProperty("user.dir"));

	/**
	 * 用户目录
	 * 
	 * @return
	 */
	public static File getUserDir() {
		return USER_DIR;
	}

	/**
	 * 获取文件,以用户目录为根目录
	 * 
	 * @param file
	 * @return
	 */
	public static File getFile(String file) {
		return new File(USER_DIR, file);
	}

	/**
	 * 查询文件(以用户目录为根目录开始查询)
	 * 
	 * @param targetName
	 *            目标文件名
	 * @return
	 */
	public static File findFile(String targetName) {
		return findFile(USER_DIR, targetName);
	}

	/**
	 * 查找文件
	 * 
	 * @param file
	 *            根目录
	 * @param targetName
	 *            目标文件名
	 * @return
	 */
	public static File findFile(File file, String targetName) {
		if (file.isDirectory()) {
			File target = null;
			for (File f : file.listFiles()) {
				target = findFile(f, targetName);
				if (target != null) {
					break;
				}
			}
			return target;
		} else if (file.getName().equals(targetName)) {
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 查询文件(以用户目录为根目录开始查询)
	 * 
	 * @param targetName
	 *            目标文件名
	 * @return
	 */
	public static List<File> findFiles(String targetName) {
		return findFiles(USER_DIR, targetName);
	}

	/**
	 * 查找文件
	 * 
	 * @param file
	 *            根目录
	 * @param targetName
	 *            目标文件名
	 * @return
	 */
	public static List<File> findFiles(File file, String targetName) {
		if (file.isDirectory()) {
			List<File> list = new ArrayList<File>();
			for (File f : file.listFiles()) {
				list.addAll(findFiles(f, targetName));
			}
			return list;
		} else if (file.getName().equals(targetName)) {
			return Collections.singletonList(file);
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param able
	 */
	public static void closeIo(Closeable able) {
		if (null != able) {
			try {
				able.close();
			} catch (IOException e) {
				if (_Logger.isTraceEnabled()) {
					_Logger.trace("关闭" + able + "出错", e);
				}
			}
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param able
	 */
	public static void closeIo(AutoCloseable able) {
		if (null != able) {
			try {
				able.close();
			} catch (Exception e) {
				if (_Logger.isTraceEnabled()) {
					_Logger.trace("关闭" + able + "出错", e);
				}
			}
		}
	}
}
