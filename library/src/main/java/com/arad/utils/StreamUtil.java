package com.arad.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 文档保存
 * Created by Beanu on 16/7/13.
 */
public class StreamUtil {

    public static final void saveObject(String path, Object saveObject) {
        FileOutputStream fOps = null;
        ObjectOutputStream oOps = null;
        File file = new File(path);
        try {
            fOps = new FileOutputStream(file);
            oOps = new ObjectOutputStream(fOps);
            oOps.writeObject(saveObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(oOps);
            CloseUtils.close(fOps);
        }
    }

    public static final Object restoreObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(fis);
            CloseUtils.close(ois);
        }
        return obj;

    }

    static class CloseUtils {
        public static void close(Closeable stream) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
