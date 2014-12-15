package com.mi.FoodChoice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        initDb(db);
    }

    private void initDb(SQLiteDatabase db) {
        BufferedReader bufferedReader = null;
        String line;
        ContentValues values = new ContentValues();
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(FILE_BUILT_IN)));

            while ((line = bufferedReader.readLine()) != null) {
                values.clear();
                String[] fields = line.split(",");
                values.put(FoodDatabase.ShopTable.NAME, fields[0]);
                values.put(FoodDatabase.ShopTable.GROUP, fields[1]);
                values.put(FoodDatabase.ShopTable.PRICE, fields[2]);
                values.put(FoodDatabase.ShopTable.TASTE_TYPE, fields[3]);
                values.put(FoodDatabase.ShopTable.DISTANCE, fields[4]);
                db.insert(FoodDatabase.TABLE_SHOP, null, values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                FoodDatabase.TABLE_SHOP + "(" +
                FoodDatabase.ShopTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FoodDatabase.ShopTable.GROUP + " INTEGER, " +
                FoodDatabase.ShopTable.NAME + " TEXT, " +
                FoodDatabase.ShopTable.PRICE + " INTEGER, " +
                FoodDatabase.ShopTable.TASTE_TYPE + " INTEGER, " +
                FoodDatabase.ShopTable.DISTANCE + " INTEGER " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
