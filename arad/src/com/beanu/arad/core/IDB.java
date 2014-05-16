package com.beanu.arad.core;

import java.util.List;

public interface IDB {

	public void save(Object entity);

	public <T> List<T> findAll(Class<T> clazz);

	public <T> List<T> findAll(Class<T> clazz, String orderBy);

	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere);

	public <T> T findById(Class<T> clazz, Object id);

	public <T> int countByWhere(Class<T> clazz, String strWhere);

	public void update(Object entity);

	public void update(Object entity, String strWhere);

	public void delete(Object entity);

	public <T> void deleteById(Class<T> clazz, String id);
	
	public <T> void deleteByWhere(Class<?> clazz, String strWhere);

}
