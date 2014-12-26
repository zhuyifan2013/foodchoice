package com.mi.FoodChoice.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Shop implements Parcelable{

    private int business_id;
    private String name;
    private String address;
    private String telephone;
    private float latitude;
    private float longitude;
    private float distance;
    private int avg_price;
    private String photo_url;
    private String business_url;
    private int has_deal;
    private int deal_count;
    private ArrayList<ShopDeal> deals;

    public Shop() {}

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(int avg_price) {
        this.avg_price = avg_price;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(business_id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(telephone);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeInt(avg_price);
        dest.writeString(photo_url);
        dest.writeInt(has_deal);
        dest.writeInt(deal_count);
        dest.writeList(deals);
    }

    private Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            Shop shop = new Shop();
            shop.setBusiness_id(source.readInt());
            shop.setName(source.readString());
            shop.setAddress(source.readString());
            shop.setTelephone(source.readString());
            shop.setLatitude(source.readFloat());
            shop.setLongitude(source.readFloat());
            shop.setAvg_price(source.readInt());
            shop.setPhoto_url(source.readString());
            shop.setHas_deal(source.readInt());
            shop.setDeal_count(source.readInt());
            source.readList(shop.getDeals(),ShopDeal.class.getClassLoader());
            return shop;
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public String getBusiness_url() {
        return business_url;
    }

    public void setBusiness_url(String business_url) {
        this.business_url = business_url;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getHas_deal() {
        return has_deal;
    }

    public void setHas_deal(int has_deal) {
        this.has_deal = has_deal;
    }

    public int getDeal_count() {
        return deal_count;
    }

    public void setDeal_count(int deal_count) {
        this.deal_count = deal_count;
    }

    public ArrayList<ShopDeal> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<ShopDeal> deals) {
        this.deals = deals;
    }
}
