package com.echo.android.common.template.contentProvider;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public abstract class BaseDataHelper {
	private Context mContext;

	public BaseDataHelper(Context context) {
		mContext = context;
	}

	abstract public Uri getContentUri();

	abstract public CursorLoader getCursorLoader();

	protected final Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		return mContext.getContentResolver().query(getContentUri(), projection, selection, selectionArgs, sortOrder);
	}

	protected final Uri insert(ContentValues values) {
		return mContext.getContentResolver().insert(getContentUri(), values);
	}

	protected final int bulkInsert(ContentValues[] values) {
		return mContext.getContentResolver().bulkInsert(getContentUri(), values);
	}

	protected final int bulkInsert(List<ContentValues> listValues) {
		return mContext.getContentResolver().bulkInsert(getContentUri(), (ContentValues[]) listValues.toArray());
	}

	protected final int delete(String selection, String[] selectionArgs) {
		return mContext.getContentResolver().delete(getContentUri(), selection, selectionArgs);
	}

	protected final int update(ContentValues values, String selection, String[] selectionArgs) {
		return mContext.getContentResolver().update(getContentUri(), values, selection, selectionArgs);
	}

}
