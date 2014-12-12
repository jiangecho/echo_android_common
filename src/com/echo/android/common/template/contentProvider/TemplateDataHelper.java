package com.echo.android.common.template.contentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

/**
 * TODO this is just a template
 * @author jiangecho
 *
 */
public class TemplateDataHelper extends BaseDataHelper{
	private Context mContext;
	private TemplateDataHelper(Context context){
		super(context);
		mContext = context;
	}
	
	public static final String CONTENT_AUTHORITY = "com.echo.template.template.contentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TemplateDbInfo.TABLE_NAME);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.template.table";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.template.row";
	
	@Override
	public Uri getContentUri() {
		return CONTENT_URI;
	}


	// TODO must implement your own CursorLoader
	@Override
	public CursorLoader getCursorLoader() {
		return new CursorLoader(mContext, getContentUri(), null, null, null, TemplateDbInfo._ID + "ASC");
	}

	public static class TemplateDbInfo implements BaseColumns{
		public static final String TABLE_NAME = "templateTable";
		public static final String ROW_1 = "row_1";
		public static final String ROW_2 = "row_2";
		public static final String ROW_3 = "row_3";

		public static void createTable(SQLiteDatabase db){
			String sqlCreateTable = "CREATE TABLE " + TABLE_NAME + " ("
					+ TemplateDbInfo._ID + " INTEGER PRIMARY KEY, "
					+ TemplateDbInfo.ROW_1 + " text, "
					+ TemplateDbInfo.ROW_2 + " text, "
					+ TemplateDbInfo.ROW_3 + " text "
					+ ")"
					;
			db.execSQL(sqlCreateTable);
		}
	}
}
