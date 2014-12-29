package com.mi.FoodChoice;

import android.content.Context;
import android.database.Cursor;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.Shop;
import com.mi.FoodChoice.data.UnExpShop;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodHelper {

    public static String BAIDU_URI = "http://api.map.baidu.com/marker?";

    private static List<UnExpShop> unexpectedShopList;
    private static List<Shop> shopList;

    public static DisplayImageOptions DEFAULT_DISPLAY_OPTIONS = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.wtf)
            .showImageOnLoading(R.drawable.wtf)
            .showImageOnFail(R.drawable.wtf)
            .cacheOnDisk(true)
            .displayer(new SimpleBitmapDisplayer())
            .build();

    static {
        unexpectedShopList = new ArrayList<UnExpShop>();
        shopList = new ArrayList<Shop>();
    }

    public static List<UnExpShop> getUnexpectedShopList() {
        return unexpectedShopList;
    }

    public static void setUnexpectedShopList(List<UnExpShop> unexpectedShopList) {
        FoodHelper.unexpectedShopList = unexpectedShopList;
    }

    public static List<Shop> getShopList() {
        return shopList;
    }

    public static void setShopList(List<Shop> shopList) {
        FoodHelper.shopList = shopList;
    }

    public static void initUnExpectShopIdList(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(FoodDatabase.UnexpectedShop.URI_UNEXPECTED_TABLE, null, null, null,
                            null);
            while (cursor.moveToNext()) {
                UnExpShop unExpShop = new UnExpShop();
                unExpShop.setBusinessId(cursor.getInt(cursor.getColumnIndex(FoodDatabase.UnexpectedShop.BUSINESS_ID)));
                unExpShop.setShopName(cursor.getString(cursor.getColumnIndex(FoodDatabase.UnexpectedShop.SHOP_NAME)));
                unExpShop.setAddDate(cursor.getLong(cursor.getColumnIndex(FoodDatabase.UnexpectedShop.ADD_DATE)));
                unExpShop.setIsExcluded(cursor.getInt(cursor.getColumnIndex(FoodDatabase.UnexpectedShop.IS_EXCLUDED)));
                unexpectedShopList.add(unExpShop);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getDateString(long milliTime, String patten) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.format(new Date(milliTime));
    }
}
