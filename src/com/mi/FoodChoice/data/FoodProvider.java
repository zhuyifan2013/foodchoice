package com.mi.FoodChoice.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FoodProvider extends ContentProvider {

    private static final String AUTHORITY = "com.mi.foodchoice";
    public static final Uri CONTENT_URI = Uri.parse("content://com.mi.foodchoice");

    private static FoodDbHelper mFoodDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int URI_SHOP = 0;

    static {
        sUriMatcher.addURI(AUTHORITY, "shop", URI_SHOP);
    }

    @Override
    public boolean onCreate() {
        mFoodDbHelper = new FoodDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        long rowId;
        switch(sUriMatcher.match(uri)) {
            case URI_SHOP:
                /**
                 * insert row
                 */
                rowId = db.insert(FoodDatabase.TABLE_SHOP, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                return null;
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
