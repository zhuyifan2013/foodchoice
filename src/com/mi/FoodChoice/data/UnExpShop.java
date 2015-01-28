package com.mi.FoodChoice.data;

public class UnExpShop extends Shop {

    protected int isExcluded;
    private long addDate;

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public int getIsExcluded() {
        return isExcluded;
    }

    public void setIsExcluded(int isExcluded) {
        this.isExcluded = isExcluded;
    }
}
