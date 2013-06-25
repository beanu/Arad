package com.beanu.arad;

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

}
