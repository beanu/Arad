package com.beanu.arad.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类转换初始化
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            Type clsType = o.getClass().getGenericSuperclass();
            if (clsType instanceof ParameterizedType) {
                Type type = ((ParameterizedType) clsType).getActualTypeArguments()[i];
                if (type instanceof ParameterizedType) {
                    return ((Class<T>) ((ParameterizedType) type).getRawType()).newInstance();
                } else if (type instanceof Class) {
                    return ((Class<T>) type).newInstance();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
