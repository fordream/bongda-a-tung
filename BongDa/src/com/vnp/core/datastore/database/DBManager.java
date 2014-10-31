package com.vnp.core.datastore.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager {

	public static final String BDUPDATE = "BDUPDATE";
	// Database fields
	private DataBaseWrapper dbHelper;

	public void addTable(Table table) {
		dbHelper.addTable(table);
	}

	private SQLiteDatabase database;

	public SQLiteDatabase getDatabase() {
		return database;
	};

	private Context mContext;

	public DBManager(Context context) {
		dbHelper = new DataBaseWrapper(context);
		this.mContext = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	class DataBaseWrapper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "Students.db";
		private static final int DATABASE_VERSION = 1;
		private List<Table> list = new ArrayList<Table>();

		public void addTable(Table table) {
			list.add(table);
		}

		public DataBaseWrapper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// addTable(new ProjectTable());
			addTable(new CountryTable());
			addTable(new DoiBongTable());
			addTable(new GiaiDauTable());
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			for (Table table : list) {
				db.execSQL(table.createString());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (newVersion > oldVersion) {
				for (Table table : list) {
					db.execSQL(table.deleteString());
				}
			}

			onCreate(db);
		}
	}

	public long insertContry(ContentValues values) {
		try {
			open();
			String table = new CountryTable().getTableName();
			String iID_MaQuocGia = values.getAsString("iID_MaQuocGia");
			String whereClause = String.format("iID_MaQuocGia ='%s'", iID_MaQuocGia);
			// database.delete(table, whereClause, null);
			Cursor cursor = database.query(table, null, whereClause, null, null, null, null);

			int count = 0;
			if (cursor != null) {
				count = cursor.getCount();
			}
			long id = 0;
			
			if (count == 0) {
				Log.e("ID_Country", "insert");
				id = database.insert(table, null, values);
			} else {
				Log.e("ID_Country", "update");
				id = database.update(table, values, whereClause, null);
			}
			
			close();
			return id;
		} catch (Exception exception) {
			return -1;
		}
	}
}