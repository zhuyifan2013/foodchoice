package com.mi.FoodChoice.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopItem implements Parcelable {

    private int mId;
    private String mShopName;
    private int mGroup;
    private int mPrice;
    private int mTaste;
    private int mDistance;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShopName);
        dest.writeInt(mGroup);
        dest.writeInt(mPrice);
        dest.writeInt(mTaste);
        dest.writeInt(mDistance);
    }

    public static final Creator<ShopItem> CREATOR = new Creator<ShopItem>() {

        @Override
        public ShopItem createFromParcel(Parcel source) {
            return new ShopItem(source);
        }

        @Override
        public ShopItem[] newArray(int size) {
            return new ShopItem[size];
        }
    };

    private ShopItem(Parcel in) {
        mId = in.readInt();
        mShopName = in.readString();
        mGroup = in.readInt();
        mPrice = in.readInt();
        mTaste = in.readInt();
        mDistance = in.readInt();
    }

    public ShopItem() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getShopName() {
        return mShopName;
    }

    public void setShopName(String shopName) {
        mShopName = shopName;
    }

    public int getGroup() {
        return mGroup;
    }

    public void setGroup(int group) {
        mGroup = group;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getTaste() {
        return mTaste;
    }

    public void setTaste(int taste) {
        mTaste = taste;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

}
