package com.beanu.arad;

import android.content.Context;
import android.os.Parcelable;

import com.beanu.arad.base.IPreferences;
import com.beanu.arad.support.log.KLog;
import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * MMKV @Link(https://github.com/Tencent/MMKV)
 * 适合存储全局变量的数据，也可以替换SharePreferences
 * <p>
 * An efficient, small mobile key-value storage framework developed by WeChat. Works on iOS, macOS and Android.
 *
 * @author Beanu
 */
public class MMKVStorage implements IPreferences {

    MMKV kv;

    public MMKVStorage(Context context) {
        String rootDir = MMKV.initialize(context);
        KLog.d("mmkv root: " + rootDir);
        kv = MMKV.defaultMMKV();
    }

    @Override
    public IPreferences put(String key, boolean val) {
        kv.encode(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, int val) {
        kv.encode(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, long val) {
        kv.encode(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, float val) {
        kv.encode(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, String val) {
        kv.encode(key, val);
        return this;
    }

    @Override
    public IPreferences put(String key, Set<String> set) {
        if (set == null) {
            throw new IllegalArgumentException("Set<String> can't be null");
        }
        kv.encode(key, set);
        return this;
    }

    @Override
    public boolean getBoolean(String key) {
        return kv.decodeBool(key);
    }

    @Override
    public int getInteger(String key) {
        return kv.decodeInt(key);
    }

    @Override
    public long getLong(String key) {
        return kv.decodeLong(key);
    }

    @Override
    public float getFloat(String key) {
        return kv.decodeFloat(key);
    }

    @Override
    public String getString(String key) {
        return kv.decodeString(key);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return kv.decodeStringSet(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return kv.decodeBool(key, defValue);
    }

    @Override
    public int getInteger(String key, int defValue) {
        return kv.decodeInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return kv.decodeLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return kv.decodeFloat(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return kv.decodeString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValue) {
        if (defValue == null) {
            throw new IllegalArgumentException("can not null");
        }
        return kv.getStringSet(key, defValue);
    }


    @Override
    public boolean contains(String key) {
        return kv.contains(key);
    }

    @Override
    public void clear() {
        kv.clearAll();
    }

    @Override
    public IPreferences remove(String key) {
        kv.removeValueForKey(key);
        return this;
    }

    @Override
    public void flush() {
        //一般不使用这个方法
        kv.commit();
    }

    public IPreferences put(String key, Parcelable parcelable) {
        kv.encode(key, parcelable);
        return this;
    }

    public <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return kv.decodeParcelable(key, tClass);
    }

    public MMKV getKv() {
        return kv;
    }
}