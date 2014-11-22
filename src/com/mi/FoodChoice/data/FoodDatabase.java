package com.mi.FoodChoice.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FoodDatabase {

    public static final String TABLE_SHOP = "shop";

    public static final String[] PROJECTION_SHOP = new String[] {
            ShopTable._ID, ShopTable.NAME, ShopTable.DISTANCE, ShopTable.PRICE, ShopTable.TASTE_TYPE
    };

    public static class ShopTable implements BaseColumns {

        public static final Uri URI_SHOP_TABLE = Uri.parse(FoodDbProvider.CONTENT_URI + "/shop");

        public static final String NAME = "name";

        public static final String PRICE = "price";

        public static final String TASTE_TYPE = "taste_type";

        public static final String DISTANCE = "distance";

    }
}
