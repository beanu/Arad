package com.arad;

import android.content.Context;

import com.arad.base.IDB;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;

/**
 * lite-orm 数据库
 * Created by Beanu on 16/9/7.
 */
public class DB implements IDB {

    private static DB instance;
    private LiteOrm mLiteOrm;

    public DB(Context context) {
        mLiteOrm = LiteOrm.newCascadeInstance(context, "arad.db");
        mLiteOrm.setDebugged(true);
    }

    public static DB getInstance(Context context) {
        if (instance == null)
            instance = new DB(context);
        return instance;
    }

    @Override
    public void save(Object entity) {
        mLiteOrm.save(entity);
    }

    @Override
    public <T> ArrayList<T> findAll(Class<T> clazz) {
        return mLiteOrm.query(clazz);
    }

    @Override
    public <T> ArrayList<T> findAll(Class<T> clazz, String orderBy) {
        return mLiteOrm.query(new QueryBuilder<T>(clazz).orderBy(orderBy));
    }

    @Override
    public <T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere) {
        return mLiteOrm.query(new QueryBuilder<T>(clazz).where(strWhere));
    }

    @Override
    public <T> T findById(Class<T> clazz, Object id) {

        if (id instanceof Long) {
            return mLiteOrm.queryById((long) id, clazz);
        } else if (id instanceof String) {
            return mLiteOrm.queryById((String) id, clazz);
        }

        return mLiteOrm.queryById(String.valueOf(id), clazz);
    }

    @Override
    public <T> int countByWhere(Class<T> clazz, String strWhere) {

        return (int) mLiteOrm.queryCount(new QueryBuilder(clazz).where(strWhere));
    }

    @Override
    public void update(Object entity) {
        mLiteOrm.update(entity);
    }

    @Override
    public void update(Object entity, String strWhere) {
//TODO        mLiteOrm.update
    }

    @Override
    public void delete(Object entity) {
        mLiteOrm.delete(entity);
    }

    @Override
    public <T> void deleteById(Class<T> clazz, String id) {
//TODO       mLiteOrm.delete
    }

    @Override
    public <T> void deleteByWhere(Class<?> clazz, String strWhere) {
//TODO
    }

    public LiteOrm getLiteOrm() {
        return mLiteOrm;
    }
}
