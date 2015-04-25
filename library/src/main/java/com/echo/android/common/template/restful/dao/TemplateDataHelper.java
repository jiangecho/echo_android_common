package com.echo.android.common.template.restful.dao;

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
	public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TemplateObjectsDbInfo.TABLE_NAME);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.template.table";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.template.row";
	
	@Override
	public Uri getContentUri() {
		return CONTENT_URI;
	}


	/**
	 * TODO implement your own CursorLoader and make your Fragment or Activity
	 * implement LoarderManager.LoadCallbacks
	 * 
	 */
	@Override
	public CursorLoader getCursorLoader() {
		return new CursorLoader(mContext, getContentUri(), null, null, null, TemplateObjectsDbInfo._ID + "ASC");
	}

	public static class TemplateObjectsDbInfo implements BaseColumns{
		public static final String TABLE_NAME = "templateTable";
		public static final String TITLE = "title";
		public static final String URL = "url";

		public static void createTable(SQLiteDatabase db){
			String sqlCreateTable = "CREATE TABLE " + TABLE_NAME + " ("
					+ TemplateObjectsDbInfo._ID + " INTEGER PRIMARY KEY, "
					+ TemplateObjectsDbInfo.TITLE + " text, "
					+ TemplateObjectsDbInfo.URL + " text "
					+ ")"
					;
			db.execSQL(sqlCreateTable);
		}
	}
}
