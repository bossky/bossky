package org.bossky.store.mysql;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.bossky.store.Assistant;
import org.bossky.store.support.AbstractStoreble;

public class BaseTest extends AbstractStoreble<Assistant> {
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
	@Resource
	protected List<Integer> intlist;
	@Resource
	protected Set<String> strSet;
	@Resource
	protected String a;
	@Resource
	protected String b;

	protected BaseTest() {
		super(null);
	}

	protected BaseTest(Assistant assistant) {
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
		intlist = Arrays.asList(110, 112);
		Set<String> set = new HashSet<String>();
		set.add("Hello");
		set.add("World");
		strSet = set;
	}

	public void flush() {
		super.flush();
	}

}
