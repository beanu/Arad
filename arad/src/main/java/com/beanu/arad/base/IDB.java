package com.beanu.arad.base;

import java.util.ArrayList;

public interface IDB {

	void save(Object entity);

	<T> ArrayList<T> findAll(Class<T> clazz);

	<T> ArrayList<T> findAll(Class<T> clazz, String orderBy);

	<T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere);

	<T> T findById(Class<T> clazz, Object id);

	<T> int countByWhere(Class<T> clazz, String strWhere);

	void update(Object entity);

	void update(Object entity, String strWhere);

	void delete(Object entity);

	<T> void deleteById(Class<T> clazz, String id);

	<T> void deleteByWhere(Class<?> clazz, String strWhere);

}
