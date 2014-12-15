package com.mi.FoodChoice;

import com.mi.FoodChoice.data.ShopItem;

import java.util.ArrayList;

public class ShopListManager {

    private static final ShopListManager instance = new ShopListManager();
    private static ArrayList<ShopItem> mShopItemList;

    private ShopListManager() {
        mShopItemList = new ArrayList<ShopItem>();
    }

    public static ShopListManager getInstance() {
        return instance;
    }

    public ArrayList<ShopItem> getShopList(){
        return mShopItemList;
    }

    public void setShopList(ArrayList<ShopItem> shopList) {
        mShopItemList = shopList;
    }
}
