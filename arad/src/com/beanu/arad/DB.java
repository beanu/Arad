package com.beanu.arad;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DaoConfig;

import com.beanu.arad.core.IDB;

public class DB implements IDB {

	private static DB instance;

	private FinalDb db;

	private DB(DaoConfig config) {
		db = FinalDb.create(config);
	}

	public static DB getInstance(DaoConfig config) {
		if (instance == null)
			instance = new DB(config);
		return instance;
	}

	@Override
	public void save(Object entity) {
		db.save(entity);
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return db.findAll(clazz);
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz, String orderBy) {
		return db.findAll(clazz, orderBy);
	}

	@Override
	public void update(Object entity) {
		db.update(entity);
	}

	@Override
	public void delete(Object entity) {
		db.delete(entity);
	}

	@Override
	public <T> void deleteById(Class<T> clazz, String id) {
		db.deleteById(clazz, id);
	}

	@Override
	public <T> T findById(Class<T> clazz, Object id) {
		return db.findById(id, clazz);
	}

}
