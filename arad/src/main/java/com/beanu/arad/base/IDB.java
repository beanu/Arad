package com.beanu.arad.base;

import java.util.ArrayList;

public interface IDB {

	public void save(Object entity);

	public <T> ArrayList<T> findAll(Class<T> clazz);

	public <T> ArrayList<T> findAll(Class<T> clazz, String orderBy);

	public <T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere);

	public <T> T findById(Class<T> clazz, Object id);

	public <T> int countByWhere(Class<T> clazz, String strWhere);

	public void update(Object entity);

	public void update(Object entity, String strWhere);

	public void delete(Object entity);

	public <T> void deleteById(Class<T> clazz, String id);

	public <T> void deleteByWhere(Class<?> clazz, String strWhere);

}
