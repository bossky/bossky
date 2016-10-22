package org.bossky.bossky.store.sqlite;

import java.util.Date;

import javax.annotation.Resource;

import org.bossky.store.support.AbstractStoreble;

public class BaseTest extends AbstractStoreble<AssistantImpl> {
	@Resource
	protected byte byteValue;
	@Resource
	protected Byte byteValueObj;
	@Resource
	protected short shortValue;
	@Resource
	protected Short shortValueObj;
	@Resource
	protected int intValue;
	@Resource
	protected Integer intValueObj;
	@Resource
	protected long longValue;
	@Resource
	protected Long longValueObj;
	@Resource
	protected float floatValue;
	@Resource
	protected Float floatValueObj;
	@Resource
	protected double doubleValue;
	@Resource
	protected Double doubleValueObj;
	@Resource
	protected boolean booleanValue;
	@Resource
	protected Boolean booleanValueObj;
	@Resource
	protected char charValue;
	@Resource
	protected Character charValueObj;
	@Resource
	protected Date dateValue;
	@Resource
	protected String strValue;

	protected BaseTest() {
		super(null);
	}

	protected BaseTest(AssistantImpl assistant) {
		super(assistant);
		genId();
	}

	public void init() {
		byteValue = Byte.MIN_VALUE;
		byteValueObj = Byte.MAX_VALUE;
		shortValue = Short.MIN_VALUE;
		shortValueObj = Short.MAX_VALUE;
		intValue = Integer.MIN_VALUE;
		intValueObj = Integer.MAX_VALUE;
		longValue = Long.MIN_VALUE;
		longValueObj = Long.MAX_VALUE;
		floatValue = Float.MIN_VALUE;
		floatValueObj = Float.MAX_VALUE;
		doubleValue = Double.MIN_VALUE;
		doubleValueObj = Double.MAX_VALUE;
		booleanValue = false;
		booleanValueObj = true;
		charValue = Character.MIN_VALUE;
		charValueObj = Character.MAX_VALUE;
		dateValue = new Date();
		strValue = "\"HelloWorld\"";
	}

	public void flush() {
		super.flush();
	}

}
