package com.echo.android.common.template.contentProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

/**
 * TODO can use this class to wrapper & hide the table info
 * delegate the request to contentResolver
 * @author jiangecho
 *
 */
public class DataHelper {
	private Context mContext;
	private static DataHelper mInstance;

	private DataHelper(Context context){
		this.mContext = context;
	}
	
	public static DataHelper getInstance(Context context){
		if (mInstance == null) {
			mInstance = new DataHelper(context);
		}
		return mInstance;
	}
	
	// TODO the up layer do not need care about the uri
	public Cursor queryXXX(String[] projection, String selection, String[] selectionArgs, String sortOrder){
		ContentResolver contentResolver = mContext.getContentResolver();
		return contentResolver.query(TemplateDataHelper.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
	}
}
