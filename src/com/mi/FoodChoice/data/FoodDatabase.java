package com.mi.FoodChoice.data;

import android.provider.BaseColumns;

public class FoodDatabase {

    public static final String TABLE_SHOP = "shop";

    public static class ShopTable implements BaseColumns {

        public static final String NAME = "name";

        public static final String PRICE = "price";

        public static final String TASTE_TYPE = "taste_type";

        public static final String DISTANCE = "distance";

    }
}
