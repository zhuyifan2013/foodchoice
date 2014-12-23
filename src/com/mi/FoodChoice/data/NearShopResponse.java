package com.mi.FoodChoice.data;

import java.util.List;

public class NearShopResponse {

    private String status;
    private String total_count;
    private String count;
    private List<Shop> businesses;

    public NearShopResponse() {

    }

    public NearShopResponse(String status, String total_count, String count, List<Shop> businesses){
        this.status = status;
        this.total_count = total_count;
        this.count = count;
        this.businesses = businesses;
    }

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
