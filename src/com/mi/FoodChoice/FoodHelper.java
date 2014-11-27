package com.mi.FoodChoice;

import android.content.Context;
import android.database.Cursor;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.ShopItem;

import java.util.ArrayList;

public class FoodHelper {

    private static final int ERROR_CODE = -1;

    private static final int TASTE_ZONGHE = 0;
    private static final int TASTE_CHUANCAI = 1;
    private static final int TASTE_XIANGCAI = 2;
    private static final int TASTE_DONGBEI = 3;
    private static final int TASTE_HANGZHOU = 4;

    private static final int PRICE_LEVEL1 = 0;
    private static final int PRICE_LEVEL2 = 1;
    private static final int PRICE_LEVEL3 = 2;
    private static final int PRICE_LEVEL4 = 3;
    private static final int PRICE_LEVEL5 = 4;
    private static final int PRICE_LEVEL6 = 5;
    private static final int PRICE_LEVEL7 = 6;

    private static final int DISTANCE_LEVEL1 = 0;
    private static final int DISTANCE_LEVEL2 = 1;
    private static final int DISTANCE_LEVEL3 = 2;
    private static final int DISTANCE_LEVEL4 = 3;

    public static int getTasteIdAtPostion(int postion) {
        switch (postion) {
            case TASTE_ZONGHE:
                return R.string.taste_zonghe;
            case TASTE_CHUANCAI:
                return R.string.taste_chuancai;
            case TASTE_XIANGCAI:
                return R.string.taste_hunan;
            case TASTE_DONGBEI:
                return R.string.taste_dongbei;
            case TASTE_HANGZHOU:
                return R.string.taste_hangzhou;
            default:
                return ERROR_CODE;
        }
    }

    public static int getPriceIdAtPosition(int position) {
        switch (position) {
            case PRICE_LEVEL1:
                return R.string.price_level1;
            case PRICE_LEVEL2:
                return R.string.price_level2;
            case PRICE_LEVEL3:
                return R.string.price_level3;
            case PRICE_LEVEL4:
                return R.string.price_level4;
            case PRICE_LEVEL5:
                return R.string.price_level5;
            case PRICE_LEVEL6:
                return R.string.price_level6;
            case PRICE_LEVEL7:
                return R.string.price_level7;
            default:
                return ERROR_CODE;
        }
    }

    public static int getDistanceIdAtPosition(int position) {
        switch (position) {
            case DISTANCE_LEVEL1:
                return R.string.distance_level1;
            case DISTANCE_LEVEL2:
                return R.string.distance_level2;
            case DISTANCE_LEVEL3:
                return R.string.distance_level3;
            case DISTANCE_LEVEL4:
                return R.string.distance_level4;
            default:
                return ERROR_CODE;
        }
    }

    public static ArrayList<ShopItem> initShopArray(Context context) {
        ArrayList<ShopItem> shopArrayList = new ArrayList<ShopItem>();
        ShopItem shopItem;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(FoodDatabase.ShopTable.URI_SHOP_TABLE, null, null, null, null);
            if (cursor == null) {
                return null;
            }

            while (cursor.moveToNext()) {
                shopItem = new ShopItem();
                shopItem.setId(cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable._ID)));
                shopItem.setShopName(
                        cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.NAME)));
                shopItem.setPrice(
                        cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.PRICE)));
                shopItem.setTaste(
                        cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.TASTE_TYPE)));
                shopItem.setDistance(
                        cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.DISTANCE)));
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
