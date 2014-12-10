package com.echo.android.common.template.contentProvider;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * TODO this is just a template
 * @author jiangecho
 *
 */
public class TemplateTable {
	private TemplateTable(){
		
	}
	
	public static final String TABLE_NAME = "templateTable";
	public static final String CONTENT_AUTHORITY = "com.echo.template.template.contentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TABLE_NAME);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.template.table";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.template.row";
	
	public static void createTable(SQLiteDatabase db){
		String sqlCreateTable = "CREATE TABLE " + TABLE_NAME + " ("
				+ Rows._ID + " INTEGER PRIMARY KEY, "
				+ Rows.ROW_1 + " text, "
				+ Rows.ROW_2 + " text, "
				+ Rows.ROW_3 + " text "
				+ ")"
				;
		
		db.execSQL(sqlCreateTable);
	}
	
	public static class Rows implements BaseColumns{
		public static final String ROW_1 = "row_1";
		public static final String ROW_2 = "row_2";
		public static final String ROW_3 = "row_3";
	}
}
