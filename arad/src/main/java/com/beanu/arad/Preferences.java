package com.beanu.arad;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.beanu.arad.base.IPreferences;

import java.util.Set;

public class Preferences implements IPreferences {

    SharedPreferences sharedPrefs;
    Editor editor;

    public Preferences(SharedPreferences preferences) {
        this.sharedPrefs = preferences;
    }

    @Override
    public IPreferences put(String key, boolean val) {
        edit();
        editor.putBoolean(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, int val) {
        edit();
        editor.putInt(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, long val) {
        edit();
        editor.putLong(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, float val) {
        edit();
        editor.putFloat(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, String val) {
        edit();
        editor.putString(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, Set<String> set) {
        edit();
        editor.putStringSet(key, set);
        return this;
    }

    @Override
    public boolean getBoolean(String key) {
        return sharedPrefs.getBoolean(key, false);
    }

    @Override
    public int getInteger(String key) {
        return sharedPrefs.getInt(key, 0);
    }

    @Override
    public long getLong(String key) {
        return sharedPrefs.getLong(key, 0);
    }

    @Override
    public float getFloat(String key) {
        return sharedPrefs.getFloat(key, 0);
    }

    @Override
    public String getString(String key) {
        return sharedPrefs.getString(key, "");
    }

    @Override
    public Set<String> getStringSet(String key) {
        return sharedPrefs.getStringSet(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPrefs.getBoolean(key, defValue);
    }

    @Override
    public int getInteger(String key, int defValue) {
        return sharedPrefs.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPrefs.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPrefs.getFloat(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return sharedPrefs.getString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPrefs.getStringSet(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return sharedPrefs.contains(key);
    }

    @Override
    public void clear() {
        edit();
        editor.clear();
    }

    @Override
    public void flush() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }

    @Override
    public IPreferences remove(String key) {
        edit();
        editor.remove(key);
        return this;
    }

    private void edit() {
        if (editor == null) {
            editor = sharedPrefs.edit();
        }
    }

    public SharedPreferences getSharedPrefs() {
        return sharedPrefs;
    }
}