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
			addTable(new LiveScoreLikeTable());
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

	// -------------------------------------------------
	// LVIESCORE
	// -------------------------------------------------
	public void liveScoreHiddenAll() {
		ContentValues values = new ContentValues();
		values.put("bdneedshow", "0");
		database.update(new LiveScoreLikeTable().getTableName(), values, null, null);
	}

	public long liveScoreAdd(ContentValues values) {
		LiveScoreLikeTable table = new LiveScoreLikeTable();
		// String iID_MaMayChu = values.getAsString("iID_MaMayChu");
		/**
		 * add giai dau. neu giai dau chua co, insert giai dau neu giai dau da
		 * ton tai thi update lai thong tin giai dau giai dau luon co bdposition
		 * = 0
		 */
		String iID_MaGiai = values.getAsString("iID_MaGiai");
		String sTenGiai = values.getAsString("sTenGiai");
		String sLogoQuocGia = values.getAsString("sLogoQuocGia");

		String where = String.format(//
				"iID_MaGiai = '%s' and bdposition='0' "//
				, iID_MaGiai//
				);
		Cursor cursor = database.query(table.getTableName(), null, where, null, null, null, null);
		ContentValues dataGiaiDauValues = new ContentValues();
		dataGiaiDauValues.put("bdposition", "0");
		dataGiaiDauValues.put("iID_MaGiai", iID_MaGiai);
		dataGiaiDauValues.put("sTenGiai", sTenGiai);
		dataGiaiDauValues.put("sLogoQuocGia", sLogoQuocGia);
		dataGiaiDauValues.put("bdneedshow", "1");

		if (cursor != null && cursor.getCount() >= 1) {
			database.update(table.getTableName(), dataGiaiDauValues, where, null);
		} else {
			database.insert(table.getTableName(), null, dataGiaiDauValues);
		}

		/**
		 * insert or update tran
		 */
		String iID_MaTran = values.getAsString("iID_MaTran");
		String iID_MaDoiNha = values.getAsString("iID_MaDoiNha");
		String iID_MaDoiKhach = values.getAsString("iID_MaDoiKhach");

		String whereTranDau = String.format(//
				"iID_MaTran ='%s' and iID_MaGiai='%s' and iID_MaDoiNha='%s' and iID_MaDoiKhach='%s' ",//
				iID_MaTran//
				, iID_MaGiai//
				, iID_MaDoiNha//
				, iID_MaDoiKhach//
				);

		Cursor cursorCheck = database.query(table.getTableName(), null, whereTranDau, null, null, null, null);
		long id = -1;
		values.put("bdposition", "1");
		values.put("bdneedshow", "1");

		if (cursorCheck != null && cursorCheck.getCount() >= 1) {
			id = database.update(table.getTableName(), values, whereTranDau, null);
		} else {
			id = database.insert(table.getTableName(), null, values);
		}

		return id;
	}

	public long liveScoreLike(String iID_MaTran) {
		String where = String.format(//
				"iID_MaTran ='%s' and bdneedshow ='1'",//
				iID_MaTran//
				);
		LiveScoreLikeTable table = new LiveScoreLikeTable();
		Cursor cursor = database.query(table.getTableName(), null, where, null, null, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			String bdliked = cursor.getString(cursor.getColumnIndex("bdliked"));

			if (bdliked == null || "0".equals(bdliked) || "".equals(bdliked)) {
				bdliked = "1";
			} else {
				bdliked = "0";
			}

			ContentValues values = new ContentValues();
			values.put("bdliked", bdliked);
			return database.update(table.getTableName(), values, where, null);
		}

		return -1;
	}

	public Cursor liveScoreQueryLiked() {
		String whereTeamFiller = " bdliked='1' and bdposition ='1'";
		Cursor cursorTeam = database.query(new LiveScoreLikeTable().getTableName(), null, whereTeamFiller, null, null, null, null);
		String where = " bdliked='1' or ( bdposition ='0' and iID_MaGiai IN(%s))";
		String in = "";
		if (cursorTeam != null) {
			while (cursorTeam.moveToNext()) {
				String iID_MaGiai = cursorTeam.getString(cursorTeam.getColumnIndex("iID_MaGiai"));
				if ("".equals(in)) {
					in = String.format("'%s'", iID_MaGiai);
				} else {
					in = String.format("%s,'%s'", in, iID_MaGiai);
				}
			}
		}
		where = String.format(where, in);
		String orderBy = "iID_MaGiai,bdposition";
		return database.query(new LiveScoreLikeTable().getTableName(), null, where, null, null, null, orderBy);
	}

}