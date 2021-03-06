package com.echo.android.common.template.restful.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class TemplateContentProvider extends ContentProvider{
	
	
	// TODO modify to classify your need
	/**
	 * URI ID for route: /templateTable
	 */
	public static final int ROUTE_TEMPLATE_TABLE = 0;

	/**
	 * URI ID for route: /templateTable/{ROW}
	 */
	public static final int ROUTE_TEMPLATE_TABLE_ROW = 1;
	
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static{
		sUriMatcher.addURI(TemplateDataHelper.CONTENT_AUTHORITY, TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, ROUTE_TEMPLATE_TABLE);
		sUriMatcher.addURI(TemplateDataHelper.CONTENT_AUTHORITY, TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME + "/#", ROUTE_TEMPLATE_TABLE_ROW);
	}
	
	// end modify
	// TODO modify the query and etc. methods.

	private static DBHelper mDBHelper;
	private static final Object DB_LOCK = new Object();

	@Override
	public boolean onCreate() {
		if (mDBHelper == null) {
			mDBHelper = new DBHelper(getContext());
		}
		return true;
	}

	@Override
	public String getType(Uri uri) {
		String type = null;
		switch (sUriMatcher.match(uri)) {
		case ROUTE_TEMPLATE_TABLE:
			type = TemplateDataHelper.CONTENT_TYPE;
			break;
		case ROUTE_TEMPLATE_TABLE_ROW:
			type = TemplateDataHelper.CONTENT_ITEM_TYPE;
			break;
		default:
			throw new IllegalArgumentException("Uri error: " + uri);
		}
		return type;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		synchronized (DB_LOCK) {
			SQLiteDatabase db = mDBHelper.getReadableDatabase();
			Cursor cursor = null;
			switch (sUriMatcher.match(uri)) {
			case ROUTE_TEMPLATE_TABLE_ROW:
				String id = uri.getLastPathSegment();
				selection = selection + " AND " + TemplateDataHelper.TemplateObjectsDbInfo._ID + " = " + id;
				// no break here
			case ROUTE_TEMPLATE_TABLE:
				cursor = db.query(TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				break;
			default:
				throw new IllegalArgumentException("Query failed: " + uri);
			}
			return cursor;
		}
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {
		synchronized (DB_LOCK) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			long id;
			switch (sUriMatcher.match(uri)) {
			case ROUTE_TEMPLATE_TABLE:
				id = db.insert(TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				break;

			case ROUTE_TEMPLATE_TABLE_ROW:
			default:
				throw new IllegalArgumentException("Insert failed: " + uri);
			}
			return ContentUris.withAppendedId(uri, id);
		}
	}
	
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		synchronized (DB_LOCK) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			try {
				db.beginTransaction();
				switch (sUriMatcher.match(uri)) {
					case ROUTE_TEMPLATE_TABLE:
						for (ContentValues value : values) {
							db.insert(TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, null, value);
						}
						db.setTransactionSuccessful();
						getContext().getContentResolver().notifyChange(uri, null);
						return values.length;
					case ROUTE_TEMPLATE_TABLE_ROW:
					default:
						db.setTransactionSuccessful();
						throw new IllegalArgumentException("Insert failed: " + uri);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Insert failed: " + uri);
			} finally {
				db.endTransaction();
			}
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		synchronized (DB_LOCK) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			String id;
			int count = 0;
			switch (sUriMatcher.match(uri)) {
			case ROUTE_TEMPLATE_TABLE_ROW:
				id = uri.getLastPathSegment();
				selection = selection + " AND " + TemplateDataHelper.TemplateObjectsDbInfo._ID + " = " + id;
			case ROUTE_TEMPLATE_TABLE:
				count = db.delete(TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, selection, selectionArgs);
				break;

			default:
				throw new IllegalArgumentException("Delete failed: " + uri);
			}
			return count;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		synchronized (DB_LOCK) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			int count = 0;
			String id;
			switch (sUriMatcher.match(uri)) {
			case ROUTE_TEMPLATE_TABLE_ROW:
				id = uri.getLastPathSegment();
				selection = selection + " AND " + TemplateDataHelper.TemplateObjectsDbInfo._ID + " = " + id;
			case ROUTE_TEMPLATE_TABLE:
				count = db.update(TemplateDataHelper.TemplateObjectsDbInfo.TABLE_NAME, values, selection, selectionArgs);
				break;

			default:
				throw new IllegalArgumentException("Update failed: " + uri);
			}
			return count;
		}
	}
	
	private class DBHelper extends SQLiteOpenHelper{
		
		private static final String DB_NAME = "template.db";
		private static final int VERSION = 1;

		public DBHelper(Context context){
			this(context, DB_NAME, null, VERSION);
		}
		public DBHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			TemplateDataHelper.TemplateObjectsDbInfo.createTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO
		}
		
	}

}
