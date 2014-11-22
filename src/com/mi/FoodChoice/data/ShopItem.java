package com.mi.FoodChoice.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopItem implements Parcelable {

    private int mId;
    private String mShopName;
    private String mPrice;
    private String mTaste;
    private String mDistance;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShopName);
        dest.writeString(mPrice);
        dest.writeString(mTaste);
        dest.writeString(mDistance);
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
        mPrice = in.readString();
        mTaste = in.readString();
        mDistance = in.readString();
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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getTaste() {
        return mTaste;
    }

    public void setTaste(String taste) {
        mTaste = taste;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

}
