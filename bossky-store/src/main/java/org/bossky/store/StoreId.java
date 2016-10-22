package org.bossky.store;

import org.bossky.common.util.Misc;

/**
 * 存储Id
 * 
 * @author bo
 *
 */
public final class StoreId {
	/** 唯一标识 */
	protected String id;
	/** 类型 */
	protected String type;
	/** 描述 */
	protected String caption;
	/** 类型分隔符 */
	public static char TYPE_SEPARATOR = '$';
	/** 描述分隔符 */
	public static char CAPTION_SEPARATOR = '!';

	public StoreId(String type, String id) {
		this(type, id, null);
	}

	public StoreId(Class<?> type, String id) {
		this(getType(type), id, null);
	}

	public StoreId(Class<?> type, String id, String caption) {
		this(getType(type), id, caption);
	}

	public StoreId(String type, String id, String caption) {
		this.type = type;
		this.id = id;
		this.caption = caption;
	}

	/**
	 * 获取唯一Id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 是否为指定类型
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean isType(Class<?> clazz) {
		return Misc.eq(type, getType(clazz));
	}

	/**
	 * 获取描述
	 * 
	 * @return
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * 全局唯一的标识
	 * 
	 * @return
	 */
	public String getUuId() {
		return type + TYPE_SEPARATOR + id;
	}

	public boolean equals(Object obj) {
		if (obj instanceof StoreId) {
			StoreId other = (StoreId) obj;
			return Misc.eq(type, other.type) && Misc.eq(id, other.id)
					&& Misc.eq(caption, other.caption);
		}
		return false;
	}

	@Override
	public String toString() {
		return type + TYPE_SEPARATOR + id
				+ (Misc.isEmpty(caption) ? "" : CAPTION_SEPARATOR + caption);
	}

	/**
	 * 获取类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getType(Class<?> clazz) {
		return clazz.getSimpleName();
	}

	/**
	 * 构造 StoreId对象 格式为 type$id!caption
	 * 
	 * @param uuid
	 * @return
	 */
	public static StoreId valueOf(String uuid) {
		if (Misc.isEmpty(uuid)) {
			return null;
		}
		String type = null;
		String id = null;
		String caption = null;
		int idStart = 0;
		int idEnd = uuid.length();
		int typeIndex = uuid.indexOf(TYPE_SEPARATOR);
		if (-1 != typeIndex) {
			type = uuid.substring(0, typeIndex);
			idStart = typeIndex + 1;
		}
		int captionIndex = uuid.lastIndexOf(CAPTION_SEPARATOR);
		if (-1 != captionIndex) {
			caption = uuid.substring(captionIndex + 1);
			idEnd = captionIndex;
		}
		if (idEnd - idStart != uuid.length()) {
			id = uuid.substring(idStart, idEnd);
		} else {
			id = uuid;
		}
		return new StoreId(type, id, caption);
	}

	public static void main(String[] args) {
		System.out.println(valueOf("User$123456789!我"));
	}
}
