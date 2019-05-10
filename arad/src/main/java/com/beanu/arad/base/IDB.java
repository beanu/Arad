package com.beanu.arad.base;

import java.util.ArrayList;

public interface IDB {

    <T> void save(T entity);

    <T> ArrayList<T> findAll(Class<T> clazz);

    <T> ArrayList<T> findAll(Class<T> clazz, String orderBy);

    <T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere);

    <T> T findById(Class<T> clazz, Object id);

    <T> int countByWhere(Class<T> clazz, String strWhere);

    <T> void update(T entity);

    <T> void update(T entity, String strWhere);

    <T> void delete(T entity);

    <T> void deleteById(Class<T> clazz, String id);

    <T> void deleteByWhere(Class<?> clazz, String strWhere);

}
