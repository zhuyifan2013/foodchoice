package com.mi.FoodChoice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDbHelper extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "food.db";
    private final String FILE_BUILT_IN = "built_in_shop";
    private static Context mContext;

    public FoodDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
