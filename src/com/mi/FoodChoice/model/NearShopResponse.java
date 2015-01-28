package com.mi.FoodChoice.model;

import com.mi.FoodChoice.data.Shop;
import com.mi.FoodChoice.model.BaseResponse;

import java.util.List;

public class NearShopResponse extends BaseResponse{


    private String total_count;

    private List<Shop> businesses;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Shop> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Shop> businesses) {
        this.businesses = businesses;
    }
}
