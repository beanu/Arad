package com.beanu.arad.base;

import java.util.Map;
import java.util.Set;

public interface IPreferences {
	IPreferences putBoolean(String key, boolean val);

	IPreferences putInteger(String key, int val);

	IPreferences putLong(String key, long val);

	IPreferences putFloat(String key, float val);

	IPreferences putString(String key, String val);

	IPreferences putStringSet(String key, Set<String> set);

	IPreferences put(Map<String, ?> vals);

	boolean getBoolean(String key);

	int getInteger(String key);

	long getLong(String key);

	float getFloat(String key);

	String getString(String key);

	boolean getBoolean(String key, boolean defValue);

	int getInteger(String key, int defValue);

	long getLong(String key, long defValue);

	float getFloat(String key, float defValue);

	String getString(String key, String defValue);

	/**
	 * Returns a read only  Map with all the key, objects of the
	 * preferences.
	 */
	Map<String, ?> get();

	boolean contains(String key);

	void clear();

	void remove(String key);

	/** Makes sure the preferences are persisted. */
	void flush();
}
