package com.mi.FoodChoice.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopDeal implements Parcelable {

    private String id;
    private String title;
    private String description;
    private String url;
    private float current_price;
    private float list_price;
    private String image_url;
    private String deal_h5_url;

    private ShopDeal() {

    }

    private ShopDeal(Parcel parcel) {
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.url = parcel.readString();
        this.current_price = parcel.readFloat();
        this.list_price = parcel.readFloat();
        this.image_url = parcel.readString();
        this.deal_h5_url = parcel.readString();
    }

    public static final Creator<ShopDeal> CREATOR = new Creator<ShopDeal>() {
        @Override
        public ShopDeal createFromParcel(Parcel source) {
            return new ShopDeal(source);
        }

        @Override
        public ShopDeal[] newArray(int size) {
            return new ShopDeal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeFloat(current_price);
        dest.writeFloat(list_price);
        dest.writeString(image_url);
        dest.writeString(deal_h5_url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }

    public float getList_price() {
        return list_price;
    }

    public void setList_price(float list_price) {
        this.list_price = list_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDeal_h5_url() {
        return deal_h5_url;
    }

    public void setDeal_h5_url(String deal_h5_url) {
        this.deal_h5_url = deal_h5_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
