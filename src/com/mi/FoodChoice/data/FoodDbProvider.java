package com.mi.FoodChoice.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FoodDbProvider extends ContentProvider {

    private static final String AUTHORITY = "com.mi.foodchoice";
    public static final Uri CONTENT_URI = Uri.parse("content://com.mi.foodchoice");

    private static FoodDbHelper mFoodDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int URI_UNEXPECTED_SHOP = 0;
    public static final int URI_UNEXPECTED_SHOP_ITEM = 1;

    static {
        sUriMatcher.addURI(AUTHORITY, "unexpected_shop", URI_UNEXPECTED_SHOP);
        sUriMatcher.addURI(AUTHORITY, "unexpected_shop/#", URI_UNEXPECTED_SHOP_ITEM);
    }

    @Override
    public boolean onCreate() {
        mFoodDbHelper = new FoodDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mFoodDbHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case URI_UNEXPECTED_SHOP:
                projection = FoodDatabase.PROJECTION_UNEXPECTED_SHOP;
                cursor = db.query(FoodDatabase.TABLE_UNEXPECTED_SHOP, projection, selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        long rowId;
        switch (sUriMatcher.match(uri)) {
            case URI_UNEXPECTED_SHOP:
                /**
                 * insert row
                 */
                rowId = db.insert(FoodDatabase.TABLE_UNEXPECTED_SHOP, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                return null;
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        int rowNums = 0;
        switch (sUriMatcher.match(uri)) {
            case URI_UNEXPECTED_SHOP:
                /**
                 *
                 */
                rowNums = db.delete(FoodDatabase.TABLE_UNEXPECTED_SHOP, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                rowNums = 0;
        }
        return rowNums;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mFoodDbHelper.getWritableDatabase();
        int rowNums = 0;
        switch (sUriMatcher.match(uri)) {
            case URI_UNEXPECTED_SHOP:
                rowNums = db.update(FoodDatabase.TABLE_UNEXPECTED_SHOP, values, selection,
                        selectionArgs);
                break;
            default:
                rowNums = 0;
        }
        return rowNums;
    }
}
