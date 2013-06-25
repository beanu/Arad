package com.beanu.arad.core;

import java.util.Map;

public interface IPreferences {
	public void putBoolean(String key, boolean val);

	public void putInteger(String key, int val);

	public void putLong(String key, long val);

	public void putFloat(String key, float val);

	public void putString(String key, String val);

	public void put(Map<String, ?> vals);

	public boolean getBoolean(String key);

	public int getInteger(String key);

	public long getLong(String key);

	public float getFloat(String key);

	public String getString(String key);

	public boolean getBoolean(String key, boolean defValue);

	public int getInteger(String key, int defValue);

	public long getLong(String key, long defValue);

	public float getFloat(String key, float defValue);

	public String getString(String key, String defValue);

	/**
	 * Returns a read only Map<String, Object> with all the key, objects of the
	 * preferences.
	 */
	public Map<String, ?> get();

	public boolean contains(String key);

	public void clear();

	public void remove(String key);

	/** Makes sure the preferences are persisted. */
	public void flush();
}
