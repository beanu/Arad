package com.beanu.arad;

import net.tsz.afinal.FinalDb;
import android.content.Context;

import com.beanu.arad.core.IDB;

public class DB implements IDB {

	private static DB instance;

	private FinalDb db;

	private DB(Context ctx) {
		db = FinalDb.create(ctx);
	}

	public static DB getInstance(Context ctx) {
		if (instance == null)
			instance = new DB(ctx);
		return instance;
	}

	@Override
	public void save(Object entity) {
		db.save(entity);
	}

}
