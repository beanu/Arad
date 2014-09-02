package com.beanu.arad;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DaoConfig;
import net.tsz.afinal.db.sqlite.DbModel;
import net.tsz.afinal.db.table.TableInfo;

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

	@Override
	public <T> int countByWhere(Class<T> clazz, String strWhere) {
		TableInfo table = TableInfo.get(clazz);
		DbModel model = db
				.findDbModelBySQL("select count(*) count from " + table.getTableName() + " where " + strWhere);
		if (model != null) {
			return model.getInt("count");
		}
		return 0;
	}

	@Override
	public void update(Object entity, String strWhere) {
		db.update(entity, strWhere);
	}

	@Override
	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere) {
		return db.findAllByWhere(clazz, strWhere);
	}

	@Override
	public <T> void deleteByWhere(Class<?> clazz, String strWhere) {
		db.deleteByWhere(clazz, strWhere);
	}

}
