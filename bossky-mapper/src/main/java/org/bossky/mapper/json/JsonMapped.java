package org.bossky.mapper.json;

import java.util.Date;

import org.bossky.common.util.TimeUtil;
import org.bossky.mapper.Mapped;
import org.bossky.mapper.Mappeds;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 基于json的映射表
 * 
 * @author daibo
 *
 */
public class JsonMapped implements Mapped {
	/** json对象 */
	protected JSONObject obj;

	public JsonMapped() {
		this.obj = new JSONObject();
	}

	public JsonMapped(String value) {
		this.obj = new JSONObject(value);
	}

	protected JsonMapped(JSONObject obj) {
		this.obj = obj;
	}

	@Override
	public boolean isNull(String key) {
		return obj.isNull(key);
	}

	@Override
	public void put(String key, Boolean value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Short value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Integer value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Long value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Float value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Double value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, String value) {
		if (null == value) {
			return;
		}
		obj.put(key, value);
	}

	@Override
	public void put(String key, Date date) {
		if (null == date) {
			return;
		}
		obj.put(key, TimeUtil.formatCompleteTime(date));
	}

	@Override
	public void put(String key, Mapped mapped) {
		if (mapped instanceof JsonMapped) {
			obj.put(key, ((JsonMapped) mapped).obj);
		} else {
			throw new UnsupportedOperationException("不支持的mapped对象");
		}
	}

	@Override
	public Boolean getBoolean(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		return obj.getBoolean(key);
	}

	@Override
	public Short getShort(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		Object object = obj.get(key);
		return object instanceof Number ? ((Number) object).shortValue() : Short.parseShort((String) object);
	}

	@Override
	public Integer getInteger(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		Object object = obj.get(key);
		return object instanceof Number ? ((Number) object).intValue() : Integer.parseInt((String) object);
	}

	@Override
	public Long getLong(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		Object object = obj.get(key);
		return object instanceof Number ? ((Number) object).longValue() : Long.parseLong((String) object);
	}

	@Override
	public Float getFloat(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		Object object = obj.get(key);
		return object instanceof Number ? ((Number) object).floatValue() : Float.parseFloat((String) object);
	}

	@Override
	public Double getDouble(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		Object object = obj.get(key);
		return object instanceof Number ? ((Number) object).doubleValue() : Double.parseDouble((String) object);
	}

	@Override
	public String getString(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		return obj.getString(key);
	}

	@Override
	public Date getDate(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		return TimeUtil.parseCompleteTime(obj.getString(key));
	}

	@Override
	public Mapped getMapped(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		return new JsonMapped(obj.getJSONObject(key));
	}

	@Override
	public void put(String key, Mappeds mappeds) {
		if (null == mappeds) {
			return;
		}
		if (mappeds instanceof JsonMappeds) {
			obj.put(key, ((JsonMappeds) mappeds).array);
		} else {
			throw new UnsupportedOperationException("不支持的类型" + obj.getClass());
		}

	}

	@Override
	public Mappeds getMappeds(String key) {
		if (obj.isNull(key)) {
			return null;
		}
		JSONArray array = obj.getJSONArray(key);
		return new JsonMappeds(array);
	}

	@Override
	public Mapped createChildMapped() {
		return new JsonMapped();
	}

	@Override
	public Mappeds createChildMappeds() {
		return new JsonMappeds();
	}

	@Override
	public String toString() {
		return obj.toString();
	}

}
