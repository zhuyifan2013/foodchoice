package com.mi.FoodChoice;

import android.content.Context;
import android.database.Cursor;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.ShopItem;
import com.mi.FoodChoice.data.Constants;

import java.util.ArrayList;

public class FoodHelper {

    private static final int ERROR_CODE = -1;

    public static int getTasteStrById(int id) {
        switch (id) {
            case Constants.TASTE_ID_ZONGHE:
                return R.string.taste_zonghe;
            case Constants.TASTE_ID_CHUANCAI:
                return R.string.taste_chuancai;
            case Constants.TASTE_ID_XIANGCAI:
                return R.string.taste_hunan;
            case Constants.TASTE_ID_DONGBEI:
                return R.string.taste_dongbei;
            case Constants.TASTE_ID_HANGZHOU:
                return R.string.taste_hangzhou;
            default:
                return ERROR_CODE;
        }
    }

    public static int getPriceStrById(int id) {
        switch (id) {
            case Constants.PRICE_ID_LEVEL1:
                return R.string.price_level1;
            case Constants.PRICE_ID_LEVEL2:
                return R.string.price_level2;
            case Constants.PRICE_ID_LEVEL3:
                return R.string.price_level3;
            case Constants.PRICE_ID_LEVEL4:
                return R.string.price_level4;
            case Constants.PRICE_ID_LEVEL5:
                return R.string.price_level5;
            case Constants.PRICE_ID_LEVEL6:
                return R.string.price_level6;
            case Constants.PRICE_ID_LEVEL7:
                return R.string.price_level7;
            default:
                return ERROR_CODE;
        }
    }

    public static int getDistanceStrById(int id) {
        switch (id) {
            case Constants.DISTANCE_ID_LEVEL1:
                return R.string.distance_level1;
            case Constants.DISTANCE_ID_LEVEL2:
                return R.string.distance_level2;
            case Constants.DISTANCE_ID_LEVEL3:
                return R.string.distance_level3;
            case Constants.DISTANCE_ID_LEVEL4:
                return R.string.distance_level4;
            default:
                return ERROR_CODE;
        }
    }

    public static ArrayList<ShopItem> getShopList(Context context) {
        return getShopList(context, -1);
    }

    /**
     * get shop list by group id
     */
    public static ArrayList<ShopItem> getShopList(Context context, int group) {
        String selection = FoodDatabase.ShopTable.GROUP + "=?";
        String[] selectionArgs = new String[] { Integer.toString(group) };
        if (-1 == group) {
            selection = null;
            selectionArgs = null;
        }
        ArrayList<ShopItem> shopArrayList = new ArrayList<ShopItem>();
        ShopItem shopItem;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(FoodDatabase.ShopTable.URI_SHOP_TABLE, null, selection, selectionArgs,
                            null);
            if (cursor == null) {
                return null;
            }

            while (cursor.moveToNext()) {
                shopItem = new ShopItem();
                shopItem.setId(cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable._ID)));
                shopItem.setShopName(
                        cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.NAME)));
                shopItem.setGroup(
                        cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable.GROUP)));
                shopItem.setPrice(
                        cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable.PRICE)));
                shopItem.setTaste(
                        cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable.TASTE_TYPE)));
                shopItem.setDistance(
                        cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable.DISTANCE)));
                shopArrayList.add(0, shopItem);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return shopArrayList;
    }
}
