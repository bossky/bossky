package org.bossky.mapper.json;

import java.util.Date;

import org.bossky.common.util.TimeUtil;
import org.bossky.mapper.Mapped;
import org.bossky.mapper.Mappeds;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 基于json的映射表集合
 * 
 * @author daibo
 *
 */
public class JsonMappeds implements Mappeds {
	/** json数组 */
	protected JSONArray array;

	public JsonMappeds() {
		this.array = new JSONArray();
	}

	protected JsonMappeds(JSONArray array) {
		this.array = array;
	}

	public void putNull() {
		this.array.put(JSONObject.NULL);
	}

	@Override
	public void put(Boolean value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Short value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Integer value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Long value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Float value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Double value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(String value) {
		if (null == value) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(value);
	}

	@Override
	public void put(Date date) {
		if (null == date) {
			array.put(JSONObject.NULL);
			return;
		}
		array.put(TimeUtil.formatCompleteTime(date));
	}

	@Override
	public void put(Mapped mapped) {
		if (null == mapped) {
			array.put(JSONObject.NULL);
			return;
		}
		if (mapped instanceof JsonMapped) {
			array.put(((JsonMapped) mapped).obj);
		} else {
			throw new UnsupportedOperationException("不支持的mapped对象");
		}
	}

	public void put(Mappeds mappeds) {
		if (null == mappeds) {
			array.put(JSONObject.NULL);
			return;
		}
		if (mappeds instanceof JsonMappeds) {
			array.put(((JsonMappeds) mappeds).array);
		} else {
			throw new UnsupportedOperationException("不支持的mapped对象");
		}
	}

	@Override
	public Boolean getBoolean(int index) {
		if (array.isNull(index)) {
			return null;
		}
		return array.getBoolean(index);
	}

	@Override
	public Short getShort(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return object instanceof Number ? ((Number) object).shortValue() : Short.parseShort((String) object);
	}

	@Override
	public Integer getInteger(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return object instanceof Number ? ((Number) object).intValue() : Integer.parseInt((String) object);
	}

	@Override
	public Long getLong(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return object instanceof Number ? ((Number) object).longValue() : Long.parseLong((String) object);
	}

	@Override
	public Float getFloat(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return object instanceof Number ? ((Number) object).floatValue() : Float.parseFloat((String) object);
	}

	@Override
	public Double getDouble(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return object instanceof Number ? ((Number) object).doubleValue() : Double.parseDouble((String) object);
	}

	@Override
	public String getString(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return String.valueOf(object);
	}

	@Override
	public Date getDate(int index) {
		if (array.isNull(index)) {
			return null;
		}
		Object object = array.get(index);
		return TimeUtil.parseCompleteTime(String.valueOf(object));
	}

	@Override
	public Mapped getMapped(int index) {
		if (array.isNull(index)) {
			return null;
		}
		JSONObject object = array.getJSONObject(index);
		return new JsonMapped(object);
	}

	@Override
	public int size() {
		return array.length();
	}

}
