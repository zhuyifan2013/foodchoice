package com.mi.FoodChoice.model;

import com.mi.FoodChoice.data.Shop;

public class SingleShopResponse extends BaseResponse {

    private Shop businesses;

    public Shop getBusinesses() {
        return businesses;
    }

    public void setBusinesses(Shop businesses) {
        this.businesses = businesses;
    }
}
