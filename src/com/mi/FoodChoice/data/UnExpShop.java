package com.mi.FoodChoice.data;

public class UnExpShop {
    private int businessId;
    private int isExcluded;
    private String shopName;
    private long addDate;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getIsExcluded() {
        return isExcluded;
    }

    public void setIsExcluded(int isExcluded) {
        this.isExcluded = isExcluded;
    }
}
