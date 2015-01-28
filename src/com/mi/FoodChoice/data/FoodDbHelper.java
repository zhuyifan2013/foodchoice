package com.mi.FoodChoice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDbHelper extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "food.db";

    public FoodDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                FoodDatabase.TABLE_UNEXPECTED_SHOP + "(" +
                FoodDatabase.UnexpectedShop._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FoodDatabase.UnexpectedShop.BUSINESS_ID + " INTEGER, " +
                FoodDatabase.UnexpectedShop.SHOP_NAME + " TEXT, " +
                FoodDatabase.UnexpectedShop.IS_EXCLUDED + " INTEGER, " +
                FoodDatabase.UnexpectedShop.ADD_DATE + " LONG " + ")");
    }
}
