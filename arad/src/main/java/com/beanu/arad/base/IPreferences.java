package com.beanu.arad.base;

import java.util.Set;

/**
 * @author Beanu
 */
public interface IPreferences {

    IPreferences put(String key, boolean val);

    IPreferences put(String key, int val);

    IPreferences put(String key, long val);

    IPreferences put(String key, float val);

    IPreferences put(String key, String val);

    IPreferences put(String key, Set<String> set);

    boolean getBoolean(String key);

    int getInteger(String key);

    long getLong(String key);

    float getFloat(String key);

    String getString(String key);

    Set<String> getStringSet(String key);

    boolean getBoolean(String key, boolean defValue);

    int getInteger(String key, int defValue);

    long getLong(String key, long defValue);

    float getFloat(String key, float defValue);

    String getString(String key, String defValue);

    Set<String> getStringSet(String key, Set<String> defValue);

    boolean contains(String key);

    void clear();

    IPreferences remove(String key);

    /**
     * Makes sure the preferences are persisted.
     */
    void flush();
}
