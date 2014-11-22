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

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
        FoodDatabase.TABLE_SHOP + "(" +
        FoodDatabase.ShopTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
        FoodDatabase.ShopTable.NAME + " TEXT, " +
        FoodDatabase.ShopTable.PRICE + " TEXT, " +
        FoodDatabase.ShopTable.TASTE_TYPE + " TEXT, " +
        FoodDatabase.ShopTable.DISTANCE + " TEXT " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
