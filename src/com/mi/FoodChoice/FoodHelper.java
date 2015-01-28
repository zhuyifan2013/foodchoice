package com.mi.FoodChoice;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.Shop;
import com.mi.FoodChoice.data.UnExpShop;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodHelper {

    private static String TAG = "FoodHelper";

    public static String GAODE_URI = "http://mo.amap.com/?";

    private static List<UnExpShop> unexpectedShopList;
    private static List<Shop> shopList;

    public static DisplayImageOptions DEFAULT_DISPLAY_OPTIONS = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.wtf)
            .showImageOnLoading(R.drawable.wtf)
            .showImageOnFail(R.drawable.wtf)
            .cacheOnDisk(true)
            .displayer(new FadeInBitmapDisplayer(500))
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

    /**
     * 扩展shop列表，取消了原有的setShopList方法，直接在现有元素的基础上增加元素
     *
     * @param shopList  待加入的list对象
     * @param needCheck true：需要检查是否存在于unExp列表中，若存在则不添加;
     *                  false：不检查
     */
    public static boolean extendShopList(List<Shop> shopList, boolean needCheck) {
        for (Shop shop : shopList) {
            if (!extendShopList(shop, needCheck)) {
                Log.e(TAG, "error when adding shop list");
                return false;
            }
        }
        return true;
    }

    /**
     * 扩展shop列表，取消了原有的setShopList方法，直接在现有元素的基础上增加元素
     *
     * @param shop      待加入的shop对象
     * @param needCheck true：需要检查是否存在于unExp列表中，若存在则不添加;
     *                  false：不检查
     */
    public static boolean extendShopList(Shop shop, boolean needCheck) {
        if (shop == null) {
            Log.e(TAG, "shop is null");
            return false;
        }
        //若shopList中已有此对象,则不再添加
        if (FoodHelper.shopList.contains(getShopByBusinessId(shop.getBusiness_id()))) {
            return true;
        }
        if (!needCheck) {
            FoodHelper.shopList.add(shop);
        } else {
            if (!unexpectedShopList.contains(getUnExpShopByBusinessId(shop.getBusiness_id()))) {
                FoodHelper.shopList.add(shop);
            }
        }
        return true;
    }

    public static void initUnExpectShopIdList(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(FoodDatabase.UnexpectedShop.URI_UNEXPECTED_TABLE, null, null, null,
                            null);
            while (cursor.moveToNext()) {
                UnExpShop unExpShop = new UnExpShop();
                unExpShop.setBusiness_id(cursor.getInt(
                        cursor.getColumnIndex(FoodDatabase.UnexpectedShop.BUSINESS_ID)));
                unExpShop.setName(cursor.getString(
                        cursor.getColumnIndex(FoodDatabase.UnexpectedShop.SHOP_NAME)));
                unExpShop.setAddDate(cursor.getLong(
                        cursor.getColumnIndex(FoodDatabase.UnexpectedShop.ADD_DATE)));
                unExpShop.setIsExcluded(cursor.getInt(
                        cursor.getColumnIndex(FoodDatabase.UnexpectedShop.IS_EXCLUDED)));
                if (unExpShop.getIsExcluded() == 1) {
                    unexpectedShopList.add(unExpShop);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static UnExpShop getUnExpShopByBusinessId(int businessId) {
        for (UnExpShop shop : unexpectedShopList) {
            if (shop.getBusiness_id() == businessId) {
                return shop;
            }
        }
        return null;
    }

    public static Shop getShopByBusinessId(int businessId) {
        for (Shop shop : shopList) {
            if (shop.getBusiness_id() == businessId) {
                return shop;
            }
        }
        return null;
    }

    /**
     * 根据patten对时间进行处理，返回相应的字符串
     *
     * @param milliTime 以毫秒为单位的时间值
     * @param patten    处理形式
     */
    public static String getDateString(long milliTime, String patten) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.format(new Date(milliTime));
    }

    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
