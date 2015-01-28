package com.mi.FoodChoice.model;

import com.mi.FoodChoice.data.ShopDeal;

import java.util.List;

public class DealsResponse extends BaseResponse {

    private List<ShopDeal> deals;

    public List<ShopDeal> getDeals() {
        return deals;
    }

    public void setDeals(List<ShopDeal> deals) {
        this.deals = deals;
    }
}
