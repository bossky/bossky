package org.bossky.store.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bossky.common.ResultPage;
import org.bossky.common.util.Misc;
import org.bossky.common.util.TimeUtil;
import org.bossky.mapper.Mapped;
import org.bossky.mapper.Mappeds;
import org.bossky.mapper.Mapper;
import org.bossky.mapper.Meta;
import org.bossky.mapper.MetaType;
import org.bossky.mapper.json.JsonMapped;
import org.bossky.mapper.json.JsonMappeds;
import org.bossky.mapper.support.SimpleMapperSet;
import org.bossky.store.StoreId;
import org.bossky.store.Storeble;
import org.bossky.store.db.DbStore;
import org.bossky.store.db.support.DbExecuter;
import org.bossky.store.db.util.SQLUtil;
import org.bossky.store.exception.StoreException;

/**
 * 基于mysq的存储器
 * 
 * @author bo
 *
 * @param <T>
 */
public class MysqlStore<T extends Storeble> extends DbStore<T> {
	/** 真值 */
	private static final int TRUE = 1;
	/** 假值 */
	private static final int FALSE = 0;
	/** 空值 */
	private static final String NULL = "NULL";
	/** 空字符 */
	private static final char EMPTY = '\u0000';
	/** 列表类型映射常量 */
	private static final String LIST_TYPE = "__types";
	/** 列表值映射常量 */
	private static final String LIST_VALUE = "__values";
	/** 集合类型映射常量 */
	private static final String SET_TYPE = "__types";
	/** 集合值映射常量 */
	private static final String SET_VALUE = "__values";
	/** 映射表键类型映射常量 */
	private static final String MAP_KEY_TYPE = "__keytypes";
	/** 映射表值类型映射常量 */
	private static final String MAP_KEY_VALUE = "__keyvalues";
	/** 映射表值类型映射常量 */
	private static final String MAP_VALUE_TYPE = "__keytypes";
	/** 映射表值值映射常量 */
	private static final String MAP_VALUE_VALUE = "__keyvalues";
	/** 对象类型映射常量 */
	private static final String OBJECT_TYPE = "__type";
	/** 对象类映射常量 */
	private static final String OBJECT_CLASS = "__class";
	/** 映射器集合 */
	protected SimpleMapperSet mapperSet;
	/** 是否智能的注册映射器 */
	protected boolean isSmartMapper;

	protected MysqlStore(MysqlStoreHub hub, Class<T> clazz, Object... initargs) {
		super(hub, clazz, initargs);
		isSmartMapper = true;
		mapperSet = new SimpleMapperSet();
	}

	@Override
	protected String toSqlType(MetaType type) {
		// NULL The value is a NULL value.
		// INTEGER The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8
		// bytes depending on the magnitude of the value.
		// REAL The value is a floating point value, stored as an 8-byte IEEE
		// floating point number.
		// TEXT The value is a text string, stored using the database encoding
		// (UTF-8, UTF-16BE or UTF-16LE)
		// BLOB The value is a blob of data, stored exactly as it was input.
		if (type == MetaType.BYTE) {
			return "TEXT";
		}
		if (type == MetaType.SHORT || type == MetaType.INTEGER || type == MetaType.LONG) {
			return "INTEGER";
		}
		if (type == MetaType.FLOAT || type == MetaType.DOUBLE) {
			return "REAL";
		}
		if (type == MetaType.BOOLEAN) {
			return "INTEGER";
		}
		if (type == MetaType.CHARACTER) {
			return "TEXT";
		}
		if (type == MetaType.STRING) {
			return "TEXT";
		}
		if (type == MetaType.DATE) {
			return "TEXT";
		}
		if (type == MetaType.ARRAY) {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		}
		if (type == MetaType.LIST) {
			return "TEXT";
		}
		if (type == MetaType.SET) {
			return "TEXT";
		}
		if (type == MetaType.MAP) {
			return "TEXT";
		}
		if (type == MetaType.OBJECT) {
			return "TEXT";// 所有对象都用文本存储
		}
		throw new UnsupportedOperationException("尚不支持" + type + "类型");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object toSqlValue(MetaType type, Object value) {
		if (null == value) {
			return NULL;
		} else if (type == MetaType.BYTE) {
			return "'" + SQLUtil.escape(value) + "'";
		} else if (type == MetaType.SHORT) {
			return (Short) value;
		} else if (type == MetaType.INTEGER) {
			return (Integer) value;
		} else if (type == MetaType.LONG) {
			return (Long) value;
		} else if (type == MetaType.FLOAT) {
			return (Float) value;
		} else if (type == MetaType.DOUBLE) {
			return (Double) value;
		} else if (type == MetaType.BOOLEAN) {
			return ((Boolean) value) ? TRUE : FALSE;
		} else if (type == MetaType.CHARACTER) {
			return "'" + SQLUtil.escape(value) + "'";
		} else if (type == MetaType.STRING) {
			return "'" + SQLUtil.escape(value) + "'";
		} else if (type == MetaType.DATE) {
			Date date = (Date) value;
			return "'" + TimeUtil.formatCompleteTime(date) + "'";
		} else if (type == MetaType.ARRAY) {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		} else if (type == MetaType.LIST) {
			JsonMapped m = new JsonMapped();
			JsonMappeds types = new JsonMappeds();
			JsonMappeds values = new JsonMappeds();
			for (Object obj : (List<Object>) value) {
				putElement(types, values, obj);
			}
			m.put(LIST_TYPE, types);
			m.put(LIST_VALUE, values);
			return "'" + SQLUtil.escape(m.toString()) + "'";
		} else if (type == MetaType.SET) {
			JsonMapped m = new JsonMapped();
			JsonMappeds types = new JsonMappeds();
			JsonMappeds values = new JsonMappeds();
			for (Object obj : (Set<Object>) value) {
				putElement(types, values, obj);
			}
			m.put(SET_TYPE, types);
			m.put(SET_VALUE, values);
			return "'" + SQLUtil.escape(m.toString()) + "'";
		} else if (type == MetaType.MAP) {
			JsonMapped m = new JsonMapped();
			JsonMappeds keytypes = new JsonMappeds();
			JsonMappeds keyvalues = new JsonMappeds();
			JsonMappeds types = new JsonMappeds();
			JsonMappeds values = new JsonMappeds();
			for (Entry<Object, Object> e : ((Map<Object, Object>) value).entrySet()) {
				putElement(keytypes, keyvalues, e.getKey());
				putElement(types, values, e.getValue());
			}
			m.put(MAP_KEY_TYPE, keytypes);
			m.put(MAP_KEY_VALUE, keyvalues);
			m.put(MAP_VALUE_TYPE, types);
			m.put(MAP_VALUE_VALUE, values);
			return "'" + SQLUtil.escape(m.toString()) + "'";
		} else if (type == MetaType.OBJECT) {
			JsonMapped mapped = new JsonMapped();
			toMapped(value, mapped);
			return "'" + SQLUtil.escape(mapped.toString()) + "'";
		} else {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		}
	}

	/**
	 * 存入元素
	 * 
	 * @param types
	 * @param values
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	private void putElement(JsonMappeds types, JsonMappeds values, Object value) {
		if (null == value) {
			types.putNull();
			values.putNull();
			return;
		}
		MetaType type = MetaType.valueOf(value.getClass());
		types.put(type.name());
		if (type == MetaType.BYTE) {
			values.put(String.valueOf(value));
		} else if (type == MetaType.CHARACTER) {
			values.put(String.valueOf(value));
		} else if (type == MetaType.BOOLEAN) {
			values.put((Boolean) value);
		} else if (type == MetaType.SHORT) {
			values.put((Short) value);
		} else if (type == MetaType.INTEGER) {
			values.put((Integer) value);
		} else if (type == MetaType.LONG) {
			values.put((Long) value);
		} else if (type == MetaType.FLOAT) {
			values.put((Float) value);
		} else if (type == MetaType.DOUBLE) {
			values.put((Double) value);
		} else if (type == MetaType.STRING) {
			values.put(String.valueOf(value));
		} else if (type == MetaType.DATE) {
			values.put((Date) value);
		} else if (type == MetaType.ARRAY) {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		} else if (type == MetaType.LIST) {
			JsonMapped child = new JsonMapped();
			JsonMappeds childtypes = new JsonMappeds();
			JsonMappeds childvalues = new JsonMappeds();
			for (Object obj : (List<Object>) value) {
				putElement(childtypes, childvalues, obj);
			}
			child.put(LIST_TYPE, childtypes);
			child.put(LIST_VALUE, childvalues);
			values.put(child);
		} else if (type == MetaType.SET) {
			JsonMapped child = new JsonMapped();
			JsonMappeds childtypes = new JsonMappeds();
			JsonMappeds childvalues = new JsonMappeds();
			for (Object obj : (Set<Object>) value) {
				putElement(childtypes, childvalues, obj);
			}
			child.put(SET_TYPE, childtypes);
			child.put(SET_VALUE, childvalues);
			values.put(child);
		} else if (type == MetaType.MAP) {
			JsonMapped child = new JsonMapped();
			JsonMappeds childkeytypes = new JsonMappeds();
			JsonMappeds childkeyvalues = new JsonMappeds();
			JsonMappeds childtypes = new JsonMappeds();
			JsonMappeds childvalues = new JsonMappeds();
			for (Entry<Object, Object> e : ((Map<Object, Object>) value).entrySet()) {
				putElement(childkeytypes, childkeyvalues, e.getKey());
				putElement(childtypes, childvalues, e.getValue());
			}
			child.put(MAP_KEY_TYPE, childkeytypes);
			child.put(MAP_KEY_VALUE, childkeyvalues);
			child.put(MAP_VALUE_TYPE, childtypes);
			child.put(MAP_VALUE_VALUE, childvalues);
		} else if (type == MetaType.OBJECT) {
			JsonMapped childmapped = new JsonMapped();
			toMapped(value, childmapped);
			values.put(childmapped);
		} else {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		}
	}

	/**
	 * 对象转换成映射表
	 * 
	 * @param value
	 * @param mapped
	 */
	@SuppressWarnings("unchecked")
	private void toMapped(Object object, Mapped mapped) {
		Mapper<?> mapper = hitMapper(object.getClass());
		mapped.put(OBJECT_TYPE, mapper.getName());
		mapped.put(OBJECT_CLASS, object.getClass().getName());
		for (Meta m : mapper.getMetas()) {
			MetaType type = m.getType();
			Object value = m.getValue(object);
			if (null == value) {
				continue;
			}
			if (type == MetaType.BYTE) {
				mapped.put(m.getName(), String.valueOf(value));
			} else if (type == MetaType.CHARACTER) {
				mapped.put(m.getName(), String.valueOf(value));
			} else if (type == MetaType.BOOLEAN) {
				mapped.put(m.getName(), (Boolean) value);
			} else if (type == MetaType.SHORT) {
				mapped.put(m.getName(), (Short) value);
			} else if (type == MetaType.INTEGER) {
				mapped.put(m.getName(), (Integer) value);
			} else if (type == MetaType.LONG) {
				mapped.put(m.getName(), (Long) value);
			} else if (type == MetaType.FLOAT) {
				mapped.put(m.getName(), (Float) value);
			} else if (type == MetaType.DOUBLE) {
				mapped.put(m.getName(), (Double) value);
			} else if (type == MetaType.STRING) {
				mapped.put(m.getName(), String.valueOf(value));
			} else if (type == MetaType.DATE) {
				mapped.put(m.getName(), (Date) value);
			} else if (type == MetaType.ARRAY) {
				throw new UnsupportedOperationException("尚不支持" + type + "类型");
			} else if (type == MetaType.LIST) {
				JsonMapped child = new JsonMapped();
				JsonMappeds childtypes = new JsonMappeds();
				JsonMappeds childvalues = new JsonMappeds();
				for (Object obj : (List<Object>) value) {
					putElement(childtypes, childvalues, obj);
				}
				child.put(LIST_TYPE, childtypes);
				child.put(LIST_VALUE, childvalues);
				mapped.put(m.getName(), child);
			} else if (type == MetaType.SET) {
				JsonMapped child = new JsonMapped();
				JsonMappeds childtypes = new JsonMappeds();
				JsonMappeds childvalues = new JsonMappeds();
				for (Object obj : (Set<Object>) value) {
					putElement(childtypes, childvalues, obj);
				}
				child.put(SET_TYPE, childtypes);
				child.put(SET_VALUE, childvalues);
				mapped.put(m.getName(), child);
			} else if (type == MetaType.MAP) {
				JsonMapped child = new JsonMapped();
				JsonMappeds childkeytypes = new JsonMappeds();
				JsonMappeds childkeyvalues = new JsonMappeds();
				JsonMappeds childtypes = new JsonMappeds();
				JsonMappeds childvalues = new JsonMappeds();
				for (Entry<Object, Object> e : ((Map<Object, Object>) value).entrySet()) {
					putElement(childkeytypes, childkeyvalues, e.getKey());
					putElement(childtypes, childvalues, e.getValue());
				}
				child.put(MAP_KEY_TYPE, childkeytypes);
				child.put(MAP_KEY_VALUE, childkeyvalues);
				child.put(MAP_VALUE_TYPE, childtypes);
				child.put(MAP_VALUE_VALUE, childvalues);
			} else if (type == MetaType.OBJECT) {
				JsonMapped childmapped = new JsonMapped();
				toMapped(value, childmapped);
				mapped.put(m.getName(), childmapped);
			} else {
				throw new UnsupportedOperationException("尚不支持" + type + "类型");
			}
		}
	}

	/**
	 * 从sql中解析出对象值
	 * 
	 * @param m
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Object fromSqlValue(Meta m, ResultSet rs) throws SQLException {
		MetaType type = m.getType();
		Object obj = rs.getObject(m.getName());
		if (null == obj) {
			return null;
		} else if (type == MetaType.BYTE) {
			String value = (String) obj;
			return Byte.valueOf(value);
		} else if (type == MetaType.SHORT) {
			return rs.getShort(m.getName());
		} else if (type == MetaType.INTEGER) {
			return rs.getInt(m.getName());
		} else if (type == MetaType.LONG) {
			return rs.getLong(m.getName());
		} else if (type == MetaType.FLOAT) {
			return rs.getFloat(m.getName());
		} else if (type == MetaType.DOUBLE) {
			return rs.getDouble(m.getName());
		} else if (type == MetaType.BOOLEAN) {
			return rs.getInt(m.getName()) == TRUE ? true : false;
		} else if (type == MetaType.CHARACTER) {
			String value = (String) obj;
			if (value.isEmpty()) {
				return EMPTY;
			}
			return value.charAt(0);
		} else if (type == MetaType.STRING) {
			return (String) obj;
		} else if (type == MetaType.DATE) {
			String value = (String) obj;
			return TimeUtil.parseCompleteTime(value);
		} else if (type == MetaType.ARRAY) {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		} else if (type == MetaType.LIST) {
			String value = (String) obj;
			JsonMapped mapped = new JsonMapped(value);
			Mappeds types = mapped.getMappeds(LIST_TYPE);
			Mappeds values = mapped.getMappeds(LIST_VALUE);
			int length = types.size() < values.size() ? types.size() : values.size();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < length; i++) {
				list.add(getElement(types, values, i));
			}
			return list;
		} else if (type == MetaType.SET) {
			String value = (String) obj;
			JsonMapped mapped = new JsonMapped(value);
			Mappeds types = mapped.getMappeds(SET_TYPE);
			Mappeds values = mapped.getMappeds(SET_VALUE);
			int length = types.size() < values.size() ? types.size() : values.size();
			Set<Object> list = new HashSet<Object>();
			for (int i = 0; i < length; i++) {
				list.add(getElement(types, values, i));
			}
			return list;
		} else if (type == MetaType.MAP) {
			String value = (String) obj;
			JsonMapped mapped = new JsonMapped(value);
			Mappeds keytypes = mapped.getMappeds(MAP_KEY_TYPE);
			Mappeds keyvalues = mapped.getMappeds(MAP_KEY_VALUE);
			Mappeds types = mapped.getMappeds(MAP_VALUE_TYPE);
			Mappeds values = mapped.getMappeds(MAP_VALUE_VALUE);
			int keylength = keytypes.size() < keyvalues.size() ? keytypes.size() : keyvalues.size();
			int length = types.size() < values.size() ? types.size() : values.size();
			length = length < keylength ? length : keylength;
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (int i = 0; i < length; i++) {
				Object k = getElement(keytypes, keyvalues, i);
				Object v = getElement(types, values, i);
				map.put(k, v);
			}
			return map;
		} else if (type == MetaType.OBJECT) {
			String value = (String) obj;
			JsonMapped mapped = new JsonMapped(value);
			return fromMapped(mapped);
		}
		throw new UnsupportedOperationException("尚不支持" + type + "类型");
	}

	/**
	 * 获取元素
	 * 
	 * @param types
	 * @param values
	 * @param index
	 * @return
	 */
	private Object getElement(Mappeds types, Mappeds values, int index) {
		String typeStr = types.getString(index);
		if (null == typeStr) {
			return null;
		}
		MetaType type = MetaType.valueOf(typeStr);
		if (type == MetaType.BYTE) {
			String value = values.getString(index);
			return Byte.valueOf(value);
		} else if (type == MetaType.CHARACTER) {
			String value = values.getString(index);
			if (value.isEmpty()) {
				return EMPTY;
			}
			return value.charAt(0);
		} else if (type == MetaType.BOOLEAN) {
			return values.getBoolean(index);
		} else if (type == MetaType.SHORT) {
			return values.getShort(index);
		} else if (type == MetaType.INTEGER) {
			return values.getInteger(index);
		} else if (type == MetaType.LONG) {
			return values.getLong(index);
		} else if (type == MetaType.FLOAT) {
			return values.getFloat(index);
		} else if (type == MetaType.DOUBLE) {
			return values.getDouble(index);
		} else if (type == MetaType.STRING) {
			return values.getString(index);
		} else if (type == MetaType.DATE) {
			return values.getDate(index);
		} else if (type == MetaType.ARRAY) {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		} else if (type == MetaType.LIST) {
			Mapped childmapped = values.getMapped(index);
			Mappeds childtypes = childmapped.getMappeds(LIST_TYPE);
			Mappeds childvalues = childmapped.getMappeds(LIST_VALUE);
			int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < length; i++) {
				list.add(getElement(childtypes, childvalues, i));
			}
			return list;
		} else if (type == MetaType.SET) {
			Mapped childmapped = values.getMapped(index);
			Mappeds childtypes = childmapped.getMappeds(SET_TYPE);
			Mappeds childvalues = childmapped.getMappeds(SET_VALUE);
			int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
			Set<Object> list = new HashSet<Object>();
			for (int i = 0; i < length; i++) {
				list.add(getElement(childtypes, childvalues, i));
			}
			return list;
		} else if (type == MetaType.MAP) {
			Mapped childmapped = values.getMapped(index);
			Mappeds childkeytypes = childmapped.getMappeds(MAP_KEY_TYPE);
			Mappeds childkeyvalues = childmapped.getMappeds(MAP_KEY_VALUE);
			Mappeds childtypes = childmapped.getMappeds(MAP_VALUE_TYPE);
			Mappeds childvalues = childmapped.getMappeds(MAP_VALUE_VALUE);
			int keylength = childkeytypes.size() < childkeyvalues.size() ? childkeytypes.size() : childkeyvalues.size();
			int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
			length = length < keylength ? length : keylength;
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (int i = 0; i < length; i++) {
				Object k = getElement(childkeytypes, childkeyvalues, i);
				Object v = getElement(childtypes, childvalues, i);
				map.put(k, v);
			}
			return map;
		} else if (type == MetaType.OBJECT) {
			Mapped childmapped = values.getMapped(index);
			return fromMapped(childmapped);
		} else {
			throw new UnsupportedOperationException("尚不支持" + type + "类型");
		}
	}

	/**
	 * 从映射表顺解析出对象
	 * 
	 * @param mapped
	 * @return
	 */
	private Object fromMapped(Mapped mapped) {
		Mapper<?> mapper = hitMapper(mapped);
		Object result = mapper.newInstance();
		for (Meta m : mapper.getMetas()) {
			MetaType type = m.getType();
			String key = m.getName();
			if (mapped.isNull(key)) {
				m.setValue(result, null);
				continue;
			}
			if (type == MetaType.BYTE) {
				String value = mapped.getString(key);
				m.setValue(result, Byte.valueOf(value));
			} else if (type == MetaType.CHARACTER) {
				String value = mapped.getString(key);
				if (value.isEmpty()) {
					m.setValue(result, EMPTY);
				} else {
					m.setValue(result, value.charAt(0));
				}
			} else if (type == MetaType.BOOLEAN) {
				m.setValue(result, mapped.getBoolean(key));
			} else if (type == MetaType.SHORT) {
				m.setValue(result, mapped.getShort(key));
			} else if (type == MetaType.INTEGER) {
				m.setValue(result, mapped.getInteger(key));
			} else if (type == MetaType.LONG) {
				m.setValue(result, mapped.getLong(key));
			} else if (type == MetaType.FLOAT) {
				m.setValue(result, mapped.getFloat(key));
			} else if (type == MetaType.DOUBLE) {
				m.setValue(result, mapped.getDouble(key));
			} else if (type == MetaType.STRING) {
				m.setValue(result, mapped.getString(key));
			} else if (type == MetaType.DATE) {
				m.setValue(result, mapped.getDate(key));
			} else if (type == MetaType.ARRAY) {
				throw new UnsupportedOperationException("尚不支持" + type + "类型");
			} else if (type == MetaType.LIST) {
				Mapped childmapped = mapped.getMapped(key);
				Mappeds childtypes = childmapped.getMappeds(LIST_TYPE);
				Mappeds childvalues = childmapped.getMappeds(LIST_VALUE);
				int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
				List<Object> list = new ArrayList<Object>();
				for (int i = 0; i < length; i++) {
					list.add(getElement(childtypes, childvalues, i));
				}
				m.setValue(result, list);
			} else if (type == MetaType.SET) {
				Mapped childmapped = mapped.getMapped(key);
				Mappeds childtypes = childmapped.getMappeds(SET_TYPE);
				Mappeds childvalues = childmapped.getMappeds(SET_VALUE);
				int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
				Set<Object> set = new HashSet<Object>();
				for (int i = 0; i < length; i++) {
					set.add(getElement(childtypes, childvalues, i));
				}
				m.setValue(result, set);
			} else if (type == MetaType.MAP) {
				Mapped childmapped = mapped.getMapped(key);
				Mappeds childkeytypes = childmapped.getMappeds(MAP_KEY_TYPE);
				Mappeds childkeyvalues = childmapped.getMappeds(MAP_KEY_VALUE);
				Mappeds childtypes = childmapped.getMappeds(MAP_VALUE_TYPE);
				Mappeds childvalues = childmapped.getMappeds(MAP_VALUE_VALUE);
				int keylength = childkeytypes.size() < childkeyvalues.size() ? childkeytypes.size()
						: childkeyvalues.size();
				int length = childtypes.size() < childvalues.size() ? childtypes.size() : childvalues.size();
				length = length < keylength ? length : keylength;
				Map<Object, Object> map = new HashMap<Object, Object>();
				for (int i = 0; i < length; i++) {
					Object k = getElement(childkeytypes, childkeyvalues, i);
					Object v = getElement(childtypes, childvalues, i);
					map.put(k, v);
				}
				m.setValue(result, map);
			} else if (type == MetaType.OBJECT) {
				Mapped childmapped = mapped.getMapped(key);
				m.setValue(result, fromMapped(childmapped));
			} else {
				throw new UnsupportedOperationException("尚不支持" + type + "类型");
			}
		}
		return result;
	}

	/**
	 * 获取映射表
	 * 
	 * @param clazz
	 * @return
	 */
	private Mapper<?> hitMapper(Class<?> clazz) {
		Mapper<?> m = mapperSet.getMapper(clazz);
		if (isSmartMapper) {
			m = mapperSet.register(clazz);
		}
		if (null == m) {
			throw new IllegalArgumentException("无法获取" + clazz.getName() + "对应的映射表");
		}
		return m;
	}

	/**
	 * 获取映射表
	 * 
	 * @param mapped
	 * @return
	 */
	private Mapper<?> hitMapper(Mapped mapped) {
		String className = mapped.getString(OBJECT_CLASS);
		try {
			// 可以加载回类了
			Class<?> clazz = Class.forName(className);
			return hitMapper(clazz);
		} catch (ClassNotFoundException e) {

		}
		Mapper<?> m = mapperSet.getMapper(className);
		if (null == m) {
			String type = mapped.getString(OBJECT_TYPE);
			m = mapperSet.getMapper(type);
		}
		if (null == m) {
			throw new IllegalArgumentException("无法获取" + className + "对应的映射表");
		}
		return m;
	}

	/**
	 * 获取映射表
	 * 
	 * @param name
	 * @return
	 */
	protected Mapper<?> getMapper(String name) {
		if (null == mapperSet) {
			return null;
		}
		Mapper<?> m = mapperSet.getMapper(name);
		return m;
	}

	@Override
	protected T fromSqlValue(ResultSet rs) throws SQLException {
		T obj = mapper.newInstance();
		List<Meta> metas = mapper.getMetas();
		for (Meta m : metas) {
			Object value = fromSqlValue(m, rs);
			m.setValue(obj, value);
		}
		return obj;
	}

	@Override
	protected ResultPage<T> doSearch(String start, String end) {
		return new MysqlResultPage<T>(this, start, end);
	}

	/**
	 * 搜索
	 * 
	 * @param start
	 * @param end
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	protected List<T> search(String start, String end, int startIndex, int endIndex) {
		String sql = searchSql(start, end, startIndex, endIndex);
		DbExecuter executer = hub.getExecuter();
		ResultSet rs = null;
		try {
			rs = executer.executeQuery(sql);
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				T obj = fromSqlValue(rs);
				String idValue = rs.getString(__STORE_ID);
				String captionValue = rs.getString(__STORE_ID_CAPTION);
				StoreId sid = new StoreId(obj.getClass(), idValue, captionValue);
				obj.init(sid, this);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		} finally {
			Misc.close(rs);
		}
	}

	/**
	 * 计数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	protected int count(String start, String end) {
		String sql = countSql(start, end);
		DbExecuter executer = hub.getExecuter();
		ResultSet rs = null;
		try {
			rs = executer.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		} finally {
			Misc.close(rs);
		}
	}

	/**
	 * 搜索sql语句
	 * 
	 * @param start
	 * @param end
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	protected String searchSql(String start, String end, int startIndex, int endIndex) {
		List<Meta> metas = mapper.getMetas();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (Meta m : metas) {
			sb.append("`");
			sb.append(m.getName());
			sb.append("`");
			sb.append(",");
		}
		sb.append("`");
		sb.append(__STORE_ID_CAPTION);
		sb.append("`,`");
		sb.append(__STORE_ID);
		sb.append("` FROM `");
		sb.append(tableName());
		sb.append("`");
		if (null != start || null != end) {// 有不为空的属性
			sb.append(" WHERE `");
			sb.append(__STORE_ID);
			sb.append("`");
			if (start == end) {// 相同即=
				sb.append("='");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			} else if (null == start) {// 无下限 小于
				sb.append(" < '");
				sb.append(SQLUtil.escape(end));
				sb.append("'");
			} else if (null == end) {// 无上限 大于
				sb.append(" > '");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			} else {
				sb.append(" between '");
				sb.append(SQLUtil.escape(start));
				sb.append("' and '");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			}
		}
		sb.append("limit ").append(startIndex).append(",").append(endIndex);
		sb.append(";");
		return sb.toString();
	}

	/**
	 * 计数的sl
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	protected String countSql(String start, String end) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(`").append(__STORE_ID).append("`) ");
		sb.append(" FROM `");
		sb.append(tableName());
		sb.append("`");
		if (null != start || null != end) {// 有不为空的属性
			sb.append(" WHERE `");
			sb.append(__STORE_ID);
			sb.append("`");
			if (start == end) {// 相同即=
				sb.append("='");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			} else if (null == start) {// 无下限 小于
				sb.append(" < '");
				sb.append(SQLUtil.escape(end));
				sb.append("'");
			} else if (null == end) {// 无上限 大于
				sb.append(" > '");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			} else {
				sb.append(" between '");
				sb.append(SQLUtil.escape(start));
				sb.append("' and '");
				sb.append(SQLUtil.escape(start));
				sb.append("'");
			}
		}
		return sb.toString();
	}
}
