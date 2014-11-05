package com.vnp.core.datastore.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
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
			String table = new CountryTable().getTableName();
			String iID_MaQuocGia = values.getAsString("iID_MaQuocGia");
			String whereClause = String.format("iID_MaQuocGia ='%s'", iID_MaQuocGia);
			Cursor cursor = database.query(table, null, whereClause, null, null, null, null);

			int count = 0;
			if (cursor != null) {
				count = cursor.getCount();
			}
			long id = 0;

			if (count == 0) {
				// Log.e(table, "insert");
				id = database.insert(table, null, values);
			} else {
				// Log.e(table, "update");
				id = database.update(table, values, whereClause, null);
			}

			return id;
		} catch (Exception exception) {
			return -1;
		}
	}

	public long insertGiaiDau(ContentValues values) {
		try {
			// open();
			String table = new GiaiDauTable().getTableName();
			String iID_MaGiai = values.getAsString("iID_MaGiai");
			String iID_MaQuocGia = values.getAsString("iID_MaQuocGia");
			String whereClause = String.format("iID_MaGiai ='%s' and iID_MaQuocGia ='%s'", iID_MaGiai, iID_MaQuocGia);

			Cursor cursor = database.query(table, null, whereClause, null, null, null, null);

			int count = 0;
			if (cursor != null) {
				count = cursor.getCount();
			}

			long id = 0;

			if (count == 0) {
				// Log.e(table, "insert");
				id = database.insert(table, null, values);
			} else {
				// Log.e(table, "update");
				id = database.update(table, values, whereClause, null);
			}

			// close();
			return id;
		} catch (Exception exception) {
			return -1;
		}
	}

	public long insertDoiBong(ContentValues values) {
		try {
			String table = new DoiBongTable().getTableName();
			String iID_MaBXH_ChiTiet = values.getAsString("iID_MaBXH_ChiTiet");
			String iID_MaBXH = values.getAsString("iID_MaBXH");
			String iID_MaGiai = values.getAsString("iID_MaGiai");
			String iID_MaDoi = values.getAsString("iID_MaDoi");
			String whereClause = String.format("iID_MaBXH_ChiTiet ='%s' and iID_MaBXH='%s' and iID_MaGiai='%s' and iID_MaDoi='%s'", iID_MaBXH_ChiTiet, iID_MaBXH, iID_MaGiai, iID_MaDoi);

			Cursor cursor = database.query(table, null, whereClause, null, null, null, null);

			int count = 0;
			if (cursor != null) {
				count = cursor.getCount();
			}

			long id = 0;

			if (count == 0) {
				// Log.e(table, "insert");
				id = database.insert(table, null, values);
			} else {
				// Log.e(table, "update");
				id = database.update(table, values, whereClause, null);
			}

			return id;
		} catch (Exception exception) {

		}
		return -1;
	}

	public GiaiDauTable getGiaiDauTable(String iID_MaGiai) {
		GiaiDauTable dauTable = new GiaiDauTable();
		String whereClause = String.format("iID_MaGiai ='%s'", iID_MaGiai);

		Cursor cursor = database.query(dauTable.getTableName(), null, whereClause, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				Set<String> columns = dauTable.columNameS();
				for (String column : columns) {
					dauTable.setData(column, cursor.getString(cursor.getColumnIndex(column)));
				}
			}
		}

		if (cursor != null)
			cursor.close();
		return dauTable;
	}

	public Cursor query(String tableName, String where) {
		return database.query(tableName, null, where, null, null, null, null);
	}

	public long inset(String tableName, ContentValues contentValues, String where) {
		Cursor cursor = database.query(tableName, null, where, null, null, null, null);

		int count = 0;
		if (cursor != null) {
			count = cursor.getCount();
		}

		if (where == null) {
			count = 0;
		}
		long id = -1;
		if (count == 0) {
			// Log.e(table, "insert");
			id = database.insert(tableName, null, contentValues);
		} else {
			// Log.e(table, "update");
			id = database.update(tableName, contentValues, where, null);
		}

		return id;

	}
}